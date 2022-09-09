/**  Author: Pal Manojkumar Patel, 000865048,08/07/2022,
* This is Assignment 6 for COMP 10259 course for use of Ajax and manipulating dom . 
 **/

/**
 * adding event listener click to the windows 
 */

window.addEventListener("load", function () {
	// preventing the form to be submit by adding an event listener to it
	document.forms.ajaxForm.addEventListener("submit", function (event) {
		event.preventDefault();
	});
	// attaching a function on the click of the first button and it doesnt require any parameter so no validation for it 
	document.forms.ajaxForm.firstButton.addEventListener("click", function (event) {
		// url
		let url1 = "https://csunix.mohawkcollege.ca/~adams/10259/a6_responder.php";
		// fetching data
		fetch(url1, { credentials: 'include' })
			.then(response => response.text())
			// run the function named firstData after success
			.then(firstData)
	});

		// document.forms.ajaxForm.check.addEventListener("click", function (event) {
		// 	// getting text input value
		// 	textinput = document.getElementById("inputField").value;

		// checking for the input of the user
		
			// document.getElementById("check").disabled = true;

			// attaching a function on the click of the second button 
			document.forms.ajaxForm.secondButton.addEventListener("click", function (event) {
					// getting text input value
					textinput = document.getElementById("inputField").value;

				if (textinput == "starwars" || textinput == "mario") {
					// if the input is correct
					document.getElementById("errorMsg").innerHTML = "Valid! Press second or third button."
		
					// disabling inputfeild and button to not be changed by user after a valid input
					document.getElementById("inputField").disabled = true;
			

				// GET method 

				// url with user input
				let url2 = "https://csunix.mohawkcollege.ca/~adams/10259/a6_responder.php?choice=" + textinput;

				// fetching data
				fetch(url2, { credentials: 'include' })
					.then(response => response.json())
					// run the function named secondData after success
					.then(secondData)
				}
				// if inputfield is empty
				else if (textinput == "") {
					document.getElementById("errorMsg").innerHTML = "*Please enter values";

				}// user entered wrong input
				else {
					document.getElementById("errorMsg").innerHTML = "*Please enter values from starwars or mario";
				}
			});

			// attaching a function on the click of the third button 
			document.forms.ajaxForm.thirdButton.addEventListener("click", function (event) {
				// getting text input value
				textinput = document.getElementById("inputField").value
				
				if (textinput == "starwars" || textinput == "mario") {
					// if the input is correct
					document.getElementById("errorMsg").innerHTML = "Valid! Press second or third button."
		
					// disabling inputfeild and button to not be changed by user after a valid input
					document.getElementById("inputField").disabled = true;
			

					// POST method
					// parameters
					let params = "choice=" + textinput;

					// url
					let url3 = "https://csunix.mohawkcollege.ca/~adams/10259/a6_responder.php";

					// fetching data
					fetch(url3, {
						credentials: 'include',
						method: 'POST',
						headers: { "Content-type": "application/x-www-form-urlencoded" },
						body: params
					})

					.then(response => response.json())
					// run the function named thirdData after success
					.then(thirdData);
				}// if inputfield is empty
				else if (textinput == "") {
					document.getElementById("errorMsg").innerHTML = "*Please enter values";

				}// user entered wrong input
				else {
					document.getElementById("errorMsg").innerHTML = "*Please enter values from starwars or mario";
				}
			});

		
	});


/**
 * function which manipulate the dom after getting the data - FirstButton
 * @param {*} text 
 */
function firstData(text) {
	// debug
	console.log(text);

	// adding the response to html
	document.getElementById("header").innerHTML = "<h1 class ='text-center'>" + text + "  -000865048" + "</h1>";
}

/**
 * function which manipulate the dom after getting the data - SecondButton
 * @param {*} a 
 */
function secondData(a) {
	// debug
	console.log(JSON.stringify(a));

	//declaring a variable to store the data 
	var ajaxData = "";

	for (let i = 0; i < a.length; i++) {

		data = "<h2>" + a[i].series + "</h2>" +
			"<img src='" + a[i].url + "' width='200px' height='200px' />" +
			"<p>" + a[i].name + "</p>";

		// for the width of the div
		if (a.length == 3) {
			ajaxData += "<div class = 'col-sm-4'>" + data + "</div>";
		}

		else if (a.length == 2) {
			ajaxData += "<div class = 'col-sm-6'>" + data + "</div>";
		}
		else {
			ajaxData += "<div class='col-sm-12'>" + data + "</div>";
		}

	}
	// for the footer text for the data

	if (textinput == "starwars") {
		document.getElementById("ajaxData").innerHTML = ajaxData + "<p class='text-center'>Star Wars © & TM 2022 Lucasfilm Ltd. All rights reserved. Visual material © 2022 Electronic Arts Inc. </p>";
	}
	else if (textinput == "mario") {
		document.getElementById("ajaxData").innerHTML = ajaxData + "<p class='text-center'>Game trademarks and copyrights are properties of their respective owners. Nintendo properties are trademarks of Nintendo. © 2019 Nintendo. </p>";
	}

}

/**
 * function which manipulate the dom after getting the data -ThirdButton
 * @param {*} a 
 */


function thirdData(a) {
	//debug
	console.log(a);
	// getting html element
	table = document.getElementById("ajaxTable");
	// adding the first row of the table
	tabledata = "<tr><th class ='danger'>Series</th><th class = 'danger'>Name</th><th class = 'danger'>Link</th></tr>";

	for (let i = 0; i < a.length; i++) {
		// adding row to the table with the data
		row = "<td>" + a[i].series + "</td>" +
			"<td>" + a[i].name + "</td>" +
			"<td><a href='" + a[i].url + "'>" + a[i].url + "</td>";
		tabledata += "<tr>" + row + "</tr>";

	}
	// for the footer text for the data

	if (textinput == "starwars") {
		table.innerHTML = tabledata + "<caption class='text-center'>Star Wars © & TM 2022 Lucasfilm Ltd. All rights reserved. Visual material © 2022 Electronic Arts Inc. </caption>";
	}
	else if (textinput == "mario") {
		table.innerHTML = tabledata + "<caption class='text-center'>Game trademarks and copyrights are properties of their respective owners. Nintendo properties are trademarks of Nintendo. © 2019 Nintendo. </caption>";
	}



}



