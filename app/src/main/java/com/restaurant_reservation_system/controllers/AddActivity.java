package com.restaurant_reservation_system.controllers;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import com.restaurant_reservation_system.database.RegisterRequest;
import com.restaurant_reservation_system.database.ReservationRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddActivity extends AppCompatActivity {
    String date;
    String time;
    int max_num;
    TextView time_pick;
    String [] t1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        TextView covers = (TextView)findViewById(R.id.Covers_add);
        Spinner table_num = (Spinner)findViewById(R.id.add_table_spinner);
        time_pick = (TextView)findViewById(R.id.add_start_time);

        String day = Integer.toString(getIntent().getIntExtra("day", 1));
        String month = Integer.toString(getIntent().getIntExtra("month", 1) + 1);
        String year = Integer.toString(getIntent().getIntExtra("year", 1));
        max_num = getIntent().getIntExtra("maxNum",1);
        date = year + "-" + month + "-" + day;

        Button submit_btn = (Button) findViewById(R.id.add_submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cover = covers.getText().toString();
                String table = Integer.toString(Integer.parseInt(table_num.getSelectedItem().toString())-1);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                ReservationRequest reservationRequest = new ReservationRequest(Integer.toString(max_num),cover,date,time,table,getIntent().getStringExtra("id"),"00:00",responseListener);
                RequestQueue queue = Volley.newRequestQueue(AddActivity.this);
                queue.add(reservationRequest);

                Toast.makeText(getApplicationContext(),"예약이 추가되었습니다.",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),TImeTableActivity.class);
                intent.putExtra("id",getIntent().getStringExtra("id"));
                intent.putExtra("day", getIntent().getIntExtra("day", 1));
                intent.putExtra("month",getIntent().getIntExtra("month", 1));
                intent.putExtra("year", getIntent().getIntExtra("year", 1));
                intent.putExtra("maxNum",max_num+1);
                intent.putExtra("penalty", getIntent().getStringExtra("penalty"));
                startActivity(intent);


            }
        });

        time_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime();
            }
        });
    }

    void showTime(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time = Integer.toString(hourOfDay)+":"+Integer.toString(minute);
            }
        },12, 00, false);
        timePickerDialog.show();
    }
}
