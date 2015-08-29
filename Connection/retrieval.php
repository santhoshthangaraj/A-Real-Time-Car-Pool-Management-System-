<?php


require("config.inc.php");


if (!empty($_POST)) {

$query = "SELECT * FROM carownertripdetails as cotd, userinfo as ui WHERE cotd.username = ui.username and cotd.username = :username ;";

$query_params = array(
        ':username' => $_POST['username'] );
        
//execute query
try {
    $stmt   = $db->prepare($query);
    $result = $stmt->execute($query_params);
}

catch (PDOException $ex) {
    $response["success"] = 0;
    $response["message"] = "Database Error!";
    die(json_encode($response));
}


$rows = $stmt->fetchAll();


if ($rows) {
    $response["success"] = 1;
    $response["message"] = "Users Available!";
    $response["users"]   = array();
    
    foreach ($rows as $row) {
        $post             = array();
        $post["id"]    = $row["id"];
        $post["username"] = $row["username"];
        $post["password"]  = $row["password"];
        
        
        //update our repsonse JSON data
        array_push($response["users"], $post);
    }
    
    echo json_encode($response);
    
    
} else {
    $response["success"] = 0;
    $response["message"] = "No Post Available!";
    die(json_encode($response));
}
}

?>