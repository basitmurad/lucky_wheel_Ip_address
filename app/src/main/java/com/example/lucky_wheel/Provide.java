package com.example.lucky_wheel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;

import com.example.lucky_wheel.session.SessionManager;


public class Provide {

    private Context context;

    private Activity activity;
    private SessionManager sessionManager;
    private AlertDialog alert;
    private ReviewManager reviewManager;

    public Provide(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
     sessionManager = new SessionManager(context);
    }
    @SuppressLint("MissingInflatedId")
    public void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        final View customLayout = LayoutInflater.from(context).inflate(R.layout.custum_dialoge, null);


        alertDialog.setView(customLayout);
        alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(true);





        alert.show();


        customLayout.findViewById(R.id.btnRate).setOnClickListener(view -> {
            sessionManager.saveISRated(true);
            alert.cancel();
            try {
                Uri uri = Uri.parse("market://details?id=" + context.getPackageName() + "");
                Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(goMarket);
            } catch (ActivityNotFoundException e) {
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName() + "");
                Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(goMarket);
            }


    });


        customLayout.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.cancel();
            }
        });

        customLayout.findViewById(R.id.btnRemind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.cancel();
            }
        });

}



    public void showRateApp() {
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Getting the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();

                Task<Void> flow = reviewManager.launchReviewFlow(activity, reviewInfo);

                flow.addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
//                        if (task.isComplete() && task.isSuccessful()) {
//                            Toast.makeText(context, "Reviewed", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(context, "Not reviewed", Toast.LENGTH_SHORT).show();
//                        }
                    }
                });
            }
        });
    }
}

