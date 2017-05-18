package com.crackingMBA.training;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.crackingMBA.training.pojo.LoginResponseObject;
import com.crackingMBA.training.pojo.Question;
import com.crackingMBA.training.util.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MSK on 24-01-2017.
 */
public class MyWhatsup extends Fragment implements View.OnClickListener {

    View rootView;
    TextView textView;

    private String TAG = MyWhatsup.class.getSimpleName();
    private ListView lv;
    ArrayList<HashMap<String, String>> contactList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_whatsup, container, false);

        contactList = new ArrayList<>();
        lv = (ListView) rootView.findViewById(R.id.list);

        new GetContacts().execute();
        return rootView;
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ListAdapter adapter = new SimpleAdapter(rootView.getContext(), contactList,
                    R.layout.fragment_whatsup_row_layout, new String[]{ "day_name","update"},
                    new int[]{R.id.day_name, R.id.update});
            lv.setAdapter(adapter);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://www.crackingmba.com/getWhatsUpList.php";
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try{
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("whatsup");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String day_name = c.getString("day_name");
                        String update = c.getString("update"); //updated section


                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("day_name", day_name);
                        contact.put("update", update);

                        // adding contact to contact list
                        contactList.add(contact);



                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }
    }



    @Override
    public void onClick(View v) {
        //Some click activity will happen here

    }





}
