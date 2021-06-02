package com.restaurant_reservation_system.database;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReservationRequest3 extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://121.169.25.215/update_reservation.php";
    private Map<String, String> map;


    public ReservationRequest3(String rNum, String covers, String time, String tableID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("reservation_num",rNum);
        map.put("covers",covers);
        map.put("time", time);
        map.put("table_id", tableID);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}