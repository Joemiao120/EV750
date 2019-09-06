package com.huidisen.ep750.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.huidisen.ep750.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by miaoyichao on 16/7/12.
 */

public class CustomDialog extends Dialog {
    private final static long DELAY_TIME = 2 * 1000;


    private Context mContext;
    private String mStr;
    private TimerTask timerTask;
    private Timer timer;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            dismiss();
        }
    };

    public CustomDialog(Context context, String str) {
        super(context,R.style.MyDialog);
        mContext = context;
        mStr = str;
        timer = new Timer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };

        timer.schedule(timerTask, DELAY_TIME);

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_dialog, null);
        TextView textView = (TextView) layout.findViewById(R.id.title);
        textView.setText(mStr);

        this.setContentView(layout);
    }
}

