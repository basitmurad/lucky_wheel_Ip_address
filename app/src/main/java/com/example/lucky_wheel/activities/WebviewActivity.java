package com.example.lucky_wheel.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.lucky_wheel.Provide;
import com.example.lucky_wheel.R;
import com.example.lucky_wheel.WebviewOpenActivityActivity;

import com.example.lucky_wheel.databinding.ActivityWebviewBinding;
import com.example.lucky_wheel.session.SessionManager;

public class WebviewActivity extends AppCompatActivity {


    ActivityWebviewBinding binding;
    Provide provide;
    SessionManager sessionManager;
    String urlGoogle = "https://www.google.com";
    String urlTwitter ="https://www.twitter.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityWebviewBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        provide = new Provide(this,this);
        sessionManager = new SessionManager(this);







        binding.btnGoogle.setOnClickListener(view -> {
           Intent intent = new Intent(WebviewActivity.this, WebviewOpenActivityActivity.class);
           intent.putExtra("google",urlGoogle);
           startActivity(intent);

        });

        binding.btnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WebviewActivity.this, WebviewOpenActivityActivity.class);
                intent.putExtra("twitter",urlTwitter);
                startActivity(intent);


            }
        });






        if (sessionManager.fetchIsSecond()) {

            if (sessionManager.fetchIsRated()) {
                Log.d(TAG, "Rated already");
            } else {
                provide.showAlertDialog();
            }
        } else {
            Log.d(TAG, "NO dialog to show");
            sessionManager.saveIsSecond(true);

        }

    }


}