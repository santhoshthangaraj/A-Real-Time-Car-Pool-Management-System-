<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) 
{
//$query = "Delete from carinfo where username = :username ;
//INSERT INTO carinfo(username,Car_Name,Car_Model,License_Plate_No,Car_Color,Seating_Capacity) 
//VALUES ( :username, :carname, :carmodel, :licenseplate, :carcolor, :seatingcapacity)";

$query = "UPDATE carinfo SET Car_Name = :carname, Car_Model = :carmodel,  License_Plate_No = :licenseplate,  Car_Color = :carcolor, Seating_Capacity =:seatingcapacity where  username = :username  ";
    //Update query
    $query_params = array(
        ':username' => $_POST['str_username'],
        ':carname' => $_POST['str_carname'],
		':carmodel' => $_POST['str_model'],
		':licenseplate' => $_POST['str_licenseNumber'],
		':carcolor' => $_POST['str_color'],
		':seatingcapacity' => $_POST['str_seating']
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
        $response["message"] = "Database Error. Couldn't update details!";
        die(json_encode($response));
    }

    $response["success"] = 1;
    $response["message"] = "Details Updated!!";
    echo json_encode($response);
   
} 
?>