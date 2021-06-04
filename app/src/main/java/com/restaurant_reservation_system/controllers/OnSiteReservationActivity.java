package com.restaurant_reservation_system.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.restaurant_reservation_system.R;
import com.restaurant_reservation_system.database.Onsite_ReservationRequest;
import org.json.JSONException;
import org.json.JSONObject;

public class OnSiteReservationActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_on_site_reservation);

        String day = Integer.toString(getIntent().getIntExtra("day", 1));
        String month = Integer.toString(getIntent().getIntExtra("month", 1)+1);
        String year = Integer.toString(getIntent().getIntExtra("year", 1));
        String date = year+"-"+month+"-"+day;

        EditText Onsite_Name = findViewById(R.id.onsite_name);
        EditText Onsite_Phone = findViewById(R.id.onsite_phone);
        TextView Onsite_Day = findViewById(R.id.onsite_day);
        Onsite_Day.setText(date);
        EditText Onsite_Time= findViewById(R.id.onsite_time);
        EditText Onsite_Covers = findViewById(R.id.onsite_covers);
        Button addonsite = (Button)findViewById(R.id.btnAddOnSite);
        addonsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog OnsiteDialog;

                String onsitename = Onsite_Name.getText().toString();
                String onsitephone = Onsite_Phone.getText().toString();

                String onsitetime = Onsite_Time.getText().toString();
                String onsitecovers = Onsite_Covers.getText().toString();

                if(onsitename.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder( OnSiteReservationActivity.this );
                    OnsiteDialog=builder.setMessage("손님 이름을 입력하세요")
                            .setPositiveButton("확인",null)
                            .create();
                    OnsiteDialog.show();
                    return;
                }
                if(onsitephone.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder( OnSiteReservationActivity.this );
                    OnsiteDialog=builder.setMessage("손님 번호를 입력하세요")
                            .setPositiveButton("확인",null)
                            .create();
                    OnsiteDialog.show();
                    return;
                }
                if(onsitetime.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder( OnSiteReservationActivity.this );
                    OnsiteDialog=builder.setMessage("예약 시간을 입력하세요")
                            .setPositiveButton("확인",null)
                            .create();
                    OnsiteDialog.show();
                    return;
                }
                if(onsitecovers.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder( OnSiteReservationActivity.this );
                    OnsiteDialog=builder.setMessage("인원 수를 입력하세요")
                            .setPositiveButton("확인",null)
                            .create();
                    OnsiteDialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success) {
                                Toast.makeText(getApplicationContext(),"현장예약 완료.",Toast.LENGTH_SHORT).show();}
                            else {Toast.makeText(getApplicationContext(),"현장예약 실패.",Toast.LENGTH_SHORT).show();}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Onsite_ReservationRequest on_request= new Onsite_ReservationRequest(onsitename, onsitephone, date, onsitetime, onsitecovers, responseListener);
                RequestQueue queue = Volley.newRequestQueue(OnSiteReservationActivity.this);
                queue.add(on_request);

                Intent intent = new Intent(OnSiteReservationActivity.this, AdminMainActivity.class);//
                startActivity(intent);
            }


        });
    }
}
