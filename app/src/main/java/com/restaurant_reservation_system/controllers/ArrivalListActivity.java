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
import com.restaurant_reservation_system.database.ArrivalRequest;
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
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ArrivalListActivity extends AppCompatActivity {
    SingleAdapter adapter;
    EditText user_id;
    EditText arrival_time;
    String time;
    ArrayList<User> users = LoginActivity.userArray;
    static HashMap<String, ListData> arrival = new HashMap<>();
    ArrayList<SingleItem> items = new ArrayList<SingleItem>();
    String penalty;
    ArrayList<Booking> bk = AdminMainActivity.booking;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival_list_main);

        user_id = (EditText) findViewById(R.id.user_id);
        arrival_time = (EditText) findViewById(R.id.arrival_time);

        ListView listView = (ListView) findViewById(R.id.listView);

        int day = getIntent().getIntExtra("day", 1);
        int month = getIntent().getIntExtra("month", 1) + 1;
        String day2 = null;
        String month2 = null;
        day2 = Integer.toString(day);
        month2 = Integer.toString(month);
        String year = Integer.toString(getIntent().getIntExtra("year", 1));
        date = year + "-" + month2 + "-" + day2;

        // 어댑터 안에 데이터 담기
        adapter = new SingleAdapter();

        if (!arrival.isEmpty()) {
            Iterator<String> keys = arrival.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                if (date.equals(arrival.get(key).getDate()))
                    adapter.addItem(new SingleItem(key, arrival.get(key).getTime(), R.drawable.logo1));
            }
        }

        for (int i = 0; i < bk.size(); i++) {
            if (bk.get(i).getDate().equals(date) && !bk.get(i).getArrivalTime().equals("00:00"))
                adapter.addItem(new SingleItem(bk.get(i).getCustomer_id(), bk.get(i).getArrivalTime(), R.drawable.logo1));
        }
        // 리스트 뷰에 어댑터 설정
        listView.setAdapter(adapter);


        // 버튼 눌렀을 때 우측 이름, 전화번호가 리스트뷰에 포함되도록 처리
        Button button = (Button) findViewById(R.id.AddList_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = user_id.getText().toString();
                time = arrival_time.getText().toString();

                if(time.contains(":")){
                    int day = getIntent().getIntExtra("day", 1);
                    int month = getIntent().getIntExtra("month", 1) + 1;
                    String day2 = null;
                    String month2 = null;

                    day2 = Integer.toString(day);
                    month2 = Integer.toString(month);
                    String year = Integer.toString(getIntent().getIntExtra("year", 1));
                    date = year + "-" + month2 + "-" + day2;
                    Booking b = null;
                    for (int i = 0; i < bk.size(); i++) {
                        if (bk.get(i).getCustomer_id().equals(id) && bk.get(i).getDate().equals(date)) {
                            b = bk.get(i);
                            break;
                        }
                    }
                    if (b != null) {

                        arrival.put(id, new ListData(date, time));
                        adapter.addItem(new SingleItem(id, time, R.drawable.logo1));
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);
                        upDate();

                        String[] t1 = null;
                        String[] t2 = null;
                        t1 = b.getTime().split(":");
                        t2 = time.split(":");
                        int reservation_time = Integer.parseInt(t1[0]) * 100 + Integer.parseInt(t1[1]);//예약시간
                        int real_arrive = Integer.parseInt(t2[0]) * 100 + Integer.parseInt(t2[1]);

                        if (real_arrive - reservation_time > 0) {
                            ListActivity.noShow.put(id, time);
                            upDate2();
                        }
                      }
                    else {
                        onClickShowAlert(v);
                     }
                }
                else{
                    AlertDialog.Builder myAlertBuilder =
                            new AlertDialog.Builder(ArrivalListActivity.this);
                    // alert의 title과 Messege 세팅
                    myAlertBuilder.setTitle("Alert");
                    myAlertBuilder.setMessage("도착시간을 다시입력해 주세요. (ex) 12:00");
                    myAlertBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // OK 버튼을 눌렸을 경우
                            Toast.makeText(getApplicationContext(), "Pressed OK",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    // Alert를 생성해주고 보여주는 메소드(show를 선언해야 Alert가 생성됨)
                    myAlertBuilder.show();
                }
            }
        });
    }

    public void onClickShowAlert(View view) {
        AlertDialog.Builder myAlertBuilder =
                new AlertDialog.Builder(ArrivalListActivity.this);
        // alert의 title과 Messege 세팅
        myAlertBuilder.setTitle("Alert");
        myAlertBuilder.setMessage("해당 날짜에 일치하는 손님은 없습니다.");
        // 버튼 추가 (Ok 버튼과 Cancle 버튼 )
        myAlertBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // OK 버튼을 눌렸을 경우
                Toast.makeText(getApplicationContext(), "Pressed OK",
                        Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(ListActivity.this, LoginActivity.class);
                // startActivity(intent);
            }
        });
        // Alert를 생성해주고 보여주는 메소드(show를 선언해야 Alert가 생성됨)
        myAlertBuilder.show();
    }

    class ListData {
        String date;
        String time;

        public ListData(String date, String time) {
            this.date = date;
            this.time = time;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

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

    public void upDate() {

        Response.Listener<String> res = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Toast.makeText(ArrivalListActivity.this, "수정완료", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ArrivalListActivity.this, "수정실패", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        user_id = (EditText) findViewById(R.id.user_id);
        arrival_time = (EditText) findViewById(R.id.arrival_time);
        String id = user_id.getText().toString();
        String time = arrival_time.getText().toString();
        ArrivalRequest aq = new ArrivalRequest(id, time, res);
        RequestQueue queue1 = Volley.newRequestQueue(ArrivalListActivity.this);
        queue1.add(aq);
    }

    public void upDate2() {

        Response.Listener<String> res = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Toast.makeText(ArrivalListActivity.this, "패널티 값 수정완료", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ArrivalListActivity.this, "패널티 값 수정실패", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            }
        };
        user_id = (EditText) findViewById(R.id.user_id);
        String id = user_id.getText().toString();
        penalty = "T";
        UpDate2 update = new UpDate2(id, penalty, res);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(update);


    }

    class UpDate2 extends StringRequest {
        final static private String URL = "http://192.168.219.101/update_penalty.php";
        private Map map;

        public UpDate2(String id, String penalty, Response.Listener listener) {
            super(Method.POST, URL, listener, null);

            map = new HashMap();
            map.put("id", id);
            map.put("penalty", penalty);
        }

        protected Map getParams() throws AuthFailureError {
            return map;
        }
    }

}