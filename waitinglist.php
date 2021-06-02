
<?php  

$link=mysqli_connect("localhost","root","****", "rrs" );  
if (!$link)  
{  
   echo "MySQL 접속 에러 : ";
   echo mysqli_connect_error();
   exit();  
}  

mysqli_set_charset($link,"utf8"); 


$sql="SELECT name,covers,reservationtime
 FROM onsite WHERE DATE_FORMAT(arrivalday,\"%Y-%m-%d\") = CURDATE() ;";

$result=mysqli_query($link,$sql);
$data = array();   
if($result){  
   
   while($row=mysqli_fetch_array($result)){
       array_push($data, 
           array( 'name'=>$row[0],
 'covers'=>$row[1],
 'reservationtime'=>$row[2]
       ));
   }


header('Content-Type: application/json; charset=utf8');
$json = json_encode(array("webnautes"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
echo $json;

}  
else{  
   echo "SQL문 처리중 에러 발생 : "; 
   echo mysqli_error($link);
} 



mysqli_close($link);  
  
?>