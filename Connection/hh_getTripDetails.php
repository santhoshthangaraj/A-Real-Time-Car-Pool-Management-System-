

<?php
require("config.inc.php");
//initial query
$query = "SELECT carownertripdetails.username as nUserName, carownertripdetails.route as nRoute, carownertripdetails.timing as nTiming, carinfo.Seating_Capacity as nSeating FROM carownertripdetails left join carinfo on carownertripdetails.username = carinfo.username where Start_Point like '%' and End_Point like '%'";

//$query = "SELECT * FROM users";
//execute query
$query_params = array(
        ':start_point' => $_POST['start_point'] ,       
        ':end_point' => $_POST['end_point']        
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
    $response["message"] = "Trips Info Retreived!";
    $response["tripdetails"]   = array();
    
    foreach ($rows as $row) {
        $post["username"]    = $row["nUserName"];
        $post["route"] = $row["nRoute"];
        $post["timing"]  = $row["nTiming"];        
        $post["seatingcapacity"] = $row["nSeating"];      
        
        //update our repsonse JSON data
        array_push($response["tripdetails"], $post);
    }
    
    // echoing JSON response
    echo json_encode($response);
    
    
} else {
    $response["success"] = 0;
    $response["message"] = "No Post Available!";
    die(json_encode($response));
}

?>