package com.crackingMBA.training.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.PreparationContentActivity;
import com.crackingMBA.training.PreparationHLContentActivity;
import com.crackingMBA.training.R;
import com.crackingMBA.training.StartMockTestActivity;
import com.crackingMBA.training.TargetVideoActivity;
import com.crackingMBA.training.VideoApplication;
import com.crackingMBA.training.interfaces.ClickListener;
import com.crackingMBA.training.pojo.MockTestTest;
import com.crackingMBA.training.pojo.RetrofitPrepHLContent;

import java.lang.ref.WeakReference;
import java.util.List;

public class PreparationHLContentAdapter extends RecyclerView.Adapter<PreparationHLContentAdapter.QuestionViewHolder>{
    private List<RetrofitPrepHLContent> questions;
    private int rowLayout;
    public Context context;
    private final ClickListener listener;

    public PreparationHLContentAdapter(List<RetrofitPrepHLContent> questions, int rowLayout, Context context, ClickListener listener) {
        this.questions = questions;
        this.rowLayout = rowLayout;
        this.context = context;
        this.listener = listener;

    }

    @Override
    public PreparationHLContentAdapter.QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new QuestionViewHolder(view, listener);
    }


    @Override
    public void onBindViewHolder(QuestionViewHolder holder, final int position) {
        holder.prep_study_day_name_tv.setText(questions.get(position).getName());



        if(questions.get(position).getStudy1().toString().length()>0){
            setContentNames(holder.prep_study1_tv,questions.get(position).getStudy1(), questions.get(position).getStudy1Type() );


            //holder.prep_study1_tv.setVisibility(View.VISIBLE);

            //String str1 = questions.get(position).getStudy1Type();
            //String substr1 = str1.substring(0,str1.indexOf(","));
/*
            if(questions.get(position).getStudy1Type().toString().equals("video")||questions.get(position).getStudy1Type().toString().equals("mocktest")){
                String str = questions.get(position).getStudy1();
                String substr = str.substring(0,str.indexOf(","));
                holder.prep_study1_tv.setText(substr);
            }else{
                holder.prep_study1_tv.setText(questions.get(position).getStudy1());
            }*/


        }else{
            holder.prep_study1_tv.setVisibility(View.GONE);
        }

        if(questions.get(position).getStudy2().toString().length()>0){
            holder.prep_study2_tv.setVisibility(View.VISIBLE);


            if(questions.get(position).getStudy2Type().toString().equals("video")||questions.get(position).getStudy2Type().toString().equals("mocktest")){
                String str = questions.get(position).getStudy2();
                String substr = str.substring(0,str.indexOf(","));
                holder.prep_study2_tv.setText(substr);
            }else{
                holder.prep_study2_tv.setText(questions.get(position).getStudy2());
            }


        }else{
            holder.prep_study2_tv.setVisibility(View.GONE);
        }

        if(questions.get(position).getStudy3().toString().length()>0){
            holder.prep_study3_tv.setVisibility(View.VISIBLE);


            if(questions.get(position).getStudy3Type().toString().equals("video")||questions.get(position).getStudy3Type().toString().equals("mocktest")){
                String str = questions.get(position).getStudy3();
                String substr = str.substring(0,str.indexOf(","));
                holder.prep_study3_tv.setText(substr);
            }else{
                holder.prep_study3_tv.setText(questions.get(position).getStudy3());
            }


        }else{
            holder.prep_study3_tv.setVisibility(View.GONE);
        }

        if(questions.get(position).getStudy4().toString().length()>0){
            holder.prep_study4_tv.setVisibility(View.VISIBLE);


            if(questions.get(position).getStudy4Type().toString().equals("video")||questions.get(position).getStudy4Type().toString().equals("mocktest")){
                String str = questions.get(position).getStudy4();
                String substr = str.substring(0,str.indexOf(","));
                holder.prep_study4_tv.setText(substr);
            }else{
                holder.prep_study4_tv.setText(questions.get(position).getStudy4());
            }


        }else{
            holder.prep_study4_tv.setVisibility(View.GONE);
        }

        if(questions.get(position).getStudy5().toString().length()>0){
            holder.prep_study5_tv.setVisibility(View.VISIBLE);


            if(questions.get(position).getStudy5Type().toString().equals("video")||questions.get(position).getStudy5Type().toString().equals("mocktest")){
                String str = questions.get(position).getStudy5();
                String substr = str.substring(0,str.indexOf(","));
                holder.prep_study5_tv.setText(substr);
            }else{
                holder.prep_study5_tv.setText(questions.get(position).getStudy5());
            }


        }else{
            holder.prep_study5_tv.setVisibility(View.GONE);
        }

        if(questions.get(position).getStudy6().toString().length()>0){
            holder.prep_study6_tv.setVisibility(View.VISIBLE);


            if(questions.get(position).getStudy6Type().toString().equals("video")||questions.get(position).getStudy6Type().toString().equals("mocktest")){
                String str = questions.get(position).getStudy6();
                String substr = str.substring(0,str.indexOf(","));
                holder.prep_study6_tv.setText(substr);
            }else{
                holder.prep_study6_tv.setText(questions.get(position).getStudy6());
            }


        }else{
            holder.prep_study6_tv.setVisibility(View.GONE);
        }
    }

    private void setContentNames(TextView tv,String study, String studyType) {
            tv.setVisibility(View.VISIBLE);

            //String str1 = questions.get(position).getStudy1Type();
            //String substr1 = str1.substring(0,str1.indexOf(","));

            if (studyType.equals("video") || studyType.equals("mocktest")) {
                String str = study;
                String substr = str.substring(0, str.indexOf(","));
                tv.setText(substr);
            } else {
                tv.setText(study);
            }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView prep_study_day_name_tv, prep_study1_tv, prep_study2_tv, prep_study3_tv;
        TextView prep_study4_tv, prep_study5_tv, prep_study6_tv;
        private WeakReference<ClickListener> listenerRef;


        public QuestionViewHolder(View v, ClickListener listener) {
            super(v);

            listenerRef = new WeakReference<>(listener);
            prep_study_day_name_tv = (TextView) v.findViewById(R.id.prep_study_day_name_tv);
            prep_study1_tv = (TextView) v.findViewById(R.id.prep_study1_tv);
            prep_study2_tv = (TextView) v.findViewById(R.id.prep_study2_tv);
            prep_study3_tv = (TextView) v.findViewById(R.id.prep_study3_tv);
            prep_study4_tv = (TextView) v.findViewById(R.id.prep_study4_tv);
            prep_study5_tv = (TextView) v.findViewById(R.id.prep_study5_tv);
            prep_study6_tv = (TextView) v.findViewById(R.id.prep_study6_tv);


            prep_study1_tv.setOnClickListener(this);
            prep_study2_tv.setOnClickListener(this);
            prep_study3_tv.setOnClickListener(this);
            prep_study4_tv.setOnClickListener(this);
            prep_study5_tv.setOnClickListener(this);
            prep_study6_tv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == prep_study1_tv.getId()){
                    //Toast.makeText(v.getContext(), String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    String tempStr=String.valueOf(getAdapterPosition())+"1";
                    listenerRef.get().onPositionClicked(Integer.parseInt(tempStr));
                }
            if (v.getId() == prep_study2_tv.getId()){
                //Toast.makeText(v.getContext(), String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                //listenerRef.get().onPositionClicked(22);
                String tempStr=String.valueOf(getAdapterPosition())+"2";
                listenerRef.get().onPositionClicked(Integer.parseInt(tempStr));
            }
            if (v.getId() == prep_study3_tv.getId()){
                //Toast.makeText(v.getContext(), String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                String tempStr=String.valueOf(getAdapterPosition())+"3";
                listenerRef.get().onPositionClicked(Integer.parseInt(tempStr));
            }
            if (v.getId() == prep_study4_tv.getId()){
                String tempStr=String.valueOf(getAdapterPosition())+"4";
                listenerRef.get().onPositionClicked(Integer.parseInt(tempStr));
            }
            if (v.getId() == prep_study5_tv.getId()){
                String tempStr=String.valueOf(getAdapterPosition())+"5";
                listenerRef.get().onPositionClicked(Integer.parseInt(tempStr));
            }
            if (v.getId() == prep_study6_tv.getId()){
                String tempStr=String.valueOf(getAdapterPosition())+"6";
                listenerRef.get().onPositionClicked(Integer.parseInt(tempStr));
            }

                //Intent intent = new Intent(v.getContext(),PreparationContentActivity.class);
                //startActivity(intent);
                //listenerRef.get().onPositionClicked(getAdapterPosition());


        }
        }


/*    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView prep_study_day_name_tv, prep_study1_tv, prep_study2_tv, prep_study3_tv;
        TextView prep_study4_tv, prep_study5_tv, prep_study6_tv;

        public QuestionViewHolder(View v) {
            super(v);
            prep_study_day_name_tv = (TextView) v.findViewById(R.id.prep_study_day_name_tv);
            prep_study1_tv = (TextView) v.findViewById(R.id.prep_study1_tv);
            prep_study2_tv = (TextView) v.findViewById(R.id.prep_study2_tv);
            prep_study3_tv = (TextView) v.findViewById(R.id.prep_study3_tv);
            prep_study4_tv = (TextView) v.findViewById(R.id.prep_study4_tv);
            prep_study5_tv = (TextView) v.findViewById(R.id.prep_study5_tv);
            prep_study6_tv = (TextView) v.findViewById(R.id.prep_study6_tv);
        }
    }*/




}

