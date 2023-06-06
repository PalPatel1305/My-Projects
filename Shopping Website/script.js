//category
function category(data) {
    let categoryList = "";
    for (let i = 0; i < data.length; i++) {
        categoryList += "<li class='nav-item' onclick='getCategoryProduct(\"" + data[i] + "\")'>" + data[i] + "</li>";
    }
    categoryList += "<li class='nav-item' onclick='getproducts(0,7)'>All Products</li>"
    document.getElementById("category").innerHTML = categoryList;
}
function getCategoryProduct(category) {
    let productUrl = "phpapp.php?request=categoryProduct&start=0&length=7&category=" + category;
    console.log(productUrl);
    fetch(productUrl, { credentials: 'include' })
        .then(response => response.json())
        .then(printData)
}
//products
function printData(data) {
    let row = "<tr><th>Product id</th><th>Product Name</th><th>Product Price</th><th>Quantity</th></tr>";
    for (i = 0; i < data.length; i++) {
        row += "<tr><td onclick='detail(\"" + data[i].product_id + "\")'>" + data[i].product_id + "</td>";
        row += "<td onclick='detail(\"" + data[i].product_id + "\")'>" + data[i].product_name + "</td><td>" + data[i].product_price + "</td>";
        if ((data[i].product_quantity) != 0) {
            row += "<td id='" + data[i].product_id + "'>" + data[i].product_quantity + "</td></tr>";
        } else {
            row += "<td id='outOfStock'>Out of Stock</td></tr>";
        }

    }
    if (data[0].last_product_number == 7) {
        row += "<tr id='lastRow'><td colspan = '4' id='btnnext'><input type='submit' onclick='getproducts(7,7)' value='Next >'></td></tr>";
    } else if (data[0].last_product_number == 14) {
        row += "<tr class='bg-light'><td id='btnprev' colspan = '2'><input type='submit' onclick='getproducts(0,7)' value=' < Previous'></td><td colspan='2' id='btnnext'><input type='submit' onclick='getproducts(14,2)' value='Next > '></td></tr>";
    } else if (data[0].last_product_number == 0) {
        row += "";
    } else {
        row += "<tr id='lastRow'><td colspan = '4' id='btnprev'><input type='submit' onclick='getproducts(7,7)' value=' < Previous'></td></tr>";
    }
    document.getElementById("products").innerHTML = row;

}

//details
function detail(product) {
    let detail = "phpapp.php?request=detail&product=" + product;
    console.log(detail); // debug

    // do the fetch
    fetch(detail, { credentials: 'include' })
        .then(response => response.json())
        .then(listdetails)
}
function listdetails(text) {
    let data = "";

    for (let i = 0; i < text.length; i++) {
        data += "<tr><td>Product Code</td><td>" + text[i].product_id + "</td></tr>";
        data += "<tr><td>Product Name</td><td>" + text[i].product_name + "</td></tr>";
        data += "<tr><td>Product Description</td><td>" + text[i].product_description + "</td></tr>";
        data += "<tr><td>Product Price</td><td>" + text[i].product_price + "</td></tr>";
        if (text[i].product_quantity <= 0) {
            data += "<tr><td id='outOfStock'>Out of Stock</td><td><input type='submit' onclick='getproducts(0,7)' value='Back'></td></tr>";
        } else {
            data += "<tr><td><button onclick='add(\"" + text[i].product_id + "\"," + text[i].product_quantity + ")'>" + "Add To Cart </button></td></tr></form>";
        }
    }
    document.getElementById("products").innerHTML = data;
}
//cart
function success(text) {
    // empty string
    let cartdata = "";
    //total
    let total = 0;
    let removeID = 0;

    for (let i = 0; i < text.length; i++) {

        cartdata += "<tr>";
        cartdata += "<td>" + text[i].product_id + "</td><td>" + text[i].product_name + "</td><td>" + text[i].product_price + "</td>";
        cartdata += "<td><input type='image' onclick='remove(" + removeID + "," + text[i].product_quantity + ")" + "' src='" + "images\\CartIcon.png'" + "width='40' height='40'/><input type='hidden' id='" + removeID + "' value=" + text[i].product_id + "></td></tr>";
        total += parseInt(text[i].product_price);
        removeID++;

    }
    document.getElementById("cart").innerHTML = cartdata;
    document.getElementById("total").innerHTML = "<div>Total: $" + total.toFixed(2) + "</div>"


}
//adding
function add(product, quantity) {
    let addCart = "phpapp.php?request=addCart&product=" + product;
    console.log(addCart);
    fetch(addCart, { credentials: 'include' })
        .then(response => response.text())
        .then(adding)

    let reduceQuantity = "phpapp.php?request=quantity&product=" + product + "&updateQuantity=-1";
    console.log(reduceQuantity);
    fetch(reduceQuantity, { credentials: 'include' })
        .then(response => response.text())
        .then(quantity)
        .then(getproducts(0, 7))
}
function quantity(text) {
    console.log(text);
}
function adding(text) {
    console.log(text);
    let cartRequest = 'phpapp.php?request=cart';
    console.log(cartRequest);
    // do the fetch
    fetch(cartRequest, { credentials: 'include' })
        .then(response => response.json())
        .then(success)
}
//remove 
function remove(id, quantity) {
    let removeurl = "phpapp.php?request=removeCart&product=" + document.getElementById(id).value;
    console.log(removeurl);
    fetch(removeurl)
        .then(response => response.text())
        .then(removeCart)

    let increaseQuantity = "phpapp.php?request=quantity&product=" + document.getElementById(id).value + "&updateQuantity=1";
    console.log(increaseQuantity);
    fetch(increaseQuantity, { credentials: 'include' })
        .then(response => response.text())
        .then(quantity)
        .then(getproducts(0, 7))
}
function removeCart(text) {
    console.log(text);
    let cartRequest = 'phpapp.php?request=cart';

    // do the fetch
    fetch(cartRequest, { credentials: 'include' })
        .then(response => response.json())
        .then(success)
}
function getproducts(start, length) {
    let productUrl = "phpapp.php?request=productlist&start=" + start + "&length=" + length;
    console.log(productUrl);
    fetch(productUrl, { credentials: 'include' })
        .then(response => response.json())
        .then(printData)
}
/**
 * load eventlistener on window
 */
window.addEventListener("load", function () {

    let categoryUrl = "phpapp.php?request=category";
    console.log(categoryUrl); // debug

    // do the fetch
    fetch(categoryUrl, { credentials: 'include' })
        .then(response => response.json())
        .then(category)

    // get the products of the website
    getproducts(0, 7);

    let cartRequest = 'phpapp.php?request=cart';
    console.log(cartRequest);
    // do the fetch
    fetch(cartRequest, { credentials: 'include' })
        .then(response => response.json())
        .then(success)
});