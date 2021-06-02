package com.restaurant_reservation_system.database;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.45.128/edit_user_inform2.php";
    private Map<String, String> map;


    public RegisterRequest(String id, String pw, String NAME, String phoneNumber, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("id",id);
        map.put("pw", pw);
        map.put("NAME", NAME);
        map.put("phoneNumber", phoneNumber);
        map.put("penalty", "F");
        map.put("admin","F");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}