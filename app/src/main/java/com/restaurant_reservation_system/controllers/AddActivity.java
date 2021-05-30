package com.restaurant_reservation_system.controllers;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Response;
import com.restaurant_reservation_system.R;
import com.restaurant_reservation_system.database.Booking;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddActivity extends AppCompatActivity {
    Date date;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        try {
            String day = Integer.toString(getIntent().getIntExtra("day",1));
            String month = Integer.toString(getIntent().getIntExtra("month",1)+1);
            String year = Integer.toString(getIntent().getIntExtra("year",1));
            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            date = dateFormat.parse(year+"."+month+"."+day);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Button submit_btn = (Button) findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView cover = (TextView) findViewById(R.id.Covers_edit);
                String covers = cover.getText().toString();

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
                ArrayList<Booking> newArray = TImeTableActivity.booking;
                // 서버로 Volley를 이용해서 요청을 함.


            }
        });
    }
}
