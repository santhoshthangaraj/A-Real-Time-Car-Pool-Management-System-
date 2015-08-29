<?php
require("config.inc.php");
//initial query
$query = "SELECT * from carinfo where username = (select carowner_username from link where hitchhiker_username=:username)";

$query_params = array(
        ':username' => $_POST['username']
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
    $response["message"] = "Car Info Retreived!";
    $response["cardetails"]   = array(); 
    
    foreach ($rows as $row) {
        $post["carname"]    = $row["Car_Name"];
        $post["carmodel"] = $row["Car_Model"];
        $post["carcolor"]  = $row["Car_Color"];        
        $post["seatingcapacity"] = $row["Seating_Capacity"];      
        
        //update our repsonse JSON data
        array_push($response["cardetails"], $post);
    }
    
    // echoing JSON response
    echo json_encode($response);
    
    
} else {
    $response["success"] = 0;
    $response["message"] = "No Post Available!";
    die(json_encode($response));
}

?>