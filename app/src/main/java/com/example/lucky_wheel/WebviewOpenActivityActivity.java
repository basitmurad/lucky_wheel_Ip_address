package com.example.lucky_wheel;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.lucky_wheel.databinding.ActivityWebviewOpenActivityBinding;
import com.example.lucky_wheel.session.SessionManager;


public class WebviewOpenActivityActivity extends AppCompatActivity {

    ActivityWebviewOpenActivityBinding binding;


    private SessionManager sessionManager;
    private Provide provide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebviewOpenActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sessionManager = new SessionManager(this);
        provide = new Provide(this,this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }




        String urlGoogle = getIntent().getStringExtra("google");
        String urlTwitter= getIntent().getStringExtra("twitter");



//        if (sessionManager.fetchIsSecond()) {
//
//            if (sessionManager.fetchIsRated()) {
//                Log.d("TAG", "Rated already");
//            } else {
//                provide.showAlertDialog();
//            }
//        } else {
//            Log.d("TAG", "NO dialog to show");
//            sessionManager.saveIsSecond(true);
//        }


            binding.webview.getSettings().setJavaScriptEnabled(true);
            binding.webview.setWebViewClient(new WebViewClient());
            binding.webview.loadUrl(urlGoogle);
            binding.webview.setVisibility(View.VISIBLE);




                binding.webview.getSettings().setJavaScriptEnabled(true);
                binding.webview.setWebViewClient(new WebViewClient());
                binding.webview.loadUrl(urlTwitter);
                binding.webview.setVisibility(View.VISIBLE);



    }

    @Override
    public void onBackPressed() {

        if(binding.webview.canGoBack()){
            binding.webview.goBack();
        }else{
            finish();
        }
    }}