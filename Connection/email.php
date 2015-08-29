<?php
   $to = "bhatnagar.ankita2209@gmail.com";
   $subject = "IMPORTANT NOTICE";
   $message = "test message";
   $headers = "From: ";   
   $retval = mail ($to,$subject,$message,$headers);
   if( $retval == true )  
   {
      echo "Message sent successfully...";
   }
   else
   {
      echo "Message could not be sent...";
   }
?>