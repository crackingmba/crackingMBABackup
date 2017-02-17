package com.crackingMBA.training.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.crackingMBA.training.pojo.VideoDataObject;
import com.crackingMBA.training.pojo.VideoList;

/**
 * Created by MSK on 05-02-2017.
 */
public class DBHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "CrackingMBA";

    // Contacts table name
    private static final String TABLE_DOWNLOAD = "DownloadVideosTable";

    // Contacts Table Columns names
    private String id="id";
    private String videoTitle="videoTitle";
    private String thumbnailURL="thumbnailURL";
    private String videoURL="videoURL";
    private String videoType="videoType";
    private String uploadedDate="uploadedDate";
    private String duration="duration";
    private String videoDescription="videoDescription";
    private static DBHelper dbInstance = null;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DBHelper getInstance(Context mContext) {
        if (dbInstance == null) {
            Log.d("first","db has been created");
            dbInstance = new DBHelper(mContext);
        }

        return dbInstance;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_DOWNLOAD + "("
                + id + " INTEGER PRIMARY KEY," + videoTitle + " TEXT,"
                + thumbnailURL + " TEXT," + videoURL + " TEXT," + videoType + " TEXT," + uploadedDate + " TEXT," + duration + " TEXT," + videoDescription + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        Log.d("first","data base created scesfully");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOWNLOAD);

        // Create tables again
        onCreate(db);
    }


    // Adding new Video record
    public void addDownloadVideo(VideoList videoList) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(id, videoList.getVideoID()); // video id
        values.put(videoTitle, videoList.getVideoTitle()); // Contact Phone Number
        values.put(thumbnailURL, videoList.getThumbnailURL());
        values.put(videoURL, videoList.getVideoURL());
        values.put(videoType, videoList.getVideoCategory());
        values.put(uploadedDate, videoList.getUploadDate());
        values.put(duration, videoList.getDuration());
        values.put(videoDescription, videoList.getVideoDescription());
        // Inserting Row
        db.insert(TABLE_DOWNLOAD, null, values);
        Log.d("first","data inserted succesfully");
        db.close(); // Closing database connection

    }

    // Getting single contact
    public VideoDataObject getVideo(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_DOWNLOAD, null);
        if (cursor != null)
            cursor.moveToFirst();

        VideoDataObject videoDataObject = new VideoDataObject(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getColumnName(3),
                cursor.getString(4), cursor.getColumnName(5), cursor.getString(6), cursor.getColumnName(7));
        // return videoDataObject
        return videoDataObject;
    }

    // Getting All Contacts
    public List<VideoList> getAllDownloadedVideos() {

        List<VideoList> videoDataObjects = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DOWNLOAD;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
               // VideoList videoDataObject = new VideoList();
                VideoList    videoDataObject = new VideoList(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
                // Adding contact to list
                videoDataObjects.add(videoDataObject);
            } while (cursor.moveToNext());
        }
        Log.d("first","fetched  all vide records succesfully");
        // return contact list
        return videoDataObjects;
    }

    // Getting VideoRecord Count
    public int getContactsCount() {

        String countQuery = "SELECT  * FROM " + TABLE_DOWNLOAD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating single contact
    public int updateVideoRecord(VideoList contact) {
        return 0;
    }

    // Deleting single VideoRecord
    public void deleteVideoRecord(VideoList videoDataObject) {
        Log.d("first","videoDataObject.getVideoURL()"+videoDataObject.getVideoURL());
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DOWNLOAD, id + " = ?",
                new String[] { String.valueOf(videoDataObject.getVideoID()) });
        db.close();
    }
}
