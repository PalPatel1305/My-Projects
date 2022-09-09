/**  Author: Pal Manojkumar Patel, 000865048,07/26/2022,
* This is Assignment 5 for COMP 10259 course for manipulating dom with the svg elements.
**/

//link
const svgNS = "http://www.w3.org/2000/svg";

// storing score
let score = 0;

/**
 * a function to identify which star is collected and to update the score
 * @param id for which star is collected
 * `
 */
function collectedStars(id) {
    let svg = document.getElementById("gameArea");
    //identifying star
    let star = document.getElementById(id);
    // when it is collected it will get blur
    svg.removeChild(star);
    //updating Score
    score++;
    document.getElementById("score").innerHTML = "Collected Stars : " + score;

}

/**
 * onmouseover eventlistener will increase the results size on hover
 */

function onmouse() {
    document.getElementById("score").style.fontSize = "1.8em";
}

/**
 * onmouseout eventlistener will bring back the results original size on hover
 */
function offmouse() {
    document.getElementById("score").style.fontSize = "1.3em";
}

/**
 * after the html page is loaded, it will get run
 * load event listener
 */
window.addEventListener("load", function () {


    //check button for validation
    let showButton = document.getElementById("check");

    //adding click event on show button
    showButton.addEventListener("click", uservalidation);

    //whole div of player information
    let playerbox = document.getElementById("playerData");

    //whole div of game
    let gamebox = document.getElementById("game");

    // setting display of game forthe first apperance as none
    gamebox.style.display = "none";

    // whole svg section
    let svg = document.getElementById("gameArea");

    // time for each game
    var timeLimit = 30;

    // div for displaying time
    var timeblock = document.getElementById('Time');

    // circle id
    var circleID = 0;

    // star id
    var polyID = 0;

    // generating 250 background stars for making the night effect
    for (i = 0; i < 250; i++) {
        //setting radius
        let radius = Math.random() * 2;
        // setting a new circle for svg
        let newBox = document.createElementNS(svgNS, "circle");
        //id of that circle
        newBox.setAttribute("id", "circle" + circleID);
        // x,y and r
        newBox.setAttribute("cx", radius * 2 + Math.random() * 700);
        newBox.setAttribute("cy", radius * 2 + Math.random() * 500);
        newBox.setAttribute("r", radius);
        // including that circle to svg
        svg.appendChild(newBox);
        // increasing id one by one to 250
        circleID++;

    }

    /**
     * a function which will produce the random stars which has to be collected by the user
     * 
     */

    function drawGame() {
        //generating 30 stars
        for (i = 0; i < 30; i++) {

            // setting a new polygon for svg
            let newStar = document.createElementNS(svgNS, "use");
            // star id
            newStar.setAttribute("id", "poly" + polyID);
            // class for stars
            newStar.setAttribute("class", "poly");
            //this will draw the stars by the reference of id star which is the polygon
            newStar.setAttributeNS("http://www.w3.org/1999/xlink", "href", "#star");
            //x and y cordinates
            newStar.setAttribute("x", Math.random() * 650);
            newStar.setAttribute("y", Math.random() * 450);
            //filling color
            newStar.setAttribute("fill", "gold");
            // event listener for the star if it is clicked 
            newStar.setAttribute("onclick", "collectedStars(\"poly" + polyID + "\")");
            // adding elements to svg
            svg.appendChild(newStar);
            //increasing poly id
            polyID++;

        }

        /**
         * time function for the countdown
         */
        // setting interval
        var timerId = setInterval(countdown, 1000);
        function countdown() {
            if (timeLimit == 0) {
                clearTimeout(timerId);
                //displaying time is over
                timeblock.innerHTML = "Time over";
                document.getElementById("star").style.display = "none";

                // creating new text Element at the end to display 
                let newText = document.createElementNS(svgNS, "text");

                //id
                newText.setAttribute("id", "svgText");

                //x,y, text, fill and fontsize
                newText.setAttribute("x", 275);
                newText.setAttribute("y", 250);
                newText.setAttribute("font-size", 50);
                newText.setAttribute("fill", "silver");

                //adding that element to svg
                svg.appendChild(newText);

                //displaying results
                document.getElementById("finalResult").innerHTML = "You Collected " + score + " stars!!"

                //animation for that new added element

                var temp = null;

                // y location for that element
                var locationy = 550;
                clearInterval(temp);
                temp = setInterval(textAnimation, 7);

                /**
                 * function for the animation
                 */

                function textAnimation() {
                    if (locationy == 250) {
                        clearInterval(temp);

                    }
                    else {
                        locationy -= 5;
                        newText.setAttribute("y", locationy + 'px');
                    }
                }

                //displaying score
                document.getElementById("score").innerHTML = "Collected Stars : " + score;
                if(score==0){
                    newText.innerHTML="Oops!!"
                }
                else if(score<=10){
                    newText.innerHTML="Good!"
                }else if(score<=20){
                    newText.innerHTML="Amazing!"
                }
                else{
                    newText.innerHTML="Excellent!!"

                 }

            }
            else {
                // showing how many time is remaining
                timeblock.innerHTML = timeLimit + ' seconds remaining';
                timeLimit--;

            }
        }

    }


    /**
     * validtaing user data
     *
     */
    function uservalidation() {

            // getting value of user input
        var playernameCheck = document.getElementById("username").value;
        var usercheck = /^[a-zA-Z]+$/;
        // checking for string is empty 
        if (playernameCheck == "" || playernameCheck == null) {

            console.log("empty string");
            document.getElementById("errorMsg").innerHTML = "*Please enter the username";
            return false;

        }

        // checking the name is of correct length
        else if (playernameCheck.length > 8 || playernameCheck.length < 4) {

            console.log("length issue");
            document.getElementById("errorMsg").innerHTML = "*Please enter the username in length of 4 to 8 characters";
            return false;

        }

        // checking for only characters
        else if (!playernameCheck.match(usercheck)) {

            document.getElementById("errorMsg").innerHTML = "*Please enter only the characters";
            return false;
        }

        else {
            // if valid 
            console.log("valid!")
            // show the game and hide the playerblock
            if (gamebox.style.display == "none") {
                gamebox.style.display = "inline-block";
                //calling the function 
                drawGame();
                playerbox.style.display = "none";
                document.getElementById("headingcurve").style.display = "none";

            } else {
                gamebox.style.display = "none";
                playerbox.style.display = "inline-block";
            }
            return true;
        }
    }

});


