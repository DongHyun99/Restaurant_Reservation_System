<?php
	$con=mysqli_connect("127.0.0.1","root","****","rrs");
	mysqli_set_charset($con,"utf8");
	
	$res1 = mysqli_query($con,"select * from User_inform");
	$res2 = mysqli_query($con,"select * from ttable");
	$result1 = array();
	$result2 = array();
	
	while($row = mysqli_fetch_array($res1)){
		array_push($result1,
			array('name'=>$row[0],'id'=>$row[1], 'phoneNumber'=>$row[2], 'pw'=>$row[3]));
		
	}		
	echo json_encode($result1,JSON_UNESCAPED_UNICODE);
	echo('</br>');
	while($row = mysqli_fetch_array($res2)){
		array_push($result2,
			array('number'=>$row[0],'places'=>$row[1]));
		
	}		
	echo json_encode($result2,JSON_UNESCAPED_UNICODE);
	
	mysqli_close($con);