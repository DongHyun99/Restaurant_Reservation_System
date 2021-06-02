<?php
	$con=mysqli_connect("127.0.0.1","root","****","rrs");
	mysqli_set_charset($con,"utf8");
	
	$res1 = mysqli_query($con,"select * from Reservation");
	$result1 = array();
	
	while($row = mysqli_fetch_array($res1)){
		array_push($result1,
			array('reservation_num'=>$row[0],'covers'=>$row[1],'date'=>$row[2], 'time'=>$row[3], 'table_id'=>$row[4], 'customer_id'=>$row[5], 'arrivalTime'=>$row[6]));
		
	}

	echo json_encode($result1,JSON_UNESCAPED_UNICODE);	

	mysqli_close($con);