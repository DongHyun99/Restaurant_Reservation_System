<?php
	$con=mysqli_connect("127.0.0.1","root","****","rrs");
	mysqli_set_charset($con,"utf8");
	
	$res2 = mysqli_query($con,"select * from ttable");
	$result2 = array();
	
	while($row = mysqli_fetch_array($res2)){
		array_push($result2,
			array('number'=>$row[0],'places'=>$row[1]));
		
	}		
	echo json_encode($result2,JSON_UNESCAPED_UNICODE);
	
	mysqli_close($con);