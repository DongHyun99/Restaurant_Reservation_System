package com.restaurant_reservation_system.database;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReservationRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://192.168.45.203/edit_reservation.php";
    private Map<String, String> map;


    public ReservationRequest(String rNum, String covers, String date, String time, String tableID, String customerID,String arrivalTime, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("reservation_num",rNum);
        map.put("covers",covers);
        map.put("DATE", date);
        map.put("TIME", time);
        map.put("table_id", tableID);
        map.put("customer_id", customerID);
        map.put("arrivaltime", arrivalTime);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}