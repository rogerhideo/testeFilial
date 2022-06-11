package com.example.testesoftsul.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

public class CreateUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
    }


    public void onClickCria(View view) throws JSONException, IOException {

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            try {
                System.out.println("onClickCria -> ");

                EditText nomeView = findViewById(R.id.nome);
                EditText emailView = findViewById(R.id.email);
                EditText senhaView = findViewById(R.id.senha);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("nome", nomeView.getText());
                jsonObject.put("email", emailView.getText());
                jsonObject.put("senha", senhaView.getText());

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

                if ( !response.isSuccessful()){
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