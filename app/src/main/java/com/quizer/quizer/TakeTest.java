package com.quizer.quizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.com.quizer.quizer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class TakeTest extends AppCompatActivity {



    Context context;
    Recycler_adapter optionRecyclerAdapter;

    @BindView(R.id.optionRecycleView)
    RecyclerView optionrecyclerView;

    @BindView(R.id.tv_question)
    TextView tv_question;

    @BindView(R.id.remainingtime)
    TextView tv_remainingtime;

    @BindView(R.id.minQue)
    TextView tv_minQue;

    @BindView(R.id.maxQue)
    TextView tv_maxQue;

    @BindView(R.id.seekbar)
    SeekBar seekBar;

    @BindView(R.id.progress_bar)
    ProgressBar pb;
    @BindView(R.id.title)
    TextView tv_level_name;


    public long time_utilised,remaining_time;
    private int progressStatus = 0;
    private long total_time,millisInFuture ;
    private long countDownInterval = 1000;
    int number_of_que,count=0,min_que,right_ans=0;
    CountDownTimer countDownTimer;
    JSONObject obj;
    String[] correct_Answer;
    JSONArray m_jArry;
    String str_correct_ans="",testId,device_id,device_name,que_id,level_name;
    //ArrayList<TestDatum> testDatumArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_test);
        ButterKnife.bind(this);
        context=TakeTest.this;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        optionrecyclerView.setLayoutManager(linearLayoutManager);

        correct_Answer = getResources().getStringArray(R.array.answers);
        seekBar.setMax(4);
        seekBar.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        try {
            obj = new JSONObject(loadJSONFromAsset());
             m_jArry = obj.getJSONArray("MCQ");
            Log.d("Json===",m_jArry.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        callQueAns(count);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("questions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public void callQueAns(int count){
        JSONObject jo_inside;
        try {
             jo_inside = m_jArry.getJSONObject(count);
            tv_question.setText(jo_inside.getString("Question"));
            total_time=30;
            pb.setProgress(progressStatus);
            JSONArray option_Array=jo_inside.getJSONArray("Options");
            optionRecyclerAdapter=new Recycler_adapter(option_Array, context,new Recycler_adapter.GetSelectedOption() {
                //get the selected ans
                @Override
                public void getCorrectAns(String answer) {
                    str_correct_ans=answer;
                    Log.d("selected_Ans",str_correct_ans);
                }
            });
            optionrecyclerView.setAdapter(optionRecyclerAdapter);
            //this is called to start time in reverse order
            callCountDown(total_time);
            countDownTimer.start();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @OnClick(R.id.submitt)
    public void onButtonClick(View view){
        countDownTimer.cancel();
        time_utilised=total_time-remaining_time;
        //this is called to reset all the values like seekbar and que ,ans
        resetAllValue();
    }

    public void callCountDown(final long total_time){
        millisInFuture=total_time*1000;
        progressStatus=0;
        pb.setMax((int)total_time);
        countDownTimer=new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv_remainingtime.setText(String.valueOf(millisUntilFinished/1000));
                Log.d("timeinsec",millisUntilFinished/1000 + "  Seconds..."+count+"count");
                progressStatus +=1;
                pb.setProgress(progressStatus);
                remaining_time=millisUntilFinished/1000;
            }

            @Override
            public void onFinish() {
                progressStatus +=1;
                pb.setProgress(progressStatus);
                remaining_time=0;
                time_utilised=total_time-remaining_time;
                progressStatus=0;
                resetAllValue();

            }
        };


    }
    public void resetAllValue(){
        submit_Value();
        if(count<m_jArry.length()-1){
            min_que=Integer.parseInt(tv_minQue.getText().toString().trim());
            min_que=min_que+1;
            tv_minQue.setText(String.valueOf(min_que));
            seekBar.setProgress(min_que);
            count=count+1;
            callQueAns(count);

        }
        else{
            Log.d("Finish","yes");
            Intent intent=new Intent(this,ScoreActivity.class);
            intent.putExtra("score",right_ans);
            startActivity(intent);
        }

    }


    public void submit_Value(){
        if(str_correct_ans.contains(correct_Answer[count])){
            right_ans=right_ans+1;
            Log.d("right_ans",String.valueOf(right_ans));
        }
        str_correct_ans="";
    }
    @Override
    public void onBackPressed() {
        return;
    }
}
