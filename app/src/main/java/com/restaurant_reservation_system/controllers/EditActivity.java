package com.restaurant_reservation_system.controllers;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.restaurant_reservation_system.R;
import com.restaurant_reservation_system.database.Booking;
import com.restaurant_reservation_system.database.ReservationRequest2;
import com.restaurant_reservation_system.database.ReservationRequest3;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    String reservation_num, id, penalty, edit_time;
    int day, month, year, maxNum;
    Booking booking;

    EditText covers;
    Spinner spinner;
    TextView time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        init();

        //timePickerDialog
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime();
            }
        });

        // 수정 버튼
        Button submit_btn = (Button) findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tableNum = Integer.parseInt(spinner.getSelectedItem().toString())-1;
                String cover = covers.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(EditActivity.this, "수정 완료", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EditActivity.this, "수정 실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                ReservationRequest3 reservationRequest = new ReservationRequest3(reservation_num,cover,edit_time,Integer.toString(tableNum), responseListener);
                RequestQueue queue = Volley.newRequestQueue(EditActivity.this);
                queue.add(reservationRequest);
                Intent intent = new Intent(getApplicationContext(), TImeTableActivity.class);
                pushing(intent);
                startActivity(intent);


            }
        });

        // 삭제 버튼
        Button delete_btn = (Button) findViewById(R.id.delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(EditActivity.this, "삭제완료", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(EditActivity.this, "삭제실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                ReservationRequest2 reservationRequest = new ReservationRequest2(reservation_num, responseListener);
                RequestQueue queue = Volley.newRequestQueue(EditActivity.this);
                queue.add(reservationRequest);
                Intent intent = new Intent(getApplicationContext(), TImeTableActivity.class);
                pushing(intent);
                startActivity(intent);
            }
        });
    }

    //객체를 전달 받는 함수
    void init() {
        reservation_num = getIntent().getStringExtra("reservation_num");
        id = getIntent().getStringExtra("id");
        day = getIntent().getIntExtra("day", -1);
        month = getIntent().getIntExtra("month", -1);
        year = getIntent().getIntExtra("year", -1);
        maxNum = getIntent().getIntExtra("maxNum", 1);
        penalty = getIntent().getStringExtra("penalty");
        ArrayList<Booking> bookings = (ArrayList<Booking>) getIntent().getSerializableExtra("booking");

        // ArrayList 에서 예약 번호와 같은 booking 찾기
        System.out.println(bookings.get(0).getCustomer_id());
        for (Booking element : bookings) {
            if (element.getReservation_num().equals(reservation_num))
                booking = element;
        }
        init_view();
    }

    //전달 받은 객체로 View 를 수정하는 함수
    void init_view() {
        int endTime = Integer.parseInt(booking.getTime().split(":")[0]) + 1;
        covers = findViewById(R.id.Covers_edit);
        spinner = findViewById(R.id.table_spinner);
        time = findViewById(R.id.start_time);
        covers.setText(booking.getCovers());
        spinner.setSelection(Integer.parseInt(booking.getTable_id()));
    }

    //전달 받은 객체들을 다시 전송하는 함수
    void pushing(Intent intent) {
        intent.putExtra("id", getIntent().getStringExtra("id"));
        intent.putExtra("day", getIntent().getIntExtra("day", -1));
        intent.putExtra("month", getIntent().getIntExtra("month", -1));
        intent.putExtra("year", getIntent().getIntExtra("year", -1));
        intent.putExtra("maxNum", getIntent().getIntExtra("maxNum", -1));
        intent.putExtra("penalty", getIntent().getStringExtra("penalty"));
    }

    void showTime(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                edit_time = Integer.toString(hourOfDay)+":"+Integer.toString(minute);
            }
        },12, 00, false);
        timePickerDialog.show();
    }
}
