package com.restaurant_reservation_system.controllers;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.CircularArray;
import androidx.fragment.app.DialogFragment;
import com.restaurant_reservation_system.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

}