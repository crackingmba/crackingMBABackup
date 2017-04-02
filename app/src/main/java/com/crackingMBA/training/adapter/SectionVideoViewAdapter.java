package com.crackingMBA.training.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

import com.crackingMBA.training.CrackingConstant;
import com.crackingMBA.training.R;
import com.crackingMBA.training.VideoApplication;
import com.crackingMBA.training.pojo.SubCatList;
import com.crackingMBA.training.pojo.SubCategories;
import com.crackingMBA.training.pojo.VideoDataObject;

/**
 * Created by Harish on 1/31/2017.
 */
//This is for displaying the list of subcategories after clicking on Category in Preparation Fragment
public class SectionVideoViewAdapter extends RecyclerView
        .Adapter<SectionVideoViewAdapter.DataObjectHolder>{
    private static String LOG_TAG = "SectionVideoViewAdapter";
    private ArrayList<SubCatList> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
          implements View
            .OnClickListener {
        TextView id;
        TextView category_name;
        TextView sub_category_name, subcatDateRange, subcatVideoYN, sub_category_description;
        ImageView thumbnail, subcat_right_arrow;
        LinearLayout linlayout;

        public DataObjectHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.section_thumbnail);
            subcat_right_arrow = (ImageView) itemView.findViewById(R.id.subcat_right_arrow);
            id = (TextView) itemView.findViewById(R.id.section_id);
            sub_category_name = (TextView) itemView.findViewById(R.id.section_name);
            sub_category_description = (TextView) itemView.findViewById(R.id.subcat_description);
            category_name = (TextView) itemView.findViewById(R.id.section_category_name);
            subcatDateRange = (TextView) itemView.findViewById(R.id.subcat_date_range);
            subcatVideoYN = (TextView) itemView.findViewById(R.id.subcat_video_yn);
            linlayout=(LinearLayout)itemView.findViewById(R.id.subcategory_img_bg_layout);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }

    //The methods of the RecyclerView Adapter start here

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public SectionVideoViewAdapter(ArrayList<SubCatList> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        Log.d(LOG_TAG,"in onCreateViewHolder..");
        String clicked = VideoApplication.videoSelected.getVideoType()==null ? "startup" : VideoApplication.videoSelected.getVideoType();
        Log.d(LOG_TAG," Clicked is "+clicked);
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sectionvideo_layout, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        String img_resource="numbers1";

        Log.d(LOG_TAG,"in onBindViewHolder..");
        holder.id.setText(mDataset.get(position).getId());
        holder.sub_category_name.setText(mDataset.get(position).getSubcategory_description());
        holder.sub_category_description.setText(mDataset.get(position).getSubcat_descrip1());
        holder.category_name.setText(mDataset.get(position).getCategory_name());
        holder.subcatDateRange.setText(mDataset.get(position).getDate_range());
        holder.subcatVideoYN.setText(mDataset.get(position).getVideo_yn());

        Context context= holder.thumbnail.getContext();

        switch(mDataset.get(position).getCategory_name()){
            case "quant":{
                img_resource="quant";
                holder.linlayout.setBackgroundColor(Color.parseColor("#82B1FF"));
                break;
            }
            case "dilr":{
                img_resource="dilr";
                holder.linlayout.setBackgroundColor(Color.parseColor("#FFAB40"));
                break;
            }
            case "verbal":{
                img_resource="verbal";
                holder.linlayout.setBackgroundColor(Color.parseColor("#EF9A9A"));
                break;
            }

        }

        if(mDataset.get(position).getVideo_yn().equals("y")){
            int id1=context.getResources().getIdentifier("right_arrow", "drawable", context.getPackageName());
            holder.subcat_right_arrow.setImageResource(id1);
        }

        //int id=context.getResources().getIdentifier(mDataset.get(position).getThumbnail(), "drawable", context.getPackageName());
        int id=context.getResources().getIdentifier(img_resource, "drawable", context.getPackageName());
        holder.thumbnail.setImageResource(id);


    }

    public void addItem(SubCatList dataObj, int index) {
        Log.d(LOG_TAG,"in addItem..");
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        Log.d(LOG_TAG,"in deleteView..");
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
