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



        // 버튼 눌렀을 때 우측 이름, 전화번호가 리스트뷰에 포함되도록 처리
        /*Button button = (Button) findViewById(R.id.AddList_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = user_id.getText().toString();
                String time = arrival_time.getText().toString();

                int day =getIntent().getIntExtra("day", 1);
                int month =getIntent().getIntExtra("month", 1) + 1;
                String day2 = null;
                String month2 =null;
                if(day<10)
                    day2="0"+Integer.toString(day);
                else
                    day2=Integer.toString(day);
                if(month<10)
                    month2="0"+Integer.toString(month);
                else
                    month2=Integer.toString(month);
                String year = Integer.toString(getIntent().getIntExtra("year", 1));
                date = year + "-" + month2 + "-" + day2;
                Booking b = null;
                for (int i = 0; i < booking.size(); i++) {
                    if (booking.get(i).getCustomer_id().equals(id)&&booking.get(i).getDate().equals(date)) {
                        b = booking.get(i);
                        break;
                    }
                }
                if(b!=null){
                    String[] t1 = null;
                    String[] t2 = null;
                    t1 = b.getTime().split(":");
                    t2 = time.split(":");
                    int reservation_time = Integer.parseInt(t1[0])*100+Integer.parseInt(t1[1]);//예약시간
                    int real_arrive = Integer.parseInt(t2[0])*100+Integer.parseInt(t2[1]);

                    if (real_arrive-reservation_time>0) {
                        adapter.addItem(new SingleItem(id, time, R.drawable.logo1));
                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);
                        upDate();
                        noShow.put(id,time);
                    }
                }else{
                    onClickShowAlert(v);
                }


//여기까지
            }
        });*/
    }
    /*public void onClickShowAlert(View view) {
        AlertDialog.Builder myAlertBuilder =
                new AlertDialog.Builder(ListActivity.this);
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
    }*/


    Runnable runnable = new Runnable() { //출처: https://javapp.tistory.com/132
        @Override
        public void run() {
            try {
                String site = "http://192.168.45.128/reservation.php";
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

   /* public void upDate (){

        Response.Listener<String> res = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if(success){
                        Toast.makeText(ListActivity.this,"패널티 값 수정완료",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(ListActivity.this,"패널티 값 수정실패",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        user_id = (EditText) findViewById(R.id.user_id);
        String id = user_id.getText().toString();
        penalty = "T";
        UpDate update = new UpDate(id, penalty, res);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(update);


    }

    class UpDate extends StringRequest{
        final static private String URL ="http://192.168.45.128/update_penalty.php";
        private Map map;
        public UpDate(String id, String penalty, Response.Listener listener){
            super(Method.POST, URL, listener, null);

            map = new HashMap();
            map.put("id",id);
            map.put("penalty",penalty);
        }
        protected Map getParams() throws AuthFailureError{
            return map;
        }
    }*/


}