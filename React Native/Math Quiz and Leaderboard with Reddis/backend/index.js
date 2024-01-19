const express = require('express');
const app = express();
const cors = require('cors');
const http = require('http');
const server = http.createServer(app);
const redis = require("redis");
//SQLITE3
const sqlite3 = require("sqlite3").verbose();
//making a database
var file = "UserData.db";
var db = new sqlite3.Database(file);

//server listening
server.listen(3000, () => {
    console.log('listening on *:3000');
});

//Connecting to redis
client = redis.createClient({
    url: "redis://redis-15131.c323.us-east-1-2.ec2.cloud.redislabs.com:15131",
    password: "Ez3oCi3wfw1vpzambYuWvt13p8EF6YY9"
}
);
client.connect();

app.use(express.json())
app.use(cors({
    allowedHeaders: ['Content-Type'],
}));

/**
 * Creating new user
 * inserting new user to the database
 */

app.post("/signup", async function (req, res) {

    const loginCredentials = req.body;
    let alreadyExists = await checkUserAlreadyExists(loginCredentials.username, loginCredentials.password);

    if (!alreadyExists) {
        console.log("Insert for the user data");
        db.run("INSERT INTO users (username, password) VALUES (?, ?)", [loginCredentials.username, loginCredentials.password]);
        res.send({ status: "Success" });
    }
    else {
        res.send({ status: "Fail" });
    }
});

/**
 * Post request for login
 * Checks if already exist than send Success login
 */
app.post("/login", async function (req, res) {

    const loginCredentials = req.body;

    const alreadyExists = await checkUserAlreadyExists(loginCredentials.username, loginCredentials.password);


    if (alreadyExists) {
        res.send({ status: "Success" });
    }
    else {
        res.send({ status: "Fail" });
    }
});
/**
 * Updating the user in redis with there status
 */
app.post("/update", async function (req, res) {
    const userUpdate = req.body;
    //if the answer is correct
    if (userUpdate.userAnswer === "Correct!") {
        client.on("error", function (err) {
            console.log("Error: " + err);
        });
        //creating a new record for the user

        let exists = await client.exists("record_id");
        let record_id;

        if (exists == 0) {
            record_id = 1;
            await client.set("record_id", record_id);
        } else {
            record_id = await client.get("record_id");
        }

        //adding there value
        await client.hSet(
            "leader:" + record_id,
            {
                username: userUpdate.leader,
                result: userUpdate.userAnswer,
            }
        );

        await client.set("record_id", parseInt(record_id) + 1);
    }

    res.send({ status: "Success" });
});

/**
 * 
 * @param {*} username username of the user
 * @param {*} password password of the user
 * @returns true or false based on if the user exists or not
 */

async function checkUserAlreadyExists(username, password) {
    try {
        // Ensure the table exists or create it if it doesn't
        db.exec("CREATE TABLE IF NOT EXISTS users (username TEXT, password TEXT)");

        //getting the results
        const results = await new Promise((resolve, reject) => {
            db.all("SELECT rowid as id FROM users WHERE username = ? AND password = ?", [username, password], (err, rows) => {
                if (err) {
                    reject(err);
                } else {
                    resolve(rows);
                }
            });
        });

        if (results && results.length > 0) {
            return true;
        } else {
            return false;
        }
    } catch (error) {
        console.error("Error occurred:", error);
        throw error;
    }
}
/**
 * Getting the leaderboard data from the redis
 */
app.post("/getLeaderBoard", async function (req, res) {
    try {
        const leaderboard = [];
        const recordId = await client.get("record_id");
        const exists = await client.exists("record_id");

        if (exists === 0) {
            console.log("No records found");
            return res.send("No records found");
        }

        //assigning the start id depending on the length of records
        const startId = recordId <= 10 ? 1 : recordId - 10;

        for (let i = recordId - 1; i >= startId; i--) {
            const result = await client.hGetAll(`leader:${i}`);
            leaderboard.push(result);
            console.log(`Leader ${i}: ${JSON.stringify(result)}`);
        }

        res.send(leaderboard);
    } catch (err) {
        console.error("Error retrieving data:", err);
        res.status(500).send("Error retrieving data");
    }
});


db.close;