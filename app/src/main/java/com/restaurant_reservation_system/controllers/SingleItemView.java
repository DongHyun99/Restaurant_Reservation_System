package com.restaurant_reservation_system.controllers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.restaurant_reservation_system.R;


public class SingleItemView extends LinearLayout {

    TextView list_id;
    TextView list_time;
    ImageView imageView;

    // Generate > Constructor

    public SingleItemView(Context context) {
        super(context);

        init(context);
    }

    public SingleItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    // singer_item.xml을 inflation
    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.single_item_list, this, true);

        list_id = (TextView) findViewById(R.id.list_id);
        list_time = (TextView) findViewById(R.id.list_time);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void setArrival(String time) {
        list_time.setText(time);
    }

    public void setId(String Id) {
        list_id.setText(Id);
    }

    public void setImage(int resId) {
        imageView.setImageResource(resId);
    }
}

