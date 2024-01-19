import {
  TouchableOpacity,
  StyleSheet,
  Text,
  View,
  TextInput,
  Button
} from 'react-native';
import axios from 'axios';
import { useEffect, useState } from 'react';

export default function App() {
  const [signup, setSignup] = useState(false);
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [reType, setRetype] = useState("")
  const [screenToDisplay, setScreenToDisplay] = useState("Home");
  const [loginMessage, setLoginMessage] = useState("")
  const [answerStatus, setAnswerStatus] = useState('');

  /**
   * HandleAction of Login or Registration
   */
  const handleAction = async () => {

    if (signup) {
      if (userName && password && password === reType) {
        //registration functionality
        await axios.post("http://localhost:3000/signup", {
          username: userName,
          password: password
        });
        setLoginMessage("");
        setSignup(false);
        setScreenToDisplay("MathGame")
        //if any field is empty.
      } else if (!(userName && password && reType)) {
        setLoginMessage('All fields must be completed');
        //if password and retype doesnt match.
      } else if (password !== reType) {
        setLoginMessage("Passwords do not match");
      }
    } else {
      if (userName && password) {
        // login functionality
        const response = await axios.post("http://localhost:3000/login", {
          username: userName,
          password: password
        });
        if (response.data) {
          if (response.data.status === "Success") {
            setLoginMessage("");
            setScreenToDisplay("MathGame")
          }
          else {
            //username or password incorrect
            setLoginMessage('Username and/or password incorrect');
          }
        }
      } else {
        setLoginMessage('Please enter valid details');
      }
    }
  };

  //-------------------------------------------MathGame Component---------------------------------------------

  const MathGame = ({ name }) => {
    const [num1, setNum1] = useState();
    const [num2, setNum2] = useState();
    const [sum, setSum] = useState();
    const [userAnswer, setUserAnswer] = useState(null);
    const [errorMessage, setErrorMessage] = useState("");


    useEffect(() => {
      generateNumbers();
    }, []);

    //generate random numbers
    const generateNumbers = () => {
      const randomNum = () => Math.floor((Math.random() * 100) + 1);
      const firstNum = randomNum();
      const secondNum = randomNum();
      setNum1(firstNum);
      setNum2(secondNum);
      setSum(firstNum + secondNum);
    };

    //check the Answer
    const checkAnswer = async () => {
      const onlyNumbers = /^[0-9]*$/;
      if (userAnswer !== "" && userAnswer!==null) {
        if (onlyNumbers.test(userAnswer)) {
          const userAnswerStatus = userAnswer == sum ? "Correct!" : "Incorrect!";
           if (userAnswerStatus && userAnswerStatus === "Correct!") {
            try {
              //updating the results in redis
              await axios.post("http://localhost:3000/update", {
                leader: name,
                userAnswer: userAnswerStatus
              });

            } catch (err) {
              console.log(err);
            }
          }
          setErrorMessage("");
          handleGameResults(userAnswerStatus);
        } else {
          setErrorMessage("Enter only numbers!");
          setUserAnswer(null);
        }
      } else {
        setErrorMessage("Enter the answer!");
        setUserAnswer(null);
      }
    }
    return (
      <View style={styles.container}>
        <View style={styles.card}>
          <View style={styles.squareContainer}>
            <Text style={styles.numbers}>{num1} </Text>
            <Text style={styles.numbers} >+</Text>
            <Text style={styles.numbers}>{num2}</Text>
          </View>
          <TextInput
            placeholder="Enter answer"
            style={styles.input}
            onChangeText={(text) => setUserAnswer(text)}
          />
          {errorMessage != "" ? <Text style={styles.loginMessage}>{errorMessage}</Text> : <></>}
          <View style={styles.buttonContainer}>
            <Button title="Result" onPress={() => checkAnswer()} />
            <Button title="Home" onPress={() => setScreenToDisplay('Home')} />
          </View>

        </View>
      </View>
    );
  }
  //HandleGameresults
  const handleGameResults = (status) => {
    setAnswerStatus(status);
    setScreenToDisplay('GameResults');
  };
  //---------------------------------GameResultsScreen-----------------------------------------------------------
  const GameResultsScreen = ({ answerStatus }) => {
    const [leaderBoard, setLeaderBoard] = useState([]);
    const [result, setResult] = useState("");
    const [status, setStatus] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    useEffect(() => {
      getLeaderBoard(); // Fetch leaderboard data on mount
      setResult(answerStatus);
      if (answerStatus === "Correct!") {
        setStatus(true);
      }
      else {
        setStatus(false);
      }
    }, []);
    const cardColor = status ? '#00ff00' : '#ff0000';

    // function to fetch leaderboard data
    async function getLeaderBoard() {
      try {
        setIsLoading(true)
        const response = await axios.post("http://localhost:3000/getLeaderBoard");
        setLeaderBoard(response.data);
        setIsLoading(false)
      }
      catch (err) {
        console.log(err);
      }
    }



    return (
      <View style={styles.container}>
        <View style={[styles.card, { backgroundColor: cardColor }]}>
          <Text style={styles.result}>{result}</Text>
        </View>

        <View style={styles.leaderboardContainer}>
          <Text style={styles.leaderboard}>Leaderboard</Text>
          {/* LeaderBoard Table */}
          {leaderBoard.length !== 0 && !isLoading ? (
            <View style={styles.tableContainer}>
              <View style={styles.row}>
                <Text style={styles.columnHeader}>Leaders</Text>

              </View>
              {leaderBoard.map((item, index) => (
                <View key={index} style={styles.row}>
                  <Text style={styles.column}>{item.username}</Text>
                </View>
              ))}
            </View>
          ) : isLoading ?
            <Text style={styles.noUsers}>Loading!!</Text>
            : <Text style={styles.noUsers}>No users yet!</Text>}
          <Button
            style={styles.playAgainButton}
            title="Next"
            onPress={() => { setScreenToDisplay('MathGame') }}
          />
        </View>
      </View>
      // </View>
    );
  }

  return (
    //Home Screen View
    screenToDisplay === "Home" ?

      <View style={styles.container}>
        <View style={styles.card}>
          <Text style={styles.logo}>Math Game</Text>
          <Text style={styles.title}>{signup ? 'Create an Account' : 'Log In'}</Text>
          <TextInput
            style={styles.input}
            placeholder="Username"
            onChangeText={(user) => setUserName(user)}
          />

          <TextInput
            style={styles.input}
            secureTextEntry={true}
            placeholder="Password"
            onChangeText={(pwd) => setPassword(pwd)}
          />

          {signup && (
            <>

              <TextInput
                style={styles.input}
                secureTextEntry={true}
                placeholder="Re-type Password"
                onChangeText={(text) => setRetype(text)}
              />
            </>
          )}


          {signup ? (
            <View style={styles.buttonContainer}>
              <Button style={{ ...styles.button, marginHorizontal: 10 }} title="Register" onPress={handleAction} />
              <Button style={styles.button} title="Cancel" onPress={() => { setSignup(false); setLoginMessage(''); }} />
            </View>
          ) : (
            <Button style={styles.button} title="Log in" onPress={handleAction} />
          )}


          <Text style={styles.loginMessage}>{loginMessage}</Text>
        </View>

        {!signup && (
          <TouchableOpacity onPress={() => { setSignup(true); setLoginMessage(''); }}>
            <Text style={styles.signUpText}>Sign Up</Text>
          </TouchableOpacity>
        )}
      </View>
      :
      //Game View
      screenToDisplay === 'MathGame' ? (
        <MathGame name={userName} handleGameResults={handleGameResults} />
      ) :
        //Results view
        screenToDisplay === 'GameResults' ? (
          <GameResultsScreen answerStatus={answerStatus} />
        ) : null
  )
}
const styles = StyleSheet.create({
  // ========================================   Home Component Styling =============================================================
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#e0e9f3', // Light blue gradient start
  },
  card: {
    backgroundColor: '#3b5998', // Dark blue gradient end
    paddingVertical: 30,
    paddingHorizontal: 20,
    borderRadius: 20,
    alignItems: 'center',
    width: '40%',
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 5,
    },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
  },
  logo: {
    fontSize: 40,
    fontWeight: 'bold',
    color: '#fff',
    marginBottom: 20,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#fff',
    marginBottom: 30,
  },
  input: {
    backgroundColor: '#fff',
    width: '100%',
    borderRadius: 10,
    paddingHorizontal: 20,
    marginBottom: 20,
    height: 50,
  },

  buttonContainer: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    width: '40%',
  },

  button: {
    marginHorizontal: 13,
  },

  buttonText: {
    color: '#fff',
    fontWeight: 'bold',
    fontSize: 18,
  },
  loginMessage: {
    color: '#fff',
    marginTop: 20,
    fontSize: 16,
  },
  signUpText: {
    color: '#3b5998', // Light grey
    marginTop: 20,
    fontSize: 16,
    fontWeight: 'bold',
    textDecorationLine: 'underline',
  },
  // ====================================================  Math Game Styling===============================================================
  squareContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 20,
  },
  numbers: {
    fontSize: 24,
    marginRight: 20,
    color: '#fff',
  },
  result: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#fff',
  },
  leaderboardContainer: {
    width: '60%',
    alignItems: 'center',
    justifyContent: 'center',
  },
  leaderboard: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#3b5998',
    margin: 10,
  },
  tableContainer: {
    width: '60%',
    backgroundColor: '#fff',

    borderRadius: 15,
    marginBottom: 20,
    borderWidth: 1,
    borderColor: '#ccc',
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 3,
    },
    shadowOpacity: 0.3,
    shadowRadius: 4,
    elevation: 6,
  },
  row: {

    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingHorizontal: 20,
    paddingVertical: 15,
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
  columnHeader: {
    textAlign: 'center',
    flex: 1,
    fontWeight: 'bold',
    fontSize: 16,
    color: '#3b5998',
  },
  column: {
    fontSize: 16,
    color: '#3b5998',
    flex: 1,
    textAlign: 'center',
  },

  noUsers: {
    fontSize: 18,
    color: '#777',
    textAlign: 'center',
    marginBottom: 20,
  },
  playAgainButton: {
    marginTop: 20,
    backgroundColor: '#4caf50', // Button color
    borderRadius: 8,
  },
});
