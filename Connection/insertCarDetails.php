<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {
	//initial query
	$query = "INSERT INTO carinfo(username,Car_Name,Car_Model,License_Plate_No,Car_Color,Seating_Capacity) VALUES ( :username, :carname, :carmodel, :licenseplate, :carcolor, :seatingcapacity)";



    //Update query
    $query_params = array(
        ':username' => $_POST['username'],
        ':carname' => $_POST['carname'],
		':carmodel' => $_POST['carmodel'],
		':licenseplate' => $_POST['licenseplate'],
		':carcolor' => $_POST['carcolor'],
		':seatingcapacity' => $_POST['seatingcapacity']
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
    $response["message"] = "User Details have been saved. Check e-mail for your password";
    echo json_encode($response);
   
} 
?>