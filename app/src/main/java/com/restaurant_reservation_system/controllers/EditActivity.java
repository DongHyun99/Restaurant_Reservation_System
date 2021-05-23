package com.restaurant_reservation_system.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.restaurant_reservation_system.R;

public class EditActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Button submit_btn =(Button) findViewById(R.id.submit_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, TImeTableActivity.class);
                startActivity(intent);
            }
        });
    }
}
