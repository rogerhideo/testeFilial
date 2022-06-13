package com.example.testesoftsul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.testesoftsul.views.user.CreateUser;
import com.example.testesoftsul.views.HomeScreen;

public class MainActivity extends AppCompatActivity {
    public static final String DATA = "com.example.testesoftsul.DATA";

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage( View view ) {
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
    }

    public void createUser( View view ) {
        Intent intent = new Intent(this, CreateUser.class);
        startActivity(intent);
    }

    public void runNewThread( Runnable thread ) {
        try {
            thread.run();
        } catch ( Exception e ) {
            Log.e("testeSoftSul::", "MainActivity.runNewThread()");
        }
    };
}