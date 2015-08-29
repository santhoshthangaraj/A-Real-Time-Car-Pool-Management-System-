<?php
require("config.inc.php");
	
if (!empty($_POST)) {

$query = "DELETE FROM carownertripdetails where username1 = :username";

$query_params = array(
        ':username' => $_POST['username']  );
try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        // For testing, you could use a die and message. 
        //die("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one:
        $response["success"] = 0;
        $response["message"] = "Database Error. Couldn't cancel trip!";
        die(json_encode($response));
    }

    $response["success"] = 1;
    $response["message"] = "Trip has Successfully been Cancelled!";
    echo json_encode($response);
   


 }



?>