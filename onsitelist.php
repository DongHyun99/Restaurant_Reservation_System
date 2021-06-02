<?php
	$con=mysqli_connect("127.0.0.1","root","","rrs");
	mysqli_set_charset($con,"utf8");
	
	$res1 = mysqli_query($con,"select * from onsite");
	$result1 = array();
	
	while($row = mysqli_fetch_array($res1)){
		array_push($result1,
			array('number'=>$row[0], 'name'=>$row[1], 'phone'=>$row[2], 'arrivalday'=>$row[3],'arrivaltime'=>$row[4],'covers'=>$row[5], 'reservationtime'=>$row[6]));		
	}

	echo json_encode($result1,JSON_UNESCAPED_UNICODE);	

	mysqli_close($con);