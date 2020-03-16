package com.quizer.quizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.com.quizer.quizer.R;

public class ScoreActivity extends AppCompatActivity {

    Context context;
    @BindView(R.id.description)
    TextView tv_des;

    @BindView(R.id.pass_description)
    LinearLayout smile;
    @BindView(R.id.fail_description)
    LinearLayout trophy;
    int result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);
        context=ScoreActivity.this;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent=getIntent();
        result=intent.getIntExtra("score",0);
       if(result>=2){
           tv_des.setText("Congratulation You Have Passed the Test!!");
           trophy.setVisibility(View.VISIBLE);
       }
       else{
           tv_des.setText("Better Luck Next Time");
           smile.setVisibility(View.VISIBLE);

       }
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAffinity(this);
    }
}
