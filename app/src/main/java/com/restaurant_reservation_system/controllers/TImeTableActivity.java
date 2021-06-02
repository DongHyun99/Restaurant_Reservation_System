package com.restaurant_reservation_system.controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.restaurant_reservation_system.R;
import com.github.tlaabs.timetableview.Time;
import com.github.tlaabs.timetableview.TimetableView;
import com.github.tlaabs.timetableview.Schedule;
import com.restaurant_reservation_system.database.Booking;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class TImeTableActivity extends AppCompatActivity {

    static ArrayList<Booking> booking;
    Intent intent;
    String user_Penalty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        user_Penalty = getIntent().getStringExtra("penalty");
        booking = new ArrayList<Booking>();
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_timetable);
        final View myLayout = findViewById(R.id.main);
        Thread thread = new Thread(runnable);
        thread.start();

        Button add_btn = (Button) findViewById(R.id.add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_Penalty.equals("F")) {
                    Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                    intent.putExtra("id", getIntent().getStringExtra("id"));
                    intent.putExtra("day", getIntent().getIntExtra("day", -1));
                    intent.putExtra("month", getIntent().getIntExtra("month", -1));
                    intent.putExtra("year", getIntent().getIntExtra("year", -1));
                    intent.putExtra("maxNum", getIntent().getIntExtra("maxNum", -1));
                    startActivity(intent);
                }
                else{
                    onClickShowAlert(v);
                }
            }
        });

        Button edit_btn = (Button) findViewById(R.id.edit_btn);
        edit_btn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText etEdit = new EditText(getApplicationContext());
                AlertDialog.Builder dialog = new AlertDialog.Builder(TImeTableActivity.this);
                dialog.setTitle("예약 번호를 입력하세요.");
                dialog.setView(etEdit);
// OK 버튼 이벤트
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        view_edit(etEdit);
                    }
                });
// Cancel 버튼 이벤트
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        }));
    }
    public void onClickShowAlert(View view) {
        android.app.AlertDialog.Builder myAlertBuilder =
                new android.app.AlertDialog.Builder(TImeTableActivity.this);
        // alert의 title과 Messege 세팅
        myAlertBuilder.setTitle("Alert");
        myAlertBuilder.setMessage("노쇼 리스트 명단 대상자로 예약이 불가합니다.");
        // 버튼 추가 (Ok 버튼과 Cancle 버튼 )
        myAlertBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // OK 버튼을 눌렸을 경우
                Toast.makeText(getApplicationContext(), "Pressed OK",
                        Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(TImeTableActivity.this, MainActivity.class);
                // startActivity(intent);
            }
        });
        // Alert를 생성해주고 보여주는 메소드(show를 선언해야 Alert가 생성됨)
        myAlertBuilder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        booking = null;
    }

    Runnable runnable = new Runnable() { //출처: https://javapp.tistory.com/132
        @Override
        public void run() {
            try {
                String site = "http://121.169.25.215/reservation.php";
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

                String day = Integer.toString(getIntent().getIntExtra("day", 1));
                String month = Integer.toString(getIntent().getIntExtra("month", 1) + 1);
                String year = Integer.toString(getIntent().getIntExtra("year", 1));

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
                    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
                    Date select1 = dateFormat.parse(inform[2].replace("-", "."));
                    Date select2 = dateFormat.parse(year + "." + month + "." + day);
                    if (select1.equals(select2))
                        booking.add(new Booking(inform[0], inform[1], inform[2], inform[3], inform[4], inform[5], inform[6]));

                }



                TimetableView timetable = (TimetableView) findViewById(R.id.timetable);
                ArrayList<Schedule> schedules = new ArrayList<Schedule>();
                for (int i = 0; i < booking.size(); i++) {
                    String time[] = booking.get(i).getTime().split(":");
                    Schedule schedule = new Schedule();
                    schedule.setClassTitle(booking.get(i).getReservation_num()); // sets subject
                    schedule.setClassPlace(booking.get(i).getCustomer_id()); // sets place
                    schedule.setStartTime(new Time(Integer.parseInt(time[0]), Integer.parseInt(time[1]))); // sets the beginning of class time (hour,minute)
                    schedule.setEndTime(new Time(Integer.parseInt(time[0]) + 1, Integer.parseInt(time[1]))); // sets the end of class time (hour,minute)
                    schedule.setDay(Integer.parseInt(booking.get(i).getTable_id()));
                    schedules.add(schedule);
                }
                timetable.add(schedules);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };

    void view_edit(EditText etEdit){
        String inputValue = etEdit.getText().toString();
        intent = new Intent(getApplicationContext(), EditActivity.class);
        intent.putExtra("booking", booking);
        intent.putExtra("reservation_num", inputValue);
        intent.putExtra("id", getIntent().getStringExtra("id"));
        intent.putExtra("day", getIntent().getIntExtra("day", -1));
        intent.putExtra("month", getIntent().getIntExtra("month", -1));
        intent.putExtra("year", getIntent().getIntExtra("year", -1));
        intent.putExtra("maxNum", getIntent().getIntExtra("maxNum", -1));
        startActivity(intent);
    }
}
