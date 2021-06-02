
<?php 

   $con=mysqli_connect("127.0.0.1","root","****","rrs");
   mysqli_set_charset($con,"utf8");
   $data_stream = "'".$_POST['id']."','".$_POST['pw']."','".$_POST['NAME']."','".$_POST['phoneNumber']."','".$_POST['penalty']."','".$_POST['admin']."'";
   
   $query =  "insert into User_inform(id,pw,NAME,phoneNumber, penalty, admin) values (".$data_stream.")";
   $result = mysqli_query($con, $query);
  
   if($result)
      $response = array();
      $response["success"] = true;
 echo json_encode($response);

   mysqli_close($con);
?>