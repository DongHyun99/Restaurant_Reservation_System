package com.restaurant_reservation_system.controllers;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.restaurant_reservation_system.R;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

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


}
