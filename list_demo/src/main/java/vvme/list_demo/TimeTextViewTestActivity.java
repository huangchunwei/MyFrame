package vvme.list_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by VV on 2016/11/17.
 */

public class TimeTextViewTestActivity extends AppCompatActivity {

    private TimeTextView mTimeTextView;
    private int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_text);
        mTimeTextView = (TimeTextView) findViewById(R.id.ttv_timeView);
        mTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (count % 2 == 0){
                    mTimeTextView.startTimeTask();
                }else{
                    mTimeTextView.stopTimeTask();
                }
            }
        });

    }


}
