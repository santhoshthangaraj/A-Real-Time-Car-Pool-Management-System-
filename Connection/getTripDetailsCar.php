<?php


require("config.inc.php");

//initial query
//$query = "Select username, route,timing FROM carownertripdetails where  Start_Point= 'UT Drive'and End_Point='IKEA store'";
$query = "Select  username,route ,timing ,seating_capacity, start_point,end_point FROM carownertripdetails , carinfo where username1 = username and License_Plate_No= :license_plate_number and seating_capacity > 0";
$query_params = array(

        ':license_plate_number' => $_POST['car_number']
               
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
        $post["start_point"] = $row["start_point"];
        $post["end_point"] = $row["end_point"];
        
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