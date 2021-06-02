<?php 

   $con=mysqli_connect("127.0.0.1","root","****","rrs");
   mysqli_set_charset($con,"utf8");
   $data_stream="'".$_POST['covers']."',time = '".$_POST['time'].
   "', table_id = '".$_POST['table_id']."' WHERE reservation_num = '".$_POST['reservation_num']."'";
   
   $query = "UPDATE reservation SET covers = ".$data_stream;
   $result = mysqli_query($con, $query);
  
   if($result)
      $response = array();
      $response["success"] = true;
 echo json_encode($response);

   mysqli_close($con);
?>
