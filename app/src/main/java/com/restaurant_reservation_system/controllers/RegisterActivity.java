package com.restaurant_reservation_system.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.restaurant_reservation_system.R;
import com.restaurant_reservation_system.database.RegisterRequest;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        EditText email = (EditText) findViewById(R.id.registerID);
        EditText password = (EditText) findViewById(R.id.registerPassword);
        EditText name = (EditText) findViewById(R.id.registerName);
        EditText phoneNumber = (EditText) findViewById(R.id.registerPhoneNumber);

        TextView linkLogin = (TextView) findViewById(R.id.linkLogin);
        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = email.getText().toString();
                String userPW = password.getText().toString();
                String userName = name.getText().toString();
                String userPhoneNum = phoneNumber.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success) Toast.makeText(getApplicationContext(),"회원등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                            else Toast.makeText(getApplicationContext(),"회원등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                RegisterRequest registerRequest = new RegisterRequest(userID, userPW, userName, userPhoneNum, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });

    }
}
