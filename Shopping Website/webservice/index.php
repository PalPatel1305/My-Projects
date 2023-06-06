<?PHP
include "connect.php";

$method = $_SERVER['REQUEST_METHOD'];
$path = $_SERVER['REQUEST_URI'];

if (isset($path)) {
    $found = strpos($path, '/ASSIGNMENT3/webservice/cart/');
    if ($found === 0) {
        $session_id = substr($path, strlen('/ASSIGNMENT3/webservice/cart/'));
        if ($method == "GET") {
            $command = "SELECT product_id, product_name,product_price FROM catalogue c Join session s ON s.session_data = c.product_id Where s.session_id = ?";
            $cursor = $dbh->prepare($command);
            $cursor->execute([$session_id]);
            $cartdata = [];
            while ($row = $cursor->fetch()) {
                http_response_code(200);
                array_push($cartdata, ["product_id" => $row["product_id"], "product_name" => $row["product_name"], "product_price" => $row["product_price"]]);
            }
            echo json_encode($cartdata);
        } else{
            http_response_code(405);
        }
        
        if ($method == "POST") {

            $data = json_decode(file_get_contents('php://input'), true);
            if (isset($data['product_id'])) {
                $addCommand = "INSERT INTO session(session_id,session_data) VALUES (?,?)";
                $addStmt = $dbh->prepare($addCommand);
                $addStmt->execute([$session_id, $data['product_id']]);
                http_response_code(204);
            } else {
                http_response_code(304);
            }
        }else{
            http_response_code(405);
        }
        
        
        if ($method == "DELETE") {
            $data = json_decode(file_get_contents('php://input'), true);

            if (isset($data['product_id'])) {
                $removeCommand = "DELETE FROM session Where session_id = ? AND session_data = ? LIMIT 1";
                $removeCommand = $dbh->prepare($removeCommand);
                $removeCommand->execute([$session_id, $data['product_id']]);
                http_response_code(204);
            } else {
                http_response_code(304);
            }
        } else {
            http_response_code(405);
        }
    }
}
if (isset($path)) {
    $found = strpos($path, '/ASSIGNMENT3/webservice/product/quantity/');
    if ($found === 0) {
        $product_id = substr($path, strlen('/ASSIGNMENT3/webservice/product/quantity/'));
        if ($method == "PUT") {
            $data = json_decode(file_get_contents('php://input'), true);
            if (isset($data["product_quantity"])) {
                $cmd = "UPDATE catalogue SET product_quantity=product_quantity+? WHERE product_id= ?";
                $stmt = $dbh->prepare($cmd);
                $params = [$data["product_quantity"], $product_id];
                $stmt->execute($params);
                http_response_code(204);
            } else {
                http_response_code(404);
            }
        } else {
            http_response_code(405);
        }
    }
}
if ($path == "/ASSIGNMENT3/webservice/product/categories/") {
    if ($method == "GET") {
        $command = "SELECT DISTINCT product_category FROM catalogue";
        $cursor = $dbh->query($command);
        $categories = [];
        while ($row = $cursor->fetch()) {

            array_push($categories, $row["product_category"]);
            http_response_code(200);
        }
        echo json_encode($categories);
    } else {
        http_response_code(405);
    }
} else {
    $found = strpos($path, '/ASSIGNMENT3/webservice/product');
    if ($found === 0) {
        if ($method=="GET" && isset($_GET["start"]) && isset($_GET["length"])) {
            $start = $_GET["start"];
            $length = $_GET["length"];
            if (isset($_GET["category"])) {
                $category = $_GET["category"];
                $query = "SELECT product_id,product_name,product_description,product_price,product_quantity FROM catalogue WHERE product_category='$category' LIMIT $start,$length";
                $stmt = $dbh->query($query);
                $productlist = [];
                while ($row = $stmt->fetch()) {
                    if ($row == null) {
                        http_response_code(404);
                    }
                    http_response_code(200);
                    array_push($productlist, ["product_id" => $row["product_id"], "product_name" => $row["product_name"], "product_description" => $row["product_description"], "product_price" => $row["product_price"], "product_quantity" => $row["product_quantity"], "last_product_number" => 0]);
                }
                echo json_encode($productlist);
            } else {
                    $query = "SELECT product_id,product_name,product_description,product_price,product_quantity FROM catalogue LIMIT $start,$length";
                    $stmt = $dbh->query($query);
                    $productlist = [];
                    while ($row = $stmt->fetch()) {
                        if ($row == null) {
                            http_response_code(404);
                        }
                        http_response_code(200);
                        array_push($productlist, ["product_id" => $row["product_id"], "product_name" => $row["product_name"], "product_description" => $row["product_description"], "product_price" => $row["product_price"], "product_quantity" => $row["product_quantity"], "last_product_number" => $start + $length]);
                    }
                
                echo json_encode($productlist);
            }
        } else {

            $product_id = substr($path, strlen('/ASSIGNMENT3/webservice/product/'));

            if ($method == "GET") {
                $command = "SELECT product_id,product_name,product_description,product_price,product_quantity,product_category  FROM catalogue Where product_id=?";

                $stmt = $dbh->prepare($command);
                $params = [$product_id];
                $success = $stmt->execute($params);
                $products = [];
                while ($row = $stmt->fetch()) {
                    if ($row == null) {
                        http_response_code(404);
                    }
                    http_response_code(200);
                    array_push($products, ["product_id" => $row["product_id"], "product_name" => $row["product_name"], "product_description" => $row["product_description"], "product_price" => $row["product_price"], "product_quantity" => $row["product_quantity"], "product_category" => $row["product_category"]]);
                }
                echo json_encode($products);
            } else {
                http_response_code(405);
            }
        }
    }
}