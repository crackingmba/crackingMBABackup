package com.crackingMBA.training;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.crackingMBA.training.util.MyUtil;

public class ViewPDFDetailsActivity extends AppCompatActivity {
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdfdetails);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        webview = (WebView) findViewById(R.id.my_webview1);
        webview.getSettings().setJavaScriptEnabled(true);


        ProgressDialog pDialog = new ProgressDialog(ViewPDFDetailsActivity.this);
        pDialog.setTitle("PDF");
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        webview.setWebViewClient(new MyWebViewClient());
        webview.loadUrl("https://drive.google.com/file/d/0B534aayZ5j7Yc3RhcnRlcl9maWxl/view?usp=sharing");
        MyUtil.hideProgressDialog();

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);

            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(ViewPDFDetailsActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            MyUtil.showProgressDialog(ViewPDFDetailsActivity.this);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            MyUtil.hideProgressDialog();
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
