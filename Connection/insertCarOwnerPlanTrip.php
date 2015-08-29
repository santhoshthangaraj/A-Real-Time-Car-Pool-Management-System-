<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {
	//initial query
	$query = "INSERT INTO carownertripdetails(username1,start_point,end_point,route,timing) VALUES ( :username, :start_point, :end_point, :route, :timing)";

    //Update query
    $query_params = array(
        ':username' => $_POST['username'],
        ':start_point' => $_POST['start_point'],
		':end_point' => $_POST['end_point'],
		':route' => $_POST['route'],
		':timing' => $_POST['timing']
    );
  
	//execute query
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        // For testing, you could use a die and message. 
        //die("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one:
        $response["success"] = 0;
        $response["message"] = "Database Error. Couldn't add trip details!";
        die(json_encode($response));
    }

    $response["success"] = 1;
    $response["message"] = "Trip has Successfully been Planned!";
    echo json_encode($response);
   
} 
?>