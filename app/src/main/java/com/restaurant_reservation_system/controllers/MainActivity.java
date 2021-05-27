package com.restaurant_reservation_system.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.restaurant_reservation_system.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnReservation = (Button) findViewById(R.id.btnTimeTable);
        btnReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TImeTableActivity.class);
                startActivity(intent);
            }
        });
        Button btnArrivalTime= (Button) findViewById(R.id.btnArriveTime);
        btnArrivalTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });

        Button btnStat= (Button) findViewById(R.id.btnStatistics);
        btnStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StaticisActivity.class);
                startActivity(intent);
            }
        });
    }


}