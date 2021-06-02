<?php
	$con=mysqli_connect("127.0.0.1","root","","rrs");
	mysqli_set_charset($con,"utf8");
	
	$res1 = mysqli_query($con,"select * from User_inform");
	$result1 = array();
	
	while($row = mysqli_fetch_array($res1)){
		array_push($result1,
			array('name'=>$row[2],'id'=>$row[0], 'phoneNumber'=>$row[3], 'pw'=>$row[1]));
		
	}

	echo json_encode($result1,JSON_UNESCAPED_UNICODE);	

	mysqli_close($con);