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

        recyclerView=(RecyclerView)findViewById(R.id.section_recycler_view2);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        getSectionDataSet();


    }

    private void getSectionDataSet() {
        String url = "http://www.crackingmba.com/getSubCategories.php?category="+VideoApplication.sectionClicked;

        Log.d(TAG,"Section Data"+url);
        try {
            AsyncHttpClient client = new AsyncHttpClient();
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
                        headerTitle = ((TextView) findViewById(R.id.CategoryTitle));
                        headerTitle.setText(subCategories.getSubCatTitle());


                        if (null != sectionAdapter) {
                            ((SectionVideoViewAdapter) sectionAdapter).setOnItemClickListener(
                                    new SectionVideoViewAdapter.MyClickListener() {
                                        @Override
                                        public void onItemClick(int position, View v) {
                                            Log.d(TAG, "Video Sub Cateogry, Clicked item at position : " + position);
                                            //VideoDataObject vdo = populateVideoDataObject(v);//new VideoDataObject();
                                            SubCatList scl = populateSubCatList(v);
                                            Log.d(TAG, "Selected Video Category : " + scl.getName());

                                            Intent weeksIntent = new Intent(getApplicationContext(), WeeksActivity.class);
                                            weeksIntent.putExtra("sectionSelected", scl.getName());
                                            weeksIntent.putExtra("headerTitle", headerTitle.getText());
                                            weeksIntent.putExtra("subcategoryid", scl.getId());
                                            weeksIntent.putExtra("subcategoryid", scl.getName());
                                            VideoApplication.subcategorySelected=scl.getName();
                                            startActivity(weeksIntent);
                                        }
                                    }
                            );
                        }
                    }
                    else{
                        (( TextView) findViewById(R.id.SubCatNotAvailable)).setVisibility(View.VISIBLE);
                        Log.d(TAG,"There is no subcategories for the category selected");
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
        vdo.setId(id.getText().toString());
        vdo.setName(name.getText().toString());
        vdo.setCategory_name(category_name.getText().toString());
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

}
