package com.example.testesoftsul.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.testesoftsul.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static okhttp3.RequestBody.create;

public class EditFilial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_filial);

        try {
            Intent intent = getIntent();
            String jsonData = intent.getStringExtra(DetailsFilial.EXTRA_MESSAGE);

            JSONObject jObject = new JSONObject(jsonData);

            String nome = jObject.getString("nome");
            String cidade = jObject.getString("cidade");
            String latitude = jObject.getString("latitude");
            String longitude = jObject.getString("longitude");

            EditText nomeView = findViewById(R.id.nome);
            nomeView.setText(nome);

            EditText cidadeView = findViewById(R.id.cidade);
            cidadeView.setText(cidade);

            EditText latitudeView = findViewById(R.id.latitude);
            latitudeView.setText(latitude);

            EditText longitudeView = findViewById(R.id.longitude);
            longitudeView.setText(longitude);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onClickCria(View view) throws JSONException, IOException {

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            try {
                System.out.println("onClickCria -> ");

                EditText nomeView = findViewById(R.id.nome);
                EditText cidadeView = findViewById(R.id.cidade);
                EditText latitudeView = findViewById(R.id.latitude);
                EditText longitudeView = findViewById(R.id.longitude);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("nome", nomeView.getText());
                jsonObject.put("cidade", cidadeView.getText());
                jsonObject.put("latitude", latitudeView.getText());
                jsonObject.put("longitude", longitudeView.getText());

                RequestBody body = create(
                        MediaType.get("application/json; charset=utf-8"),
                        jsonObject.toString()
                );
                Request request = new Request.Builder()
                        .url("http://192.168.100.76:3000/mobile/v2/abastecimentos/1")
                        .post(body)
                        .addHeader("Accept-Encoding", "gzip")
                        .build();

                OkHttpClient client = new OkHttpClient.Builder()
                        .cache(null)
                        .build();

                Call call = client.newCall(request);
                Response response = call.execute();

                System.out.println("response-> " + response.body().string());

                final ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    responseBody.close();
                }

                if ( !response.isSuccessful() ){
                    System.out.println("FAILL-> ");
                    throw new IOException("http response is not successful");
                } else {
                    System.out.println("SUCESS-> ");
                }

            } catch (Exception e) {
                Log.e("testeSoftSul:::", e + " ListFiliais->onCreate()");
            }
        });
    }
}