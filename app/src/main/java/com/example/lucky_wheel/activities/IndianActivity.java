package com.example.lucky_wheel.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.lucky_wheel.R;
import com.example.lucky_wheel.databinding.ActivityIndianBinding;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class IndianActivity extends AppCompatActivity {

    ActivityIndianBinding binding;
    int count = 0;
    String randomNumber,randomnumber2;
    final int[] sectors = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    Random random = new Random();


    ImageView imageView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIndianBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        imageView = findViewById(R.id.wheel);


        Collections.reverse(Arrays.asList(sectors));



        binding.btnCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinWheel();
            }
        });




    }



    public void spinWheel()
    {
        Random rr = new Random();
        int degreeRR = rr.nextInt(360);
        RotateAnimation rotateAnimation = new RotateAnimation(0,degreeRR
        ,RotateAnimation.RELATIVE_TO_SELF,.5f,
                RotateAnimation.RELATIVE_TO_SELF,.5f);
        rotateAnimation.setDuration(3000);
        rotateAnimation.setFillAfter(true);

        rotateAnimation.setInterpolator( new DecelerateInterpolator());

        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                CalculatePoint(degreeRR);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }



        });


        float rotationAngle = 1440f; // For example, rotate 720 degrees for two complete spins

        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(binding.wheel, "rotation", 0f, rotationAngle);
        rotationAnimator.setDuration(2000); // Set the duration of the animation (in milliseconds)


        rotationAnimator.start();

        binding.wheel.startAnimation(rotateAnimation);

    }

    private void CalculatePoint(int degreeRR) {


        //total 360 ||10 segment|| 36 each segment
        int initialPoint = 0;
        int endpoint =36;
        int a= 0;
        String ress = null;
         do {
             if (degreeRR>initialPoint&&degreeRR<endpoint)
             {


                     ress = String.valueOf(sectors[a]-1);



             }
             initialPoint +=36; endpoint+=36;
             a++;
         }
         while (ress==null);




        int i =count++;
                if (i==0)
                { randomNumber = String.valueOf(random.nextInt(10));
                    binding.firstNo.setText(randomNumber);
//                    Toast.makeText(this, "First Number is  "+randomNumber, Toast.LENGTH_SHORT).show();
                }
                else if (i==1)
                {
                    randomnumber2 = String.valueOf(random.nextInt(10));
                      binding.secondNo.setText(randomnumber2);
//                    Toast.makeText(this, "Second Number is  "+randomNumber, Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(IndianActivity.this);
                    builder.setTitle("Your Lucky Number is ")
                            .setMessage(""+randomNumber+randomnumber2)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Positive button clicked

                                    dialog.dismiss();
                                    binding.firstNo.setText(" First Number");
                                    binding.secondNo.setText(" Second Number");
                                    count=0;
                                    builder.setCancelable(false);


                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(false); // Set to false to prevent dismissal on outside touch
                    dialog.setCancelable(false); // Set to false to prevent dismissal on back press
                    dialog.show();



                }



    }

}