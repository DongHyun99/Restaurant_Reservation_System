<?php 

	$con=mysqli_connect("127.0.0.1","root","****","rrs");
	mysqli_set_charset($con,"utf8");
	
    $data_stream = "'".$_POST['reservation_num']."','".$_POST['covers']."','".$_POST['DATE']."','".$_POST['TIME']."','".$_POST['table_id']."','".$_POST['customer_id']."'";
	$query = "insert into reservation(reservation_num,covers,DATE,TIME,table_id,customer_id) values (".$data_stream.")";
	$result = mysqli_query($con, $query);
	
   if($result)
      $response = array();
      $response["success"] = true;
 echo json_encode($response);

   mysqli_close($con);
?>