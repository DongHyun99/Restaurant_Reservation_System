package com.restaurant_reservation_system.database;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public class ArrivalRequest extends StringRequest {
    final static private String URL ="http://192.168.45.128/update_arrivaltime.php";
    private Map<String,String> map;
    public ArrivalRequest(String id, String time, Response.Listener listener){
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("id",id);
        map.put("time",time);
    }
    protected Map<String,String> getParams() throws AuthFailureError {
        return map;
    }
}
