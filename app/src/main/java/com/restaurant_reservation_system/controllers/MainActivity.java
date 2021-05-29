package com.restaurant_reservation_system.controllers;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.CircularArray;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import com.github.tlaabs.timetableview.Schedule;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;
import com.restaurant_reservation_system.R;
import com.restaurant_reservation_system.database.Booking;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private View drawerView;

    static ArrayList<Booking> booking;
    static int max_num=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        booking = new ArrayList<Booking>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread thread = new Thread(runnable);
        thread.start();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerView = (View) findViewById(R.id.drawerView);
        drawerLayout.setDrawerListener(listener);

        Button btnReservation = (Button) findViewById(R.id.btnTimeTable);
        btnReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 예약

                showDate();

            }
        });
        Button btnArrivalTime= (Button) findViewById(R.id.btnArriveTime);
        btnArrivalTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 노쇼 리스트
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });

        Button btnStat= (Button) findViewById(R.id.btnStatistics);
        btnStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 통계
                Intent intent = new Intent(getApplicationContext(), StaticisActivity.class);
                startActivity(intent);
            }
        });
    }

    void showDate() {
        //달력 보여주는 함수
        java.util.Calendar cal = java.util.Calendar.getInstance();
        Intent intent = new Intent(getApplicationContext(), TImeTableActivity.class);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {

            }
        },cal.get(cal.YEAR), cal.get(cal.MONTH), cal.get(cal.DATE));
        datePickerDialog.setButton(
                DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int DAY = datePickerDialog.getDatePicker().getDayOfMonth();
                        int MONTH = datePickerDialog.getDatePicker().getMonth();
                        int YEAR = datePickerDialog.getDatePicker().getYear();
                        intent.putExtra("day", DAY);
                        intent.putExtra("month", MONTH);
                        intent.putExtra("year", YEAR);

                        startActivity(intent);
                    }
                }
        );

        datePickerDialog.setMessage("예약할 날짜를 선택해주세요");
        datePickerDialog.show();

    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
        }

        @Override
        public void onDrawerStateChanged(int newState) {
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                String site = "http://192.168.25.25.25.25/reservation.php";
                URL url = new URL(site);
                //접속
                URLConnection conn = url.openConnection();
                //서버와 연결되어 있는 스트림을 추출
                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                BufferedReader br = new BufferedReader(isr);

                String str = null;
                StringBuffer buf = new StringBuffer();

                do {
                    str = br.readLine();
                    if (str != null) {
                        buf.append(str);
                    }
                } while (str != null);

                String data = buf.toString();  //json 문자열 다 읽어옴

                data=data.replace("[","");
                data=data.replace("]","");
                data=data.replace("{","");
                String []test = data.split("\\},");
                test[test.length-1]=test[test.length-1].replace("}","");
                for(int i=0; i< test.length; i++){
                    test[i]=test[i].replace("\"reservation_num\":","");
                    test[i]=test[i].replace("\"covers\":","");
                    test[i]=test[i].replace("\"date\":","");
                    test[i]=test[i].replace("\"time\":","");
                    test[i]=test[i].replace("\"table_id\":","");
                    test[i]=test[i].replace("\"customer_id\":","");
                    test[i]=test[i].replace("\"arrivalTime\":","");
                    test[i]=test[i].replace("\"","");
                    String inform[]=test[i].split(",");
                    booking.add(new Booking(inform[0],inform[1],inform[2],inform[3],inform[4],inform[5],inform[6]));
                    if (Integer.parseInt(inform[0])>max_num) max_num = Integer.parseInt(inform[0])+1;
                }

                TimetableView timetable = (TimetableView) findViewById(R.id.timetable);
                ArrayList<Schedule> schedules = new ArrayList<Schedule>();
                for (int i =0;i<booking.size();i++){
                    String time[] = booking.get(i).getTime().split(":");
                    Schedule schedule = new Schedule();
                    schedule.setClassTitle(booking.get(i).getReservation_num()); // sets subject
                    schedule.setClassPlace(booking.get(i).getCustomer_id()); // sets place
                    schedule.setStartTime(new Time(Integer.parseInt(time[0]),Integer.parseInt(time[1]))); // sets the beginning of class time (hour,minute)
                    schedule.setEndTime(new Time(Integer.parseInt(time[0])+1,Integer.parseInt(time[1]))); // sets the end of class time (hour,minute)
                    schedule.setDay(Integer.parseInt(booking.get(i).getTable_id()));
                    schedules.add(schedule);
                }
                timetable.add(schedules);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };
}