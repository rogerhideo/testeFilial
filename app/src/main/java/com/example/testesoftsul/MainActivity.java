package com.example.testesoftsul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
//    public static final String EXTRA_MESSAGE = "com.example.testesoftsul.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view) {
        System.out.println("clicouu send -> ");
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
    }

    public void createUser(View view) {
        System.out.println("clicouu em criar -> ");
        Intent intent = new Intent(this, CreateUser.class);
        startActivity(intent);
    }
}