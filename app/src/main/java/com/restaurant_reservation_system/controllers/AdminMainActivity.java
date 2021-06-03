package com.restaurant_reservation_system.controllers;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.restaurant_reservation_system.R;
import com.restaurant_reservation_system.database.Booking;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class AdminMainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private View drawerView;

    static ArrayList<Booking> booking;
    static int max_num = 0;
    private TextView name;
    String u_date;
    String u_time;
    String u_covers;
    String getStringId;
    String getStringName;
    TextView date;
    TextView time;
    TextView covers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        booking = new ArrayList<Booking>();
        Thread thread = new Thread(runnable);
        thread.start();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        inform_search();

        drawerLayout = (DrawerLayout) findViewById(R.id.admin_main_layout);
        drawerView = (View) findViewById(R.id.drawerView);
        drawerLayout.setDrawerListener(listener);

        Button btnOnSite = findViewById(R.id.btnOnSite);
        btnOnSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//현장예약
                showDate2();
                //Intent intent2 = new Intent(AdminMainActivity.this, OnSiteReservationActivity.class);
                // startActivity(intent2);

            }
        });
        //현장예약 버튼

        Button btnArrivalTime = (Button) findViewById(R.id.noShow_btn);
        btnArrivalTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 노쇼 리스트
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });

        Button btnStat = (Button) findViewById(R.id.btnStatistics);
        btnStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 통계
                Intent intent = new Intent(getApplicationContext(), StaticisActivity.class);
                startActivity(intent);
            }
        });

        Button btnArrival = (Button) findViewById(R.id.ArrivalList_btn);
        btnArrival.setOnClickListener(new View.OnClickListener() {// 도착기록 리스트
            @Override
            public void onClick(View v) { // 도착기록 리스트
                showDate();
            }
        });
    }

    private void inform_search() {

        getStringName = getIntent().getStringExtra("name");
        name = (TextView) findViewById(R.id.names);
        name.setText(getStringName + " 님");

        boolean success = match();
        if (success) {
            date = (TextView) findViewById(R.id.date);
            date.setText("날짜     " + u_date);

            time = (TextView) findViewById(R.id.times);
            time.setText("시간     " + u_time);

            covers = (TextView) findViewById(R.id.covers);
            covers.setText("인원 수     " + u_covers);
        } else {
            date = (TextView) findViewById(R.id.date);
            date.setText("아직 예약을 하지 않았습니다.");

            time = (TextView) findViewById(R.id.times);
            time.setText("아직 예약을 하지 않았습니다.");

            covers = (TextView) findViewById(R.id.covers);
            covers.setText("아직 예약을 하지 않았습니다.");
        }
    }

    public boolean match() {
        getStringId = getIntent().getStringExtra("id");

        for (int i = 0; i < booking.size(); i++) {
            if (getStringId.equals(booking.get(i).getCustomer_id())) {
                u_date = booking.get(i).getDate();
                u_time = booking.get(i).getTime();
                u_covers = booking.get(i).getCovers();
                return true;
            }
        }
        return false;
    }

    void showDate() {
        //달력 보여주는 함수
        java.util.Calendar cal = java.util.Calendar.getInstance();
        Intent intent = new Intent(getApplicationContext(), ArrivalListActivity.class);
        intent.putExtra("id", getStringId);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {

            }
        }, cal.get(cal.YEAR), cal.get(cal.MONTH), cal.get(cal.DATE));
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
                        // intent.putExtra("maxNum", max_num);

                        startActivity(intent);
                    }
                }
        );

        datePickerDialog.setMessage("날짜를 선택해주세요");
        datePickerDialog.show();

    }
    void showDate2() {
        //달력 보여주는 함수
        java.util.Calendar cal = java.util.Calendar.getInstance();
        Intent intent = new Intent(getApplicationContext(), OnSiteReservationActivity.class);
        intent.putExtra("id", getStringId);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {

            }
        }, cal.get(cal.YEAR), cal.get(cal.MONTH), cal.get(cal.DATE));
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
                        // intent.putExtra("maxNum", max_num);

                        startActivity(intent);
                    }
                }
        );

        datePickerDialog.setMessage("날짜를 선택해주세요");
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
                String site = "http://192.168.25.25/reservation.php";
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

                data = data.replace("[", "");
                data = data.replace("]", "");
                data = data.replace("{", "");
                String[] test = data.split("\\},");
                test[test.length - 1] = test[test.length - 1].replace("}", "");
                for (int i = 0; i < test.length; i++) {
                    test[i] = test[i].replace("\"reservation_num\":", "");
                    test[i] = test[i].replace("\"covers\":", "");
                    test[i] = test[i].replace("\"date\":", "");
                    test[i] = test[i].replace("\"time\":", "");
                    test[i] = test[i].replace("\"table_id\":", "");
                    test[i] = test[i].replace("\"customer_id\":", "");
                    test[i] = test[i].replace("\"arrivalTime\":", "");
                    test[i] = test[i].replace("\"", "");
                    String inform[] = test[i].split(",");
                    booking.add(new Booking(inform[0], inform[1], inform[2], inform[3], inform[4], inform[5], inform[6]));
                    if (Integer.parseInt(inform[0]) >= max_num) max_num = Integer.parseInt(inform[0]) + 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };

}



