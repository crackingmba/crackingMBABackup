package com.crackingMBA.training;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.HashMap;

import com.crackingMBA.training.pojo.VideoDataObject;

/**
 * Created by MSK on 24-01-2017.
 */
public class PreparationFragment extends Fragment implements View.OnClickListener{

    TextView prepMsg;
    private static String TAG = "PreparationFragment";
    View rootView;
    LayoutInflater inflater;
    ViewGroup container;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerView.Adapter sectionAdapter;
    RecyclerView.Adapter weekAdapter;
    boolean isMock;

    //For Video functionality
    MediaController controller;
    VideoView surfaceView;
    DownloadManager downloadManager;
    String fullPath="http://3gp.telugump4.org/med/Chikki_Chikki_Bam_Bam_-_Aadhi_(HD_DTH_Rip).3gp";
    long downloadId;
    Button downloadBtn;
    View rootview1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        Log.d(TAG, VideoApplication.videoSelected.getVideoType()+" is selected");
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        isMock = pref.getBoolean("isMock",false);
        String clicked = VideoApplication.videoSelected.getVideoType()==null ? "startup" : VideoApplication.videoSelected.getVideoType();
        if(clicked.equals("startup")){
            VideoApplication.videoSelected.setVideoType("startup");

            rootView = inflater.inflate(R.layout.fragment_preparation_startup, container, false);

            TableRow quantRow = (TableRow) rootView.findViewById(R.id.quantrow);
            TableRow diRow = (TableRow) rootView.findViewById(R.id.dirow);
            TableRow verbalRow = (TableRow) rootView.findViewById(R.id.verbalrow);
            quantRow.setOnClickListener(this);
            diRow.setOnClickListener(this);
            verbalRow.setOnClickListener(this);
        }

        return rootView;

    }
    @Override
    public void onResume() {
        super.onResume();


    }


    @Override
    public void onClick(View v) {
        Log.d(TAG,"Clicked a week.."+v.getId());
        VideoDataObject vdo = new VideoDataObject();
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        Fragment newFrag = null;
        Intent dashboardIntent=new Intent(getActivity(),VideoSubCategoryActivity.class);
        switch (v.getId()){
            case R.id.quantrow:
                dashboardIntent.putExtra("sectionaName","quant");
                break;
            case R.id.dirow:
                dashboardIntent.putExtra("sectionaName","dilr");
                break;
            case R.id.verbalrow:
                dashboardIntent.putExtra("sectionaName","verbal");
                break;
            default:
                Log.d(TAG,"Unknown button clicked..");
                break;
        }
        startActivity(dashboardIntent);
    }


    private long downloadData(Uri uri) {

        downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle("crackMBA.com");
        request.setDescription("Downloading Aptitude Video..");

        String fileName = "/video.mp4";

        // String fileName = "/video.mp4";
        Log.d("suresh","Entered into delete video");
        String filePath1 = CrackingConstant.localstoragepath + fileName;
        File file1 = new File(filePath1);


        file1.delete();


        String filePath = CrackingConstant.localstoragepath+ fileName;
        File file = new File(filePath);
        Uri destUri = Uri.fromFile(file);
        request.setDestinationUri(destUri);

        return downloadManager.enqueue(request);
    }

    private static Bitmap retriveVideoFrameFromVideo(String videoPath)
            throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime(1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String videoPath)"
                            + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

    public void deleteVideo(View view){
        String fileName = "/video.mp4";
        Log.d("suresh","Entered into delete video");
        String filePath = CrackingConstant.localstoragepath + fileName;
        File file = new File(filePath);


        file.delete();

        Log.d("suresh","Exit into delete video");
        Toast toast = Toast.makeText(getActivity(), "Video has been deleted", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 25, 400);
        toast.show();
    }


   


}
