package com.restaurant_reservation_system.controllers;



import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.appcompat.app.AppCompatActivity;
import com.restaurant_reservation_system.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class WaitingListActivity extends AppCompatActivity {

    private static String TAG = "WaitingListActivity";

    private static final String TAG_JSON="webnautes";
    private static final String TAG_name = "name";
  private static final String TAG_covers = "covers";
    private static final String TAG_reservationtime = "reservationtime";
    ArrayList<HashMap<String, String>> wArrayList;
    ListView wlistView;
    String mJsonString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitinglist_main);

        wlistView = (ListView) findViewById(R.id.listView_watinglist_main);
        wArrayList = new ArrayList<>();

        final View header = getLayoutInflater().inflate(R.layout.activity_waitinglist_header, null, false) ;
        wlistView.addHeaderView(header) ;

        GetData task = new GetData();
        task.execute("http://192.168.25.8/waitinglist.php");
    }


    private class GetData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(WaitingListActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response  - " + result);

            if (result == null){

            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String name = item.getString(TAG_name);

                String covers = item.getString(TAG_covers);
                String reservationtime = item.getString(TAG_reservationtime);
                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_name, name);
                hashMap.put(TAG_covers, covers);
                hashMap.put(TAG_reservationtime, reservationtime);
                wArrayList.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    WaitingListActivity.this, wArrayList, R.layout.activity_waitinglist,
                    new String[]{TAG_name,TAG_covers,TAG_reservationtime},
                    new int[]{R.id.textView_waitinglist_name, R.id.textView_waitinglist_covers, R.id.textView_waitinglist_reservationtime}
            );

            wlistView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}
