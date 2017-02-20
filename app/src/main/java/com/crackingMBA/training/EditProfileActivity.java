package com.crackingMBA.training;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * A login screen that offers login via email/password.
 */
public class EditProfileActivity extends AppCompatActivity {

    TextView msg;
    String url;
    boolean isMock;
    String loggedInUserName;
    String loggedInProfilePicUrl;
    String updatedProfilePicUrl;
    static String TAG = "EditProfileActivity";

    private static Bitmap Image = null;
    private static Bitmap rotateImage = null;
    private ImageView imageView;
    private static final int GALLERY = 1;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreate..");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        isMock = pref.getBoolean("isMock",false);
        loggedInUserName = pref.getString("loggedInUserName","Guest");
        loggedInProfilePicUrl = pref.getString("loggedInProfilePicUrl","");
        imageView = (ImageView) findViewById(R.id.editprofile_pic);

        if(null != loggedInProfilePicUrl){
            Bitmap bitmapImage = null;
            File f = new File(loggedInProfilePicUrl);
            try {
                bitmapImage = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), Uri.fromFile(f));
            }catch (Exception e){
                e.printStackTrace();;
            }
            if(null != bitmapImage)
                imageView.setImageBitmap(bitmapImage);
        }

        msg = (TextView) findViewById(R.id.editprofile_msg);

    }

    public void changeProfilePic(View v){
        imageView.setImageBitmap(null);
        if (Image != null)
            Image.recycle();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY);
    }

    public void saveProfile(View v){
        isMock = false;
        if(isMock){
            msg.setText("Profile Saved Successfully");
            msg.setTextColor(Color.BLUE);
        }else{
            String firstName = ((EditText)findViewById(R.id.editprofile_fname)).getText().toString();
            String lastName = ((EditText)findViewById(R.id.editprofile_lname)).getText().toString();
            String fullName = firstName+" "+lastName;
            SharedPreferences.Editor editor = pref.edit();
            if(!fullName.equals(loggedInUserName)){
                editor.putString("loggedInUserName",fullName);
            }
            if(!loggedInProfilePicUrl.equals(updatedProfilePicUrl) && null!=updatedProfilePicUrl && ""!=updatedProfilePicUrl){
                editor.putString("loggedInProfilePicUrl",updatedProfilePicUrl);
            }
            editor.commit();
            msg.setText("Profile Saved Successfully");
            msg.setTextColor(Color.BLUE);
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY && resultCode != 0) {
            Uri mImageUri = data.getData();
            updatedProfilePicUrl = getRealPathFromURI(mImageUri);
            try {
                Image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
                if (getOrientation(getApplicationContext(), mImageUri) != 0) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(getOrientation(getApplicationContext(), mImageUri));
                    if (rotateImage != null)
                        rotateImage.recycle();
                    rotateImage = Bitmap.createBitmap(Image, 0, 0, Image.getWidth(), Image.getHeight(), matrix,true);
                    imageView.setImageBitmap(rotateImage);
                } else
                    imageView.setImageBitmap(Image);

                Log.d(TAG,"Existing path : "+loggedInProfilePicUrl+"\n Updating path to : "+updatedProfilePicUrl);
            } catch (Exception e) {
                e.printStackTrace();
                msg.setText("Oops!! Problem occured while changing profile..");
                msg.setTextColor(Color.BLUE);
            }
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public static int getOrientation(Context context, Uri photoUri) {
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION },null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }
        cursor.moveToFirst();
        return cursor.getInt(0);
    }
}

