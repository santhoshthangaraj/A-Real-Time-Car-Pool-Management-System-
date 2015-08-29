<?php

/*
Our "config.inc.php" file connects to database every time we include or require
it within a php script.  Since we want this script to add a new user to our db,
we will be talking with our database, and therefore,
let's require the connection to happen:
*/
require("config.inc.php");

//initial query
$query = "select seating_capacity from carinfo where username='rbendre'";
$query_params = array(
        ':username' => $_POST['username']      
    );

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

// Finally, we can retrieve all of the found rows into an array using fetchAll 
$rows = $stmt->fetchAll();


if ($rows) {
    $response["success"] = 1;
    $response["message"] = "Route Info Retreived!";
    $response["vacancy"]   = array();
    
    foreach ($rows as $row) {
        $post             = array();
        $post["vacancy"]    = $row["seating_capacity"];
        
        //update our repsonse JSON data
        array_push($response["acancy"], $post);
    }
    
    // echoing JSON response
    echo json_encode($response);
    
    
} else {
    $response["success"] = 0;
    $response["message"] = "No Hitchhikers Registered!";
    die(json_encode($response));
}

?>
