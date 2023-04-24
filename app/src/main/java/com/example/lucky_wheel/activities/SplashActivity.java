package com.example.lucky_wheel.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import com.example.lucky_wheel.GetDate;
import com.example.lucky_wheel.Location;
import com.example.lucky_wheel.R;
import com.example.lucky_wheel.RetrofitClient;
import com.example.lucky_wheel.databinding.ActivitySplashBinding;
import com.example.lucky_wheel.session.SessionManager;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;

    private SessionManager sessionManager;

    String word = "kanchanaburi";
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

/*if  ip address is empty then the if_block is executed at first  and stored the ip address ,country name , country code
else_block is ignored at first because there is no ip address and country code
after that  when there is ip address the if_block conditions is false and the if_block is ignored
 else_block is executed
 because we have ip address , country name , country code stored in shared preference for next use

 */


        //get ip address from api at first when activity is lunch
        // the is is executed at first when the is lunch for first time
        if (sessionManager.fetchIpAddress() == null) {

         //   Toast.makeText(this, "No IP Address found and searching for new ip address", Toast.LENGTH_SHORT).show(); // Added show() method

            getIpAddress();

            callApi();
            gotoNextActivity();
            return;
        } else {
            // fetch the data from shared preference
//            ip = sessionManager.fetchIpAddress();
//            Toast.makeText(this, "IP address is already "+ip, Toast.LENGTH_SHORT).show();
            gotoNextActivity();
           // Toast.makeText(this, ""+sessionManager.fetchCountryCode()+"   "+sessionManager.fetchCountryName(), Toast.LENGTH_SHORT).show();
        }

//        ip = sessionManager.fetchIpAddress();
//           Toast.makeText(this, "IP address is already "+ip, Toast.LENGTH_SHORT).show();

    }

    public boolean getIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip_address = Formatter.formatIpAddress(inetAddress.hashCode());
                        sessionManager.saveIpAddress(ip_address);

                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("TAG", ex.toString());
        }
        return true;
    }

    public void callApi() {
        GetDate getDate = RetrofitClient.getRetrofitObject().create(GetDate.class);


        ip = sessionManager.fetchIpAddress();
        Call<Location> call = getDate.getLocation(ip);

        call.enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                Location location = response.body();
                assert location != null;

                try {
                    sessionManager.saveCountryName(location.getCountry());
                    sessionManager.saveCountryCode(location.getCountryCode());


                } catch (Exception e) {
                    Toast.makeText(SplashActivity.this, "Exception" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Exception", e.getLocalizedMessage());
                }
//
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                Toast.makeText(SplashActivity.this, "throwable" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void gotoNextActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (sessionManager.fetchCountryCode().equalsIgnoreCase("IN") && sessionManager.fetchCountryName().equalsIgnoreCase("India")) {
//                        Log.d("TAG", "India");
                        Intent i = new Intent(SplashActivity.this, IndianActivity.class);

                        //         Toast.makeText(SplashActivity.this, "" + sessionManager.fetchCountryName(), Toast.LENGTH_SHORT).show();

                        startActivity(i);

                        finish();

                    } else {
                        Intent i = new Intent(SplashActivity.this, WebviewActivity.class);

                        startActivity(i);


                        finish();
                    }
                } catch (Exception exception) {
                    Toast.makeText(SplashActivity.this, "Exception" + exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        }, 7000);
    }

    private void getCharactor() {

        Random random = new Random();
        int index = random.nextInt(word.length());
        char randomChar = word.charAt(index);
        Toast.makeText(this, "" + randomChar, Toast.LENGTH_SHORT).show();


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();


    }
}