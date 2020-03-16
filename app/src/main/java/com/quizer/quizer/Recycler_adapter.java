package com.quizer.quizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.com.quizer.quizer.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Recycler_adapter extends RecyclerView.Adapter<Recycler_adapter.MyViewHolder> {
    JSONArray optionArrayList;
    Context context;
    String str_correct_ans="";
    private int lastSelectedPosition = -1;
    public GetSelectedOption getSelectedOption;

    public Recycler_adapter(JSONArray optionArrayList, Context context, GetSelectedOption getSelectedOption) {
        this.optionArrayList=optionArrayList;
        this.context=context;
        this.getSelectedOption=getSelectedOption;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_adapter,
                viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        String  option = null;
        try {
            option = optionArrayList.get(position).toString();
            myViewHolder.tv_answer.setText(option);
            //since only one radio button is allowed to be selected,
            // this condition un-checks previous selections
            myViewHolder.radioButton.setChecked(lastSelectedPosition == position);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return optionArrayList.length();
    }

    public interface GetSelectedOption {
        void getCorrectAns(String answer);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_answer;
        public RadioButton radioButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_answer=itemView.findViewById(R.id.tv_answer);
            radioButton=itemView.findViewById(R.id.radioButton);
            View.OnClickListener onClickListener=new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition=getAdapterPosition();
                    notifyDataSetChanged();
                    str_correct_ans=tv_answer.getText().toString();
                    getSelectedOption.getCorrectAns(str_correct_ans);

                }
            };

            tv_answer.setOnClickListener(onClickListener);
            radioButton.setOnClickListener(onClickListener);
        }
    }
}
