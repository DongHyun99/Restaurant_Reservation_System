package com.restaurant_reservation_system.controllers;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.github.tlaabs.timetableview.TimetableView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ListActivity extends AppCompatActivity {
    SingleAdapter adapter;
    EditText user_id;
    EditText arrival_time;
    ArrayList<User> users= LoginActivity.userArray;
    static HashMap<String,String> noShow = new HashMap<>() ;
    ArrayList<SingleItem> items = new ArrayList<SingleItem>();
    String penalty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_main);

        user_id = (EditText) findViewById(R.id.user_id);
        arrival_time = (EditText) findViewById(R.id.arrival_time);

        ListView listView = (ListView) findViewById(R.id.listView);

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

        // 이벤트 처리 리스너 설정
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User item = (User) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 :" + item.getName(), Toast.LENGTH_LONG).show();
            }
        });

        // 버튼 눌렀을 때 우측 이름, 전화번호가 리스트뷰에 포함되도록 처리
        Button button = (Button) findViewById(R.id.AddList_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = user_id.getText().toString();
                String time = arrival_time.getText().toString();
                Booking b = null;
                for (int i = 0; i < TImeTableActivity.booking.size(); i++) {
                    if (TImeTableActivity.booking.get(i).getCustomer_id().equals(id)) {
                        b = TImeTableActivity.booking.get(i);
                        break;
                    }
                }
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



//여기까지
            }
        });
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

    public void upDate (){

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
        final static private String URL ="http://192.168.219.100/update_penalty.php";
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
    }


}