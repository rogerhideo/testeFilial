package com.example.testesoftsul.views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.testesoftsul.R;

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
    public static final String EXTRA_MESSAGE = "com.example.testesoftsul.HomeScreen.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void filterActivity(View view) {
        try {
            System.out.println("filterActivity");
            Class switchClass = null;
            System.out.println("viewID -> " + view.getId());
            switch ( view.getId() ) {
                case ( R.id.buttonAdicionar ):
                    redirectToActivity(AddFilial.class);
                    break;
                case ( R.id.buttonListar):
                    redirectToActivity(ListFiliais.class);
                    break;
//                case ( R.id.buttonDetalhes):
//                    redirectToActivity(DetailsFilial.class);
//                    break;
//                case ( R.id.buttonEditar ):
//                    redirectToActivity(EditFilial.class);
//                    break;
            }
        } catch (Exception e) {
            Log.e("testeSoftSul:::" , "HomeScreen->filterActivity()");
        }
    }

    public void redirectToActivity (Class classToGo) {
        try {
            System.out.println("filterActivity");
            Executor myExecutor = Executors.newSingleThreadExecutor();
            myExecutor.execute(() -> {
                try {
                    Request request = new Request.Builder()
                            .url("http://192.168.100.76:3000/mobile/v1/veiculos/1")
                            .get()
                            .addHeader("Accept-Encoding", "gzip")
                            .build();

                    OkHttpClient client = new OkHttpClient.Builder()
                            .cache(null)
                            .build();

//                Call call = client.newCall(request);
//                Response response = call.execute();
//
//                String jsonData = response.body().string();
//
//                final ResponseBody responseBody = response.body();
//                if (responseBody != null) {
//                    responseBody.close();
//                }
//
//
//                JSONObject Jobject = new JSONObject(jsonData);
//                JSONArray Jarray = Jobject.getJSONArray("filiais");
//
//
//                for (int i = 0; i < Jarray.length(); i++) {
//                    JSONObject object = Jarray.getJSONObject(i);
//                    System.out.println(object);
//                }

                    Call call = client.newCall(request);
                    Response response = call.execute();

                    String jsonData = response.body().string();

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

                    // JSONObject Jobject = new JSONObject(jsonData);
                    JSONArray Jarray = new JSONArray(jsonData);
                    //List<ModelState> statess;

                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject object = Jarray.getJSONObject(i);
                        System.out.println(object);
                    }
                    Log.d("testeSoftsul::", jsonData);

                    Intent intent = new Intent(this, classToGo);
                    intent.putExtra(EXTRA_MESSAGE, jsonData);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("testeSoftSul:::", e + " ListFiliais->onCreate()");
                }
            });
        } catch (Exception e) {
            Log.e("testeSoftSul:::" , "HomeScreen->redirectToActivity()");
        }
    }

}