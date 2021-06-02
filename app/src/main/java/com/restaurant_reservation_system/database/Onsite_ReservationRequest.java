package com.restaurant_reservation_system.database;

import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Onsite_ReservationRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.219.100/edit_onsitereservation.php";
    private Map<String, String> map;


    public Onsite_ReservationRequest(String name, String pho, String date , String artime, String onsitecovers, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);


        map = new HashMap<>();

        map.put("name",name);
        map.put("phone", pho);
        map.put("arrivalday", date);
        map.put("arrivaltime", artime);
        map.put("covers", onsitecovers);
    }



    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}