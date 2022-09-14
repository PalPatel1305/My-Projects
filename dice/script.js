/**  Author: Pal Manojkumar Patel, 000865048,07/12/2022,
* This is Assignment 4 for COMP 10259 course for use of javascript and manipulating some elements . 
 **/

/**
 * a function which loads after the html get load
 * adding an event listener for whole window
 */

window.addEventListener("load", function () {

    //rollbutton is the button for rolling the dice
    var rollbutton = document.getElementById("rolling");

    /* adding click event on rollbutton which will call the function named roll */
    rollbutton.addEventListener("click", roll);

    /*resetbutton is the button to reset the whole game*/
    var resetbutton = document.getElementById("reset");

    /* adding click event on resetbutton which will call the function named reset */
    resetbutton.addEventListener("click", reset);

    /*checkbutton is the button to check the user data is valid or not*/
    var checkbutton = document.getElementById("check");
    /* adding click event on checkbutton which will call the function named uservalidation */
    checkbutton.addEventListener("click", uservalidation);
    // userWinCount is the variable which will track the wins for the user
    var userWinCount = 0;
    // computercount is the variable which will track the wins for the user
    var computerWinCount = 0;
    // tie will track how many times it got tie
    var tie = 0;
    // countnumber how many rounds user want to play to get the results
    var countnumber = 0;

    //para1 and para2 is the paragraph element for user and computer
    var para1 = document.getElementById("userCount");
    var para2 = document.getElementById("computerCount");
    // result for thr results
    var result = document.getElementById("result");
    // dicePic1 and dicePic2 are the pictures of dice
    var dicePic1 = document.getElementById("user1dice");
    var dicePic2 = document.getElementById("computerdice");
    // displaystatus will decide the display of certain divs
    var displayStatus = document.getElementById("game");
    // user div 
    var userblock = document.getElementById("userData");
    // tie paragraph
    var tieblock = document.getElementById("tie");
    // hiding game block
    displayStatus.style.display = "none";
    document.body.style.backgroundColor = "bisque";

    /**
     * roll method will roll the dice and will decide thw winner for the game
     */

    function roll() {


        //increasing countnumber
        countnumber++;
        console.log("the dice are rolled");

        // generating random number and stored in firstRandomNum and secondRandomNum
        var firstRandomNum = Math.floor(Math.random() * 6) + 1;
        var secondRandomNum = Math.floor(Math.random() * 6) + 1;

        // getting the user input value of counts in variable counts and name string in nameCheck
        var counts = document.getElementById("count").value;
        var nameCheck = document.getElementById("username").value;

        // manipulating some elements
        document.getElementById("user").innerHTML = nameCheck;
        document.getElementById("computer").innerHTML = "Computer";
        tieblock.innerHTML = "Tie : " + tie;
        para1.innerHTML = nameCheck + ' : ' + userWinCount;

        // manipulating image
        dicePic1.src = 'images/dice' + firstRandomNum + '.png';
        dicePic2.src = 'images/dice' + secondRandomNum + '.png';

        // the game will run until the user entered counts
        if (countnumber <= counts) {

            if (firstRandomNum != secondRandomNum) {

                if (firstRandomNum > secondRandomNum) {
                    userWinCount++;
                    para1.innerHTML = nameCheck + ' : ' + userWinCount;
                }
                else {
                    computerWinCount++;
                    para2.innerHTML = 'Computer : ' + computerWinCount;
                }

            }
            else {
                tie++;
                tieblock.innerHTML = "Its a tie. So, Tie : " + tie;
            }

            // prinitng results

            if (countnumber == counts) {
                if (userWinCount > computerWinCount) {
                    //calling changeheading function for styling
                    changeheading();
                    // printing result
                    result.innerHTML = "Hurray!! " + nameCheck + " won!!";
                    // calling disableButton function 
                    disableButton();
                    // setting timeout for the reset function 
                    setTimeout(function () {

                        reset();
                    }, 3500);


                } else if (userWinCount < computerWinCount) {
                    //calling changeheading function for styling
                    changeheading();
                    // printing result
                    result.innerHTML = "Opps! Computer won!!";
                    // calling disableButton function 
                    disableButton();
                    // setting timeout for the reset function 
                    setTimeout(function () {
                        reset();

                    }, 3500);

                } else {
                    //calling changeheading function for styling
                    changeheading();
                    // printing result
                    result.innerHTML = "It's a Tie with each " + userWinCount + " ties";
                    // calling disableButton function 
                    disableButton();
                    setTimeout(function () {
                        reset();

                    }, 3500);

                }

            }
        }

    }
    /**
     * a function which will disable the rollbutton for few seconds after the result is declared until it reset
     */
    function disableButton() {
        rollbutton.disabled = true;
        setTimeout(function () {
            rollbutton.disabled = false;
        }, 3500);
    }

    /**
     * a function for change in style
     */

    function changeheading() {
        result.style.background = "beige";
        result.style.padding = "30px";
        result.style.animation = "animation 3s forwards";
    }

    /**
     * a function which will reset the game by resetting all the variables back at its initials
     */

    function reset() {
        userWinCount = 0;
        computerWinCount = 0;
        para1.innerHTML = ' User : ' + userWinCount;
        para2.innerHTML = 'Computer : ' + computerWinCount;
        result.innerHTML = " Results are waiting!!!";
        result.style.background = "none";
        result.style.padding = "0px";
        console.log("you reset");
        countnumber = 0;
        tie = 0;
        tieblock.innerHTML = "Tie : " + tie;


    }

    //a function which validates the user credentials

    function uservalidation() {
        // getting the user input value of counts in variable counts and name string in nameCheck
        var counts = document.getElementById("count").value;
        var nameCheck = document.getElementById("username").value;
        // data range
        var userdatacheck = /^[a-zA-Z]+$/;

        // checking name
        if (nameCheck == "") {
            console.log("Empty string");
            document.getElementById("errorMsg").innerHTML = "*Please enter the username";
            return false;
        }
        else if (nameCheck.length > 8 || nameCheck.length < 4) {
            console.log("Length issue");
            document.getElementById("errorMsg").innerHTML = "*Please enter the username in length of 4 to 8 characters";
            document.getElementById("errorMsgNum").innerHTML = "";

            return false;
        }

        else if (!nameCheck.match(userdatacheck)) {
            console.log("Not only characters");
            document.getElementById("errorMsg").innerHTML = "*Please enter only the characters";
            document.getElementById("errorMsgNum").innerHTML = "";

            return false;
        }

        else if (nameCheck.length >= 4 && nameCheck.length <= 8) {
            var uppercaseCheck = nameCheck.toUpperCase();

            var j = 1;
            for (var i = 0; i < nameCheck.length; i++) {
                var value1 = uppercaseCheck.charCodeAt(i);
                var value2 = uppercaseCheck.charCodeAt(i + 1);
                if (value1 < value2) {
                    j++;
                }
            }//checking counts
            if (j == nameCheck.length) {
                if (counts == "") {
                    console.log("No input in number feild");
                    document.getElementById("errorMsgNum").innerHTML = "*Please enter the Number";
                    document.getElementById("errorMsg").innerHTML = "Valid";
                    return false;

                }
                else if (counts <= 0) {
                    console.log("Number is negative or 0 ");
                    document.getElementById("errorMsgNum").innerHTML = "*Please enter positive number";
                    document.getElementById("errorMsg").innerHTML = "Valid";
                    return false;

                }

                else {

                    console.log("Valid!");
                    document.getElementById("errorMsgNum").innerHTML = "";
                    if (displayStatus.style.display == "none") {
                        document.body.style.backgroundColor = "white";
                        displayStatus.style.display = "block";
                        userblock.style.display = "none";
                       

                    } else {
                        displayStatus.style.display = "none";
                        userblock.style.display = "block";
                    }

                    return true;
                }

            }
            else {
                document.getElementById("errorMsg").innerHTML = "*Please enter the username in ascending order";
                document.getElementById("errorMsgNum").innerHTML = "";

            }
            return false;
        }



    }

});



