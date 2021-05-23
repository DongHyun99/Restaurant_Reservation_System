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

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

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
                            if (success)
                                Toast.makeText(getApplicationContext(), "회원등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                            else Toast.makeText(getApplicationContext(), "회원등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
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
