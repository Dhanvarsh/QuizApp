package com.quizer.quizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.WindowManager;
import com.com.quizer.quizer.R;

public class SplashActivity extends AppCompatActivity {
Context context;
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        context=SplashActivity.this;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(context,TakeTest.class);
                startActivity(intent);

            }
        },2000);
    }
}
