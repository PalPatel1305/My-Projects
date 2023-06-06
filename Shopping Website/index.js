/**
 * JavaScript to send request to PHPApp.php to fetch data
 * 
 * @author Darshil Gajera
 */

allproduct = "AllProducts";

/**
 * page load event
 */
window.addEventListener("load", function () {
    
    // display all products categories
    let url = "PHPApp.php?request=categories";
    fetch(url, { credentials: 'include' })
        .then(response => response.text())
        .then(categories)

    // Display all products
    let url2 = "PHPApp.php?request=AllProducts&start=0&length=7";
    fetch(url2, { credentials: 'include' })
        .then(response => response.text())
        .then(displayProducts)

    // Display cart
        DisplayCart();
    
});

/**
 * Display Total categories of product
 * @param {*} text 
 */
function categories(text) { 
    
    document.getElementById("categories").innerHTML = text;
}

/**
 * Display Products Table
 * @param {*} text 
 */
function displayProducts(text) {
    document.getElementById("productTable").innerHTML = text;
}


/**
 * Display product data
 * @param {*} ProductID 
 */
function displayProductData(ProductID){
    console.log("display product data clicked");
    let url = "PHPApp.php?request=productData&productID="+ProductID;
        fetch(url, { credentials: 'include' })
            .then(response => response.text())
            .then(productData)
}


/**
 * Product data success
 * @param {*} text 
 */
function productData(text){
    console.log("product data display "+ text);
    document.getElementById("productTable").innerHTML = text;
}





/**
 * Add to cart items
 * @param {*} ProductID 
 */
function addtocart(ProductID){

    // reeust to add item
    let url = "PHPApp.php?request=addtocart&productID="+ProductID;
        fetch(url, { credentials: 'include' })
            .then(response => response.text())
            .then(DisplayCart)

    // updating quantity in the database
    let updateQuantity = "phpapp.php?request=quantity&productID=" + ProductID + "&quantityvalue=-1";
    console.log(updateQuantity);
    fetch(updateQuantity, { credentials: 'include' })
        .then(response => response.text())
        .then(displaycategorisProducts(allproduct));

    // dispay all products
    AllProducts = "AllProducts";
    displaycategorisProducts(AllProducts);
}




/**
 * Display cart request
 */
function DisplayCart(){
    let url = "PHPApp.php?request=cart";
        fetch(url, { credentials: 'include' })
            .then(response => response.text())
            .then(ShowCartData)
}


/**
 * Display cart success
 * @param {*} text 
 */
function ShowCartData(text){
    document.getElementById("cartproduct").innerHTML = text; 
}



/**
 * Delete cart item
 * @param {*} ProductID 
 */
function deleteItem(ProductID){
    let url = "phpapp.php?request=deleteCartItem&productID=" + ProductID;
    console.log(url);
    fetch(url)
        .then(response => response.text())
        .then(DisplayCart)

    // update quantity in the database
    let updateQuantity = "phpapp.php?request=quantity&productID=" + ProductID + "&quantityvalue=1";
    console.log(updateQuantity);
    fetch(updateQuantity, { credentials: 'include' })
        .then(response => response.text())
        .then(displaycategorisProducts(allproduct));
}



/**
 * Display categories
 * @param {*} text 
 */
function displaycategorisProducts(text) {
    console.log("categories clicked");

    let url = "PHPApp.php?request=SportsProducts&category="+text+"&start=0&length=7";
        fetch(url, { credentials: 'include' })
            .then(response => response.text())
            .then(displayProducts)
}

/**
 * Next button request
 * @param {*} start 
 * @param {*} length 
 */
function next(start,length){
    if(start == 0 && length == 7){
        let url = "PHPApp.php?request=SportsProducts&category=AllProducts"+"&start=7&length=7";
        fetch(url, { credentials: 'include' })
            .then(response => response.text())
            .then(displayProducts)
    }
    else if(start == 7 && length == 7){
        let url = "PHPApp.php?request=SportsProducts&category=AllProducts"+"&start=14&length=15";
        fetch(url, { credentials: 'include' })
            .then(response => response.text())
            .then(displayProducts)
    }
}

/**
 * Previous button request
 * @param {*} start 
 * @param {*} length 
 */
function prev(start,length){
    if(start == 7 && length == 7){
        let url = "PHPApp.php?request=SportsProducts&category=AllProducts"+"&start=0&length=7";
        fetch(url, { credentials: 'include' })
            .then(response => response.text())
            .then(displayProducts)
    }
    else if(start == 14 && length == 15){
        let url = "PHPApp.php?request=SportsProducts&category=AllProducts"+"&start=7&length=7";
        fetch(url, { credentials: 'include' })
            .then(response => response.text())
            .then(displayProducts)
    }
}


