package com.crackingMBA.training;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.crackingMBA.training.util.MyUtil;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

import com.crackingMBA.training.adapter.DividerItemDecoration;
import com.crackingMBA.training.adapter.SectionVideoViewAdapter;
import com.crackingMBA.training.pojo.SubCatList;
import com.crackingMBA.training.pojo.SubCategories;
import com.crackingMBA.training.pojo.VideoDataObject;

public class VideoSubCategoryActivity extends AppCompatActivity {
    private static String TAG = "SectionFragment";
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerView.Adapter sectionAdapter;
    boolean isMock;
    TextView headerTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_sub_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);
        /*Drawable d=getResources().getDrawable(R.drawable.toolbar_bg);
        getSupportActionBar().setBackgroundDrawable(d);*/

        recyclerView=(RecyclerView)findViewById(R.id.section_recycler_view2);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        getSectionDataSet();


    }

    private void getSectionDataSet() {
        String url = "http://www.crackingmba.com/getSubCategories.php?category="+VideoApplication.sectionClicked;

        Log.d(TAG,"Section Data"+url);
        if(MyUtil.checkConnectivity(getApplicationContext())) {
            try {

                AsyncHttpClient client = new AsyncHttpClient();
                MyUtil.showProgressDialog(this);
                client.get(url, null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "Response is : " + response);
                        Gson gson = new Gson();
                        SubCategories subCategories = gson.fromJson(response, SubCategories.class);
                        Log.d(TAG, "subCategories : " + subCategories);
                        if (subCategories.getSubCatList() != null) {
                            sectionAdapter = new SectionVideoViewAdapter(subCategories.getSubCatList());
                            recyclerView.setAdapter(sectionAdapter);
                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL);
                            recyclerView.addItemDecoration(itemDecoration);
                            headerTitle = ((TextView) findViewById(R.id.SubCategoryTitle));
                            headerTitle.setText(subCategories.getSubCatTitle());


                            if (null != sectionAdapter) {
                                ((SectionVideoViewAdapter) sectionAdapter).setOnItemClickListener(
                                        new SectionVideoViewAdapter.MyClickListener() {
                                            @Override
                                            public void onItemClick(int position, View v) {
                                                Log.d(TAG, "Video Sub Cateogry, Clicked item at position : " + position);
                                                //VideoDataObject vdo = populateVideoDataObject(v);//new VideoDataObject();
                                                SubCatList scl = populateSubCatList(v);
                                                String video_yn=scl.getVideo_yn();

                                                if(video_yn.equals("y")){
                                                    Intent weeksIntent = new Intent(getApplicationContext(), WeeksActivity.class);
                                                    weeksIntent.putExtra("headerTitle", headerTitle.getText());
                                                    weeksIntent.putExtra("subcategoryid", scl.getId());
                                                    weeksIntent.putExtra("sectionSelected", scl.getSubcategory_description());
                                                    //weeksIntent.putExtra("subcategoryid", scl.getName());
                                                    VideoApplication.subcategoryID=scl.getId();
                                                    startActivity(weeksIntent);
                                                }else{
                                                    //do nothing here. we only display videos for y scenario
                                                }


                                            }
                                        }
                                );
                            }
                        } else {
                            ((TextView) findViewById(R.id.SubCatNotAvailable)).setVisibility(View.VISIBLE);
                            Log.d(TAG, "There is no subcategories for the category selected");
                        }
                        MyUtil.hideProgressDialog();
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        Log.d(TAG, "Status is " + statusCode + " and " + content);
                        if (statusCode == 404) {
                            Log.d(TAG, "Requested resource not found");
                        } else if (statusCode == 500) {
                            Log.d(TAG, "Something went wrong at server end");
                        } else {
                            Log.d(TAG, "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]");
                        }
                        MyUtil.hideProgressDialog();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                ;
            }
        }
            else{
                 int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(getApplicationContext(), R.string.no_internet, duration);
                toast.show();
                TextView textView=(TextView)findViewById(R.id.networkstatus);
                textView.setVisibility(View.VISIBLE);
            }



    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private SubCatList populateSubCatList(View v){
        Log.d(TAG,"Populating videoDataObject in Preparation Fragment..");
        SubCatList vdo = new SubCatList();
        TextView id = (TextView) v.findViewById(R.id.section_id);
        TextView name = (TextView) v.findViewById(R.id.section_name);
        TextView category_name = (TextView) v.findViewById(R.id.section_category_name);
        TextView subcatDateRange = (TextView) v.findViewById(R.id.subcat_date_range);
        TextView subcatVideoYN = (TextView) v.findViewById(R.id.subcat_video_yn);

        vdo.setId(id.getText().toString());
        vdo.setCategory_name(category_name.getText().toString());
        vdo.setSubcategory_description(name.getText().toString());
        vdo.setDate_range(subcatDateRange.getText().toString());
        vdo.setVideo_yn(subcatVideoYN.getText().toString());
        return vdo;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private VideoDataObject populateVideoDataObject(View v){
        Log.d(TAG,"Populating videoDataObject in Preparation Fragment..");
        VideoDataObject vdo = new VideoDataObject();
      /*
        TextView duration = (TextView) v.findViewById(R.id.duration);
        TextView id = (TextView) v.findViewById(R.id.id);
        TextView videoTitle = (TextView) v.findViewById(R.id.videoTitle);
        TextView thumbnailURL = (TextView) v.findViewById(R.id.thumbnailURL);
        TextView videoURL = (TextView) v.findViewById(R.id.videoURL);
        TextView videoType = (TextView) v.findViewById(R.id.videoType);
        TextView dateOfUploaded = (TextView) v.findViewById(R.id.dateOfUploaded);
        TextView videoDescription = (TextView) v.findViewById(R.id.videoDescription);
        vdo.setDuration(duration.getText().toString());
        vdo.setId(id.getText().toString());
        vdo.setVideoTitle(videoTitle.getText().toString());
        vdo.setThumbnailURL(thumbnailURL.getText().toString());
        vdo.setVideoURL(videoURL.getText().toString());
        vdo.setVideoType(videoType.getText().toString());
        vdo.setDateOdUploaded(dateOfUploaded.getText().toString());
        vdo.setVideoDescription(videoDescription.getText().toString());*/
        return vdo;
    }

}
