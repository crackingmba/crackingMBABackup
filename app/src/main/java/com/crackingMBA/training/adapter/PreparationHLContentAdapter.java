package com.crackingMBA.training.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crackingMBA.training.R;
import com.crackingMBA.training.interfaces.ClickListener;
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
        setContentNames(holder.prep_study1_iv, holder.prep_study1_tv,questions.get(position).getStudy1(), questions.get(position).getStudy1Type() );
        setContentNames(holder.prep_study2_iv, holder.prep_study2_tv,questions.get(position).getStudy2(), questions.get(position).getStudy2Type() );
        setContentNames(holder.prep_study3_iv, holder.prep_study3_tv,questions.get(position).getStudy3(), questions.get(position).getStudy3Type() );
        setContentNames(holder.prep_study4_iv, holder.prep_study4_tv,questions.get(position).getStudy4(), questions.get(position).getStudy4Type() );
        setContentNames(holder.prep_study5_iv, holder.prep_study5_tv,questions.get(position).getStudy5(), questions.get(position).getStudy5Type() );
        setContentNames(holder.prep_study6_iv, holder.prep_study6_tv,questions.get(position).getStudy6(), questions.get(position).getStudy6Type() );

      }

    private void setContentNames(ImageView iv, TextView tv, String study, String studyType) {
            if(study.length()>0){

                tv.setVisibility(View.VISIBLE);
                iv.setVisibility(View.VISIBLE);

                if(studyType.equals("video")){
                    String str = study;
                    String substr = str.substring(0, str.indexOf(","));
                    Drawable myDrawable = iv.getResources().getDrawable(R.drawable.videos_img);
                    iv.setImageDrawable(myDrawable);
                    tv.setText(substr);
                }

                if(studyType.equals("mocktest")){
                    String str = study;
                    String substr = str.substring(0, str.indexOf(","));
                    Drawable myDrawable = iv.getResources().getDrawable(R.drawable.mock_test_icon);
                    iv.setImageDrawable(myDrawable);
                    tv.setText(substr);
                }

                if(studyType.equals("text")){
                    Drawable myDrawable = iv.getResources().getDrawable(R.drawable.notes);
                    iv.setImageDrawable(myDrawable);
                    tv.setText(study);
                }

            }
            else{
                tv.setVisibility(View.GONE);
                iv.setVisibility(View.GONE);
            }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView prep_study_day_name_tv, prep_study1_tv, prep_study2_tv, prep_study3_tv;
        TextView prep_study4_tv, prep_study5_tv, prep_study6_tv;
        ImageView prep_study1_iv, prep_study2_iv, prep_study3_iv;
        ImageView prep_study4_iv, prep_study5_iv, prep_study6_iv;

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

            prep_study1_iv = (ImageView) v.findViewById(R.id.prep_study1_iv);
            prep_study2_iv = (ImageView) v.findViewById(R.id.prep_study2_iv);
            prep_study3_iv = (ImageView) v.findViewById(R.id.prep_study3_iv);
            prep_study4_iv = (ImageView) v.findViewById(R.id.prep_study4_iv);
            prep_study5_iv = (ImageView) v.findViewById(R.id.prep_study5_iv);
            prep_study6_iv = (ImageView) v.findViewById(R.id.prep_study6_iv);


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

        }
        }

}

