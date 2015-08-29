<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {
	//initial query
	$query = " SELECT count(*) from  users WHERE 
                username = :username,
                password = :password 
        ";



    //Update query
    $query_params = array(
        ':username' => $_POST['username'],
        ':carname' => $_POST['password']
		
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
        $response["message"] = "Details already exist for the user. Try updating the details";
        die(json_encode($response));
    }

  
    $response["success"] = 1;
    $response["message"] = "Login Successful";
    echo json_encode($response);
   
} 
?>