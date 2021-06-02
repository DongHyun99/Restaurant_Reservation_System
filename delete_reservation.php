<?php 
	$con = mysqli_connect("localhost","root","****","rrs");
	if (!$con){
		die('Could not connect: ' . mysql_error());
	}
	$query = "DELETE FROM reservation WHERE reservation_num=".$_POST['reservation_num'];
	$result = mysqli_query($con, $query);
	if($result)
		$response = array();
		$response["success"] = true;
	echo json_encode($response);

mysqli_close($con);
?>
