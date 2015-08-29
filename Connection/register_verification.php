<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {
	

     //Update query
    $query_params = array(
    ':username' => $_POST['username'],
    ':password' => $_POST['password'],
	':firstname' => $_POST['firstname'],
	':lastname' => $_POST['lastname'],
	':dateofbirth' => $_POST['dateofbirth'],
	':unccmailid' => $_POST['unccmailid']
    );

//initial query
	$query = "INSERT INTO users(username,password) VALUES ( :username, :password);
	INSERT Into userinfo (username, First_name, Last_name, DateofBirth, UNCC_Mail_ID) values (:username, :firstname, :lastname, :dateofbirth, :unccmailid);" ;

  
	//execute query
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);

   $to = htmlspecialchars($_POST['unccmailid']);
   $subject = "Verification Mail";
   $message = "Hello ".htmlspecialchars($_POST['firstname']). " \n Your password for using the KARS Pool Services is " .htmlspecialchars($_POST['password'])." \n Use this password at the time of login. \n\n Regards,\n KARS Team";
   $headers = "From: admin@karspool.com";   
    mail($to,$subject,$message,$headers);
   
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