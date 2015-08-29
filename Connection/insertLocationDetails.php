<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {
	//initial query
	
	$query = " SELECT 1 FROM Location WHERE username = :username";
    //now lets update what :user should be
    $query_params = array(
        ':username' => $_POST['username']
    );
    
    //Now let's make run the query:
    try {
        // These two statements run the query against your database table. 
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        // For testing, you could use a die and message. 
        //die("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one to product JSON data:
        $response["success"] = 0;
        $response["message"] = "Database Error 1st. Please Try Again!";
        die(json_encode($response));
    }
    
    //fetch is an array of returned data.  If any data is returned,
    //we know that the username is already in use, so we murder our
    //page
    $row = $stmt->fetch();
    if ($row) {
        
        $query = "update Location set latitude=:latitude,longitude=:longitude where username=:username";
        
        $query_params = array(
        ':username' => $_POST['username'],
        ':latitude' => $_POST['latitude'],
		':longitude' => $_POST['longitude']
    );
    
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        // For testing, you could use a die and message. 
        //die("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one:
        $response["success"] = 0;
        $response["message"] = "Database Error 2nd. Couldn't add post!";
        die(json_encode($response));
    }

    $response["success"] = 1;
    $response["message"] = "Username Successfully Updated!";
    echo json_encode($response);
    
        $response["success"] = 0;
        $response["message"] = "I'm sorry, this username is already in use";
        die(json_encode($response));
    }
    else
    {
	$query = "INSERT INTO Location(username,latitude,longitude) VALUES ( :username, :latitude, :longitude)";

    //Update query
    $query_params = array(
        ':username' => $_POST['username'],
        ':latitude' => $_POST['latitude'],
		':longitude' => $_POST['longitude']
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
        $response["message"] = "Database Error 3rd. Couldn't add post!";
        die(json_encode($response));
    }

    $response["success"] = 1;
    $response["message"] = "Username Successfully Added!";
    echo json_encode($response);
    }
   
} 
?>