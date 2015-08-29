<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {
	//initial query
	
	$query = " SELECT username,password FROM users WHERE username = :username";
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
        
        if ($_POST['password'] === $row['password']) {
            $login_ok = true;
        }
}
    
if ($login_ok) {
    $response["success"] = 1;
    $response["message"] = "Username Successfully Updated!";
    echo json_encode($response);
    }else{
    $response["success"] = 0;
        $response["message"] = "Invalid Credentials!";
        die(json_encode($response));
    }
   
} 
?>