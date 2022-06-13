package com.example.testesoftsul.views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.testesoftsul.MainActivity;
import com.example.testesoftsul.R;
import com.example.testesoftsul.config.AppConfig;
import com.example.testesoftsul.views.filial.AddFilial;
import com.example.testesoftsul.views.filial.ListFiliais;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void filterActivity( View view ) {
        try {
            switch ( view.getId() ) {
                case ( R.id.buttonAdicionar ):
                    redirectToActivity(AddFilial.class);
                    break;
                case ( R.id.buttonListar):
                    redirectToActivity(ListFiliais.class);
                    break;
            }
        } catch ( Exception e ) {
            Log.e("testeSoftSul:::" , "HomeScreen->filterActivity()");
        }
    }

    public void redirectToActivity ( Class classToGo ) {
        try {
            Executor myExecutor = Executors.newSingleThreadExecutor();
            myExecutor.execute(() -> {
                try {
                    String endPoint = AppConfig.getServerHost() + "/" + AppConfig.getGetFiliaisEndPoint() + "1";

                    Request request = new Request.Builder()
                            .url(endPoint)
                            .get()
                            .addHeader("Accept-Encoding", "gzip")
                            .build();

                    OkHttpClient client = new OkHttpClient.Builder()
                            .cache(null)
                            .build();

                    Call call = client.newCall(request);
                    Response response = call.execute();

                    String jsonData = response.body().string();
                    System.out.println(jsonData);
                    final ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        responseBody.close();
                    }

                    if ( !response.isSuccessful()){
                        throw new IOException("http response is not successful");
                    }

                    Intent intent = new Intent(this, classToGo);
                    intent.putExtra(MainActivity.DATA, jsonData);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("testeSoftSul:::", e + " HomeScreen->myExecutor.execute(()");
                }
            });
        } catch (Exception e) {
            Log.e("testeSoftSul:::" , "HomeScreen->redirectToActivity()");
        }
    }

}