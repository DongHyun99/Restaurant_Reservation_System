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
```DB를 사용하여 로그인```

- LoginActivity, Register Activity 수정
- 로그인에서 회원가입 페이지로, 회원가입에서 로그인 페이지로 갈수 있도록 구현
- 연결된 DB에서 USER_INFORM에 대한 정보를 추출하여 ArrayList에 저장하고 이를통해 로그인
- PHP에 입력시 DB 테이블에 실시간으로 Inset
- 로그인 실패시 Alert

05/21  
```회원 가입시 DB에 값을 추가하는 INSERT Request 생성```

- 회원가입 정보를 INSERT 하는 PHP 구현
- PHP에 값을 입력하는 Request 구현, 값은 HashMap을 사용해서 전달
- JSON과 연동하는 response 구문 추가

05/22  
```예약 정보 확인을 위한 TimeTableView Activity, xml 생성 (오픈소스 라이브러리 활용)```

- 에브리타임에서 사용하는 시간표 라이브러리를 import하여 사용
[![image](https://user-images.githubusercontent.com/79196616/120608275-f8e22300-c48b-11eb-8635-edae3fab5198.png)](https://github.com/tlaabs/TimetableView)
- 타임 테이블에 대한 레이아웃 생성
- 타임 테이블에 값을 추가하고 DB에 값을 추가하는 구문 추가

05/24  
```SQL query문 수정, Edit_Timtable xml 생성```

- user_inform table에 관리자인지를 판별할 수 있는 admin, 노쇼 기능이 활성화되어있는지를 알수 있는 penalty 값 추가
- 타임 테이블을 수정하는 레이아웃 추가 (xml만 구현, Activity는 구현하지 못함..)

05/29  
```noShow 기능 구현, 통계 기능 구현```

- 도착 시간을 입력하면 늦었는지를 판별하여 DB의 penalty를 활성화 해주는 기능 구현
- 얼마만큼의 사람이 예약 했는지 등을 확인 할수 있는 통계 기능 구현
- putExtra, getExtra로 각 Activity끼리 값을 전달 할수 있게 refactoring (기존의 static 변수들 삭제)

05/30  
```editTimetable Activity 구현중 (1), drawer layout 추가, 런치 메뉴 수정```

- timetable 수정 및 삭제 기능 구현 1일차
- drawer Layout을 활용하여 메인 화면에서 왼쪽 화면을 끌어당기면 사용자의 정보를 볼수 있는 기능 구현
- 런치 메뉴 UI에 맞게 수정

05/31  
```editTimetable Activity 구현중 (2), 관리자용 / 고객용 UI 분리, drawer layout 수정```

- timetable 수정 및 삭제 기능 구현 2일차
- 관리자와 고객용 Activity_main.xml 파일 분리
- 통계부분 ui 수정, 회원가입 성공 시 토스트 메시지 출력, 회원가입 시 빈칸이 있으면 빈칸을 채우도록 수정
- drawer layout bugFix

06/01  
```editTimetable Activity 구현중 (3), noShow 기능 complete```

- timetable 수정 및 삭제 기능 구현 3일차
- noShow 기능 구현 완료, penalty가 true인 사용자 예약 거부 기능
- drawer layout 관련 코드 refactoring

06/02  
```editTimetable Activity 구현중 (4), 도착기록 리스트, 대기 리스트 구현```

- timetable 수정 및 삭제 기능 구현 4일차
- 도착기록 리스트 구현 (노쇼에서 값을 받아올 수 있도록)
- 대기 리스트 구현

06/03  
```editTimetable Activity 구현완료, 뒤로 가기 버튼 누를때 최신화 하기, timeTable bugfix```

- timeTable 수정 및 삭제 기능 구현 complete
- 뒤로가기버튼 누를 시 이전 엑티비티의 기록만을 보여주기 때문에 이를 보완 할수 있는 방안 구현
- 타임 테이블의 테이블이 한칸씩 밀리는 bugFix, 도착 시간의 초기값이 null이였는데 "00:00"으로 수정

# Samples  
## launch & login UI  
![GOMCAM 20210509_1619460123](https://user-images.githubusercontent.com/79196616/117564023-cf301900-b0e4-11eb-9b03-4e2a02a62227.gif)  

## PHP connect
![image](https://user-images.githubusercontent.com/79196616/118366782-6ad8f200-b5db-11eb-80ca-be562c064f16.png)

##Insert into DataBase  
![GOMCAM 20210522_1204260702](https://user-images.githubusercontent.com/79196616/119228717-433fd780-bb4f-11eb-8b0a-dc4237fb9092.png)

##TimeTableView  
![GOMCAM 20210522_1131220565](https://user-images.githubusercontent.com/79196616/119228737-5488e400-bb4f-11eb-9293-75c271927b04.png)

