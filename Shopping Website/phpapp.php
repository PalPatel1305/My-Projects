<?php
session_start();

include "callAPI.php";

if (isset($_GET["request"])) {
    $request = $_GET["request"];
    if ($request == "id" && isset($_GET["product"])) {
        // GET THE LIST OF IDS
        $response = callAPI("/webservice/product/" . $_GET["product"], "GET");
        echo json_encode($response["data"]);
    }
    if ($request == "categoryProduct" && isset($_GET["start"]) && isset($_GET["length"]) && isset($_GET["category"])) {
        $response = callAPI("/webservice/product?start=" . $_GET["start"] . "&length=" . $_GET["length"] . "&category=" . $_GET["category"], "GET");
        echo json_encode($response["data"]);
    }
    if ($request == "productlist" && isset($_GET["start"]) && isset($_GET["length"])) {
        $response = callAPI("/webservice/product?start=" . $_GET["start"] . "&length=" . $_GET["length"], "GET");
        echo json_encode($response["data"]);
    }
    if ($request == "category") {
        $response = callAPI("/webservice/product/categories/", "GET");
        echo json_encode($response["data"]);
    }
    if ($_GET["request"] == "cart") {
        $response = callAPI("/webservice/cart/" . $_COOKIE['PHPSESSID'], "GET");
        echo json_encode($response["data"]);
    }
    if ($_GET["request"] == "detail" && isset($_GET["product"])) {
        $response = callAPI("/webservice/product/" . $_GET["product"], "GET");
        echo json_encode($response["data"]);
    }

    if ($_GET["request"] == "quantity" && isset($_GET["product"]) && isset($_GET["updateQuantity"])) {

        $body = json_encode(["product_quantity" => $_GET["updateQuantity"]]);    // requires a body
        $response = callAPI("/webservice/product/quantity/" . $_GET["product"], "PUT", $body);
        echo $response["data"];
        if ($response["response"] == 204) {
            echo "success";
        } else {
            echo "fail " . $response["response"];
        }
    }

    if ($_GET["request"] == "addCart" && isset($_GET["product"])) {
        $body = json_encode(["product_id" => $_GET["product"]]);
        $response = callAPI("/webservice/cart/" . $_COOKIE['PHPSESSID'], "POST", $body);

        if ($response["response"] == 204) {
            echo "success";
        } else {
            echo "fail " . $response["response"];
        }
    }
    if ($_GET["request"] == "removeCart" && isset($_GET["product"])) {
        $body = json_encode(["product_id" => $_GET["product"]]);
        $response = callAPI("/webservice/cart/" . $_COOKIE['PHPSESSID'], "DELETE", $body);

        if ($response["response"] == 204) {
            echo "success";
        } else {
            echo "fail " . $response["response"];
        }
    }
}
