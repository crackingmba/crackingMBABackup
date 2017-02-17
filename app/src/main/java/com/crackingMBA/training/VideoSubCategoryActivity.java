package com.crackingMBA.training;

import android.app.ProgressDialog;
import android.content.Intent;
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
    TextView prepMsg;
    private static String TAG = "SectionFragment";
    View rootView;
    LayoutInflater inflater;
    ViewGroup container;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerView.Adapter sectionAdapter;
    boolean isMock;
    private ProgressDialog pDialog;
    TextView headerTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_sub_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView=(RecyclerView)findViewById(R.id.section_recycler_view2);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        getSectionDataSet();


    }

    private void getSectionDataSet() {


        final ArrayList<SubCategories> results = new ArrayList<SubCategories>();
        String url = "http://crackingmba.com/getSubCategories.php";
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, null, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    Log.d(TAG,"Response is : "+response);
                    Gson gson = new Gson();
                    SubCategories subCategories = gson.fromJson(response,SubCategories.class);
                    Log.d(TAG,"subCategories : "+subCategories);
                    sectionAdapter = new SectionVideoViewAdapter(subCategories.getSubCatList());
                    recyclerView.setAdapter(sectionAdapter);
                    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL);
                    recyclerView.addItemDecoration(itemDecoration);
                    headerTitle= ((TextView)findViewById(R.id.SubCatTitle));
                    headerTitle.setText(subCategories.getSubCatTitle());


                    if (null != sectionAdapter) {
                        ((SectionVideoViewAdapter) sectionAdapter).setOnItemClickListener(
                                new SectionVideoViewAdapter.MyClickListener() {
                                    @Override
                                    public void onItemClick(int position, View v) {
                                        Log.d(TAG, "Video Sub Cateogry, Clicked item at position : " + position);
                                        //VideoDataObject vdo = populateVideoDataObject(v);//new VideoDataObject();
                                        SubCatList scl = populateSubCastList(v);
                                        Log.d(TAG, "Selected Video Category : " + scl.getName());

                                        Intent weeksIntent=new Intent(getApplicationContext(),WeeksActivity.class);
                                       weeksIntent.putExtra("sectionSelected",scl.getName());
                                        weeksIntent.putExtra("headerTitle",headerTitle.getText());
                                        startActivity(weeksIntent);
                                       // Log.d(TAG, "set with week.."+scl);
                                        //VideoApplication.videoSelected = vdo;
                                        //getFragmentManager().beginTransaction().replace(container.getId(), newFrag).commit();
                                    }
                                }
                        );
                    }
                }

                @Override
                public void onFailure(int statusCode, Throwable error,
                                      String content) {
                    Log.d(TAG,"Status is "+statusCode+ " and "+content);
                    if (statusCode == 404) {
                        Log.d(TAG,"Requested resource not found");
                    }
                    else if (statusCode == 500) {
                        Log.d(TAG, "Something went wrong at server end");
                    }
                    else {
                        Log.d(TAG,"Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]");
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();;
        }



    }



    public ArrayList<VideoDataObject> populateMockGetVideoList(){
        ArrayList<VideoDataObject> mockResults = new ArrayList<VideoDataObject>();
        VideoDataObject vo = null;
        for(int i=0;i<2;i++){
            vo = new VideoDataObject("1","Ratio & Proportion Day 1","http://crackingmba.com/video.3gp","http://crackingmba.com/video.3gp","video","02-01-2017","325","This is the first day tutorial of Ratio and Proportion");
            mockResults.add(vo);
        }
        return mockResults;
    }




    @Override
    public void onResume() {
        super.onResume();
   /*if (null != sectionAdapter) {
            ((SectionVideoViewAdapter) sectionAdapter).setOnItemClickListener(
                    new SectionVideoViewAdapter.MyClickListener() {
                        @Override
                        public void onItemClick(int position, View v) {
                            Log.d(TAG, "Section adapter, Clicked item at position : " + position);
                            //VideoDataObject vdo = populateVideoDataObject(v);//new VideoDataObject();
                            SubCatList scl = populateSubCastList(v);
                            Log.d(TAG, "set with week.."+scl);
                            //VideoApplication.videoSelected = vdo;
                            //getFragmentManager().beginTransaction().replace(container.getId(), newFrag).commit();
                        }
                    }
            );
        }*/

    }

    private SubCatList populateSubCastList(View v){
        Log.d(TAG,"Populating videoDataObject in Preparation Fragment..");
        SubCatList vdo = new SubCatList();
        TextView id = (TextView) v.findViewById(R.id.section_id);
        TextView name = (TextView) v.findViewById(R.id.section_name);
        TextView category_name = (TextView) v.findViewById(R.id.section_category_name);
        vdo.setId(id.getText().toString());
        vdo.setName(name.getText().toString());
        vdo.setCategory_name(category_name.getText().toString());
        return vdo;
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

    private ArrayList<VideoDataObject> getDataSet() {

        Log.d(TAG,"isMock?"+isMock);
        isMock=
                true;
        if(isMock){
            return populateMockGetVideoList();
        }else{
            Log.d(TAG,"In else block");
            final ArrayList<VideoDataObject> results = new ArrayList<VideoDataObject>();
            String url = "http://www.crackingmba.com/getVideoList.php";
            try {
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(url, null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG,"Response is : "+response);
                        Gson gson = new Gson();
                        VideoDataObject vdo = gson.fromJson(response,VideoDataObject.class);
                        Log.d(TAG,"VDO : "+vdo);
                        results.add(vdo);
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        Log.d(TAG,"Status is "+statusCode+ " and "+content);
                        if (statusCode == 404) {
                            Log.d(TAG,"Requested resource not found");
                        }
                        else if (statusCode == 500) {
                            Log.d(TAG, "Something went wrong at server end");
                        }
                        else {
                            Log.d(TAG,"Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]");
                        }
                    }
                });
            }catch (Exception e){
                e.printStackTrace();;
            }
            return results;
        }

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

}
