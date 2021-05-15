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

### Next Week  
```회원가입, DB 확장, TimeTableView, Calendar 합치기 ```

  - DB를 사용자 정보에 대한 ArrayList 형식으로 받아오기만 했기 때문에 나머지 작업도 추가로 필요
  - 다른 팀원들이 가져온 파일들과 합치기

# Samples  
## launch & login UI  
![GOMCAM 20210509_1619460123](https://user-images.githubusercontent.com/79196616/117564023-cf301900-b0e4-11eb-9b03-4e2a02a62227.gif)  

## PHP connect
![image](https://user-images.githubusercontent.com/79196616/118366782-6ad8f200-b5db-11eb-80ca-be562c064f16.png)
