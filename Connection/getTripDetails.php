<?php

/*
Our "config.inc.php" file connects to database every time we include or require
it within a php script.  Since we want this script to add a new user to our db,
we will be talking with our database, and therefore,
let's require the connection to happen:
*/
require("config.inc.php");

//initial query
//$query = "Select username, route,timing FROM carownertripdetails where  Start_Point= 'UT Drive'and End_Point='IKEA store'";
$query = "Select  username,route ,timing ,seating_capacity FROM carownertripdetails , carinfo where username1 = username and Start_Point= :start_point and End_Point= :end_point";
$query_params = array(
        ':start_point' => $_POST['start_point'],
        ':end_point' => $_POST['end_point'],        
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
    $response["route"]   = array();
    
    foreach ($rows as $row) {
        $post             = array();
        $post["username"]    = $row["username"];
        $post["route"]    = $row["route"];                
        $post["timing"]    = $row["timing"];
        $post["seating_capacity"] = $row["seating_capacity"];
        
        //update our repsonse JSON data
        array_push($response["route"], $post);
    }
    
    // echoing JSON response
    echo json_encode($response);
    
    
} else {
    $response["success"] = 0;
    $response["message"] = "No Hitchhikers Registered!";
    die(json_encode($response));
}

?>