<?php
require("config.inc.php");
	
if (!empty($_POST)) {

$query = " 
update carinfo set Seating_Capacity = Seating_Capacity + 1 where username = (select carowner_username from link where hitchhiker_username = :username);
DELETE FROM hitchhikertripdetails where username = :username  ; 
DELETE FROM link where hitchhiker_username = :username  ;
";

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