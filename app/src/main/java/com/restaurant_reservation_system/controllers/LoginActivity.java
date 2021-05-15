/*************************************************************************************************
 * JANUARY 8, 2018
 * Mentesnot Aboset
 *  ************************************************************************************************/
package com.restaurant_reservation_system.controllers;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.restaurant_reservation_system.R;
import com.restaurant_reservation_system.database.User;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    String[] r;
    ArrayList <User> userArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userArray = new ArrayList<User>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Thread thread = new Thread(runnable);
        thread.start();

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }

    Runnable runnable = new Runnable() { //출처: https://javapp.tistory.com/132
        @Override
        public void run() {
            try {
                String site = "http://210.100.228.111/connect.php";
                URL url = new URL(site);
                //접속
                URLConnection conn = url.openConnection();
                //서버와 연결되어 있는 스트림을 추출
                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                BufferedReader br = new BufferedReader(isr);

                String str = null;
                StringBuffer buf = new StringBuffer();

                do {
                    str = br.readLine();
                    if (str != null) {
                        buf.append(str);
                    }
                } while (str != null);

                String data = buf.toString();  //json 문자열 다 읽어옴
                r = data.split("</br>"); //문자열을 줄단위로 나눈다. 0번재 줄은 user 정보, 1번째 줄은 table 정보이다.

                r[0]=r[0].replace("[","");
                r[0]=r[0].replace("]","");
                r[0]=r[0].replace("{","");
                String []test = r[0].split("\\},");
                test[test.length-1]=test[test.length-1].replace("}","");
                for(int i=0; i< test.length; i++){
                    test[i]=test[i].replace("\"name\":","");
                    test[i]=test[i].replace("\"id\":","");
                    test[i]=test[i].replace("\"phoneNumber\":","");
                    test[i]=test[i].replace("\"pw\":","");
                    test[i]=test[i].replace("\"","");
                    String inform[]=test[i].split(",");
                    userArray.add(new User(inform[0],inform[1],inform[2],inform[3]));
                }
                System.out.println(userArray.get(0).getID());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}

