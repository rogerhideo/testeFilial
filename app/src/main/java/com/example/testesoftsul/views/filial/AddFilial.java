package com.example.testesoftsul.views.filial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.testesoftsul.MainActivity;
import com.example.testesoftsul.R;
import com.example.testesoftsul.config.AppConfig;
import com.example.testesoftsul.views.HomeScreen;

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

public class AddFilial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_filial);
    }

    public void onClickCriar( View view ) {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            try {
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

                String endPoint = AppConfig.getServerHost() + "/" + AppConfig.getCreateFilialEnndPoint();

                Request request = new Request.Builder()
                        .url(endPoint)
                        .post(body)
                        .addHeader("Accept-Encoding", "gzip")
                        .build();

                OkHttpClient client = new OkHttpClient.Builder()
                        .cache(null)
                        .build();

                Call call = client.newCall(request);
                Response response = call.execute();
                System.out.println("responseee -> " + response.body().string());

                final ResponseBody responseBody = response.body();
                if ( responseBody != null ) {
                    responseBody.close();
                }


                if ( !response.isSuccessful() ) {
                    System.out.println("responseee -> " );
                    throw new IOException("http response is not successful");
                } else {
                    System.out.println("responseee -> " );
                }

                Intent intent = new Intent(this, HomeScreen.class);
                startActivity(intent);
            } catch ( Exception e ) {
                Log.e("testeSoftSul:::", e + " AddFilial->onCreate()");
            }
        });
    }
}