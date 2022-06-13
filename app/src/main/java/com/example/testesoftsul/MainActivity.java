package com.example.testesoftsul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testesoftsul.config.AppConfig;
import com.example.testesoftsul.models.Filial;
import com.example.testesoftsul.views.user.CreateUser;
import com.example.testesoftsul.views.HomeScreen;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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

public class MainActivity extends AppCompatActivity {
    public static final String DATA = "com.example.testesoftsul.DATA";

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login( View view ) {
        try {
            Executor myExecutor = Executors.newSingleThreadExecutor();
            myExecutor.execute(() -> {
                try {
                    Looper.prepare();
                    EditText emailView = findViewById(R.id.email);
                    EditText passwordView = findViewById(R.id.password);

                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("email", emailView.getText());
//                    jsonObject.put("password", passwordView.getText());

                    jsonObject.put("email", "roger_hideo@hotmail.com");
                    jsonObject.put("password", "1234556");

                    RequestBody body = create(
                            MediaType.get("application/json; charset=utf-8"),
                            jsonObject.toString()
                    );

                    String endPoint = AppConfig.getServerHost() + "/" + AppConfig.getLoginEndpoint();
                    System.out.println("mainAcitivy -> endpoind -> " + endPoint);
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

                    String jsonData = response.body().string();
                    System.out.println(jsonData);


                    JSONObject jsonResponse = new JSONObject(jsonData);
                    JSONObject object = jsonResponse.getJSONObject("data");

                    AppConfig.setAccessToken(object.getString("token"), getApplicationContext());
                    AppConfig.setUserId(String.valueOf(object.getInt("userId")), getApplicationContext());

                    final ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        responseBody.close();
                    }


                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    if ( !response.isSuccessful()){
                        CharSequence text = "Falha ao logar!";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        System.out.println("mainAcitivy  FAILUREE-> " );
                        throw new IOException("http response is not successful");
                    } else {
                        CharSequence text = "Login com sucesso!";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        System.out.println("mainAcitivy SUCESS -> " );
                    }

                    Intent intent = new Intent(this, HomeScreen.class);
                    startActivity(intent);
                    Looper.loop();
                } catch (Exception e) {
                    Log.e("testeSoftSul:::", e + " MainActivity.myExecutor.execute(()");
                }
            });
        } catch (Exception e) {
            Log.e("testeSoftSul:::" , "MainActivity.redirectToActivity()");
        }
    }

    public void sendMessage( View view ) {
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);
    }

    public void createUser( View view ) {
        Intent intent = new Intent(this, CreateUser.class);
        startActivity(intent);
    }
}