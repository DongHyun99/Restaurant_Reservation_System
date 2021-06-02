<?php 

   $con=mysqli_connect("127.0.0.1","root","","rrs");
   mysqli_set_charset($con,"utf8");
   $data_stream = "'".$_POST['name']."','".$_POST['phone']."','".$_POST['arrivalday']."','".$_POST['arrivaltime']."','".$_POST['covers']."'";
   
   $query =  "insert into Onsite(name,phone,arrivalday,arrivaltime,covers) values (".$data_stream.")";
   $result = mysqli_query($con, $query);
  
   if($result)
      $response = array();
      $response["success"] = true;
 echo json_encode($response);

   mysqli_close($con);
?>

