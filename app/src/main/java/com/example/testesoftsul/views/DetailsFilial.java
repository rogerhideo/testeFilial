package com.example.testesoftsul.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.testesoftsul.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailsFilial extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.testesoftsul.DetailsFilial.MESSAGE";
    public static String jsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_filial);

        try {
            Intent intent = getIntent();
            String message = intent.getStringExtra(ListFiliais.EXTRA_MESSAGE);

            if ( message != null && !message.equals(jsonData) )  {
                jsonData = message;
            }

            JSONObject jObject = new JSONObject(jsonData);

            String nome = jObject.getString("nome");
            String cidade = jObject.getString("cidade");
            String latitude = jObject.getString("latitude");
            String longitude = jObject.getString("longitude");

            TextView nomeView = findViewById(R.id.nome);
            nomeView.setText(nome);

            TextView cidadeView = findViewById(R.id.cidade);
            cidadeView.setText(cidade);

            TextView latitudeView = findViewById(R.id.latitude);
            latitudeView.setText(latitude);

            TextView longitudeView = findViewById(R.id.longitude);
            longitudeView.setText(longitude);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void edit(View view) {
        System.out.println("[edit]");
        Intent intent = new Intent(this, EditFilial.class);
        intent.putExtra(EXTRA_MESSAGE, jsonData);
        startActivity(intent);
    }

    public void open(View view) {
        System.out.println("[open]");
        Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194?q=37.7749,-122.4194");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if ( mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    public void delete(View view) {
        System.out.println("[delete]");
    }
}