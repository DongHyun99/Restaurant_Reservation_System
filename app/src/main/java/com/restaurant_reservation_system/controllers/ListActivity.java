package com.restaurant_reservation_system.controllers;
import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.restaurant_reservation_system.R;
import com.restaurant_reservation_system.database.Booking;
import com.restaurant_reservation_system.database.SingleItem;
import com.restaurant_reservation_system.database.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ListActivity extends AppCompatActivity {
    SingleAdapter adapter;
    EditText user_id;
    EditText arrival_time;
    ArrayList<User> users= LoginActivity.userArray;
    static HashMap<String,String> noShow = new HashMap<>() ;
    ArrayList<SingleItem> items = new ArrayList<SingleItem>();
    String penalty;
    ArrayList<Booking> booking = new ArrayList<>();
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_main);

        user_id = (EditText) findViewById(R.id.user_id);
        arrival_time = (EditText) findViewById(R.id.arrival_time);

        ListView listView = (ListView) findViewById(R.id.listView);

        Thread thread = new Thread(runnable);
        thread.start();

        // 어댑터 안에 데이터 담기
        adapter = new SingleAdapter();

        for(int i=0; i<users.size();i++){
            if(users.get(i).getPenalty().equals("T")){
                adapter.addItem(new SingleItem(users.get(i).getID(),"NO SHOW",R.drawable.logo1));
            }
        }
        if(!noShow.isEmpty()){
            Iterator<String> keys =noShow.keySet().iterator();
            while( keys.hasNext() ){
                String key = keys.next();
                adapter.addItem(new SingleItem(key,"NO SHOW",R.drawable.logo1));
            }}

        // 리스트 뷰에 어댑터 설정
        listView.setAdapter(adapter);

    }



    Runnable runnable = new Runnable() { //출처: https://javapp.tistory.com/132
        @Override
        public void run() {
            try {
                String site = "http://192.168.219.101/reservation.php";
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

                data=data.replace("[","");
                data=data.replace("]","");
                data=data.replace("{","");
                String []test = data.split("\\},");
                test[test.length-1]=test[test.length-1].replace("}","");
                for(int i=0; i< test.length; i++){
                    test[i]=test[i].replace("\"reservation_num\":","");
                    test[i]=test[i].replace("\"covers\":","");
                    test[i]=test[i].replace("\"date\":","");
                    test[i]=test[i].replace("\"time\":","");
                    test[i]=test[i].replace("\"table_id\":","");
                    test[i]=test[i].replace("\"customer_id\":","");
                    test[i]=test[i].replace("\"arrivalTime\":","");
                    test[i]=test[i].replace("\"","");
                    String inform[]=test[i].split(",");
                    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
                    Date select1 = dateFormat.parse(inform[2].replace("-","."));
                    booking.add(new Booking(inform[0],inform[1],inform[2],inform[3],inform[4],inform[5],inform[6]));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };

    class SingleAdapter extends BaseAdapter {


        // Generate > implement methods
        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(SingleItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 뷰 객체 재사용
            SingleItemView view = null;
            if (convertView == null) {
                view = new SingleItemView(getApplicationContext());
            } else {
                view = (SingleItemView) convertView;
            }

            SingleItem item = items.get(position);

            view.setId(item.getId());
            view.setArrival(item.getArrival());
            view.setImage(item.getResId());


            return view;
        }
    }

}