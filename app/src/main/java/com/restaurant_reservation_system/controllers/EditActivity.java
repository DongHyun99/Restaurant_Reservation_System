package com.restaurant_reservation_system.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.restaurant_reservation_system.R;
import com.restaurant_reservation_system.database.Booking;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    String reservation_num, id;
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

        // 수정 버튼
        Button submit_btn = (Button) findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        // 삭제 버튼
        Button delete_btn = (Button) findViewById(R.id.delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    //객체를 전달 받는 함수
    void init(){
        reservation_num = getIntent().getStringExtra("reservation_num");
        id = getIntent().getStringExtra("id");
        day = getIntent().getIntExtra("day", -1);
        month = getIntent().getIntExtra("month", -1);
        year = getIntent().getIntExtra("year",-1);
        maxNum = getIntent().getIntExtra("maxNum",1);
        ArrayList<Booking> bookings = (ArrayList<Booking>) getIntent().getSerializableExtra("booking_Array");
        // ArrayList 에서 예약 번호와 같은 booking 찾기
        for (Booking element: bookings){
            if (element.getReservation_num().equals(reservation_num))
                booking = element;
        }
        init_view();
    }

    //전달 받은 객체로 View 를 수정하는 함수
    void init_view(){
        int endTime = Integer.parseInt(booking.getTime().split(":")[0])+1;
        covers = findViewById(R.id.Covers_edit);
        spinner = findViewById(R.id.table_spinner);
        time = findViewById(R.id.start_time);
        covers.setText(booking.getCovers());
        spinner.setSelection(Integer.parseInt(booking.getTable_id())+1);
        time.setText(booking.getTime()+"-"+Integer.toString(endTime)+":"+"0");
    }
}
