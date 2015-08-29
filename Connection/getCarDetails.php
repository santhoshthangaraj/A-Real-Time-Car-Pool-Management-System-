<?php
require("config.inc.php");
//initial query
$query = "Select * FROM carinfo where username = 'ankita2'";
//execute query

try {
    $stmt   = $db->prepare($query);
    $result = $stmt->execute();
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
        $post["username"]    = $row["username"];
        $post["carno"] = $row["Car_Name"];
        $post["carmodel"]  = $row["Car_Model"];
        $post["licenseplateno"] = $row["License_Plate_No"];        
        $post["carcolor"] = $row["Car_Color"];        
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
