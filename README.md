# Restaurant_Reservation_System

SE 텀프로젝트 레스토랑 예약 시스템의 repository입니다.

### Log  
5/09   
``` 오픈소스를 참고하여 예약시스템 Lauch, Login UI 구현 (https://github.com/mentesnot/Android-Flight-Reservation-App)```

  - Main, Launch, Login, RegisterActivity와 DatabaseHelper, HelperUtilities Java 파일 구현
  - Launch와 Login에 대한 Layout Activity 구현
  - themes, AndroidManifest.xml 수정
  - 구형 v7 compat -> androidx 통합 라이브러리로 수정
  - 전체화면 깨지는 bug fix

05/16  
``` DB - PHP - Android 연결```

  - LoginActivity 수정, DatabaseHelper 삭제
  - Xampp를 이용해 Apache 서버를 생성
  - 서버에 MariaDB와 연결된 데이터를 PHP파일로 웹 생성
  - PHP 파일을 json으로 받아와 Android에 연결

05/19
```DB를 사용하여 로그인 / 회원가입 정보를 DB에 INSERT```

- LoginActivity, Register Activity 수정
- 로그인에서 회원가입 페이지로, 회원가입에서 로그인 페이지로 갈수 있도록 구현
- 연결된 DB에서 USER_INFORM에 대한 정보를 추출하여 ArrayList에 저장하고 이를통해 로그인
- PHP에 입력시 DB 테이블에 실시간으로 Inset

### Next Week  
```Insert PHP 합치기 / TimetableView DB 연결 ```

  - Insert PHP를 안드로이드와 연동
  - TimeTableView에서 PHP 정보를 받아와 table을 받아오는 method 구현

# Samples  
## launch & login UI  
![GOMCAM 20210509_1619460123](https://user-images.githubusercontent.com/79196616/117564023-cf301900-b0e4-11eb-9b03-4e2a02a62227.gif)  

## PHP connect
![image](https://user-images.githubusercontent.com/79196616/118366782-6ad8f200-b5db-11eb-80ca-be562c064f16.png)

##Insert into DataBase  
![GOMCAM 20210522_1204260702](https://user-images.githubusercontent.com/79196616/119228717-433fd780-bb4f-11eb-8b0a-dc4237fb9092.png)

##TimeTableView  
![GOMCAM 20210522_1131220565](https://user-images.githubusercontent.com/79196616/119228737-5488e400-bb4f-11eb-9293-75c271927b04.png)


