<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {
	//initial query
$query = " 
INSERT INTO hitchhikertripdetails(`username`, `Start_Point`, `End_Point`, `Route`, `Timing`) VALUES (:username, :start_point,:end_point, :route, :timing);
update carinfo set Seating_Capacity = Seating_Capacity - 1 where username = :parent_username_str and Seating_Capacity > 0;
INSERT INTO link( `carowner_username`, `hitchhiker_username`) VALUES ( :parent_username_str,:username);
";


    //Update query
    $query_params = array(
            ':username' => $_POST['username'],
            ':parent_username_str' => $_POST['parent_username_str'],
            ':route' => $_POST['route'],
            ':timing' => $_POST['timing'],
            ':start_point' => $_POST['start_point'],
            ':end_point' => $_POST['end_point']
		
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
    $response["message"] = "User Details have been saved.";
    echo json_encode($response);
   
} 
?>