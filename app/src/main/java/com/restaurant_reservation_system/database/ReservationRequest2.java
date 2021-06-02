package com.restaurant_reservation_system.database;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReservationRequest2 extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.219.100/delete_reservation.php";
    private Map<String, String> map;


    public ReservationRequest2(String rNum, String covers, String date, String time, String tableID, String customerID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("reservation_num",rNum);
        map.put("covers",covers);
        map.put("DATE", date);
        map.put("TIME", time);
        map.put("table_id", tableID);
        map.put("customer_id", customerID);
    }

    public ReservationRequest2(String rNum, Response.Listener<String> listener){ //delete 시 사용할 생성자
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("reservation_num", rNum);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}