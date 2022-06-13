package com.example.testefilial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testefilial.config.AppConfig;
import com.example.testefilial.views.filial.ListFiliais;
import com.example.testefilial.views.user.CreateUser;

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

public class MainActivity extends AppCompatActivity {
    public static final String DATA = "com.example.testefilial.DATA";

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login( View view ) {
        try {
            EditText emailView = findViewById(R.id.email);
            EditText passwordView = findViewById(R.id.password);
            String email = emailView.getText().toString().trim();
            String password = passwordView.getText().toString().trim();

            if ( email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(),"Prencha Todos os campos",Toast.LENGTH_SHORT).show();
                throw new IOException("Formulário inválido");
            }

            Executor myExecutor = Executors.newSingleThreadExecutor();
            myExecutor.execute(() -> {
                try {
                    Looper.prepare();

                    // ##### requisição de login //
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("email", emailView.getText().toString());
                    jsonObject.put("password", passwordView.getText().toString());

                    RequestBody body = create(
                            MediaType.get("application/json; charset=utf-8"),
                            jsonObject.toString()
                    );

                    String endPoint = AppConfig.getServerHost() + "/" + AppConfig.getLoginEndpoint();
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

                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    if ( !response.isSuccessful()){
                        CharSequence text = "Falha ao logar!";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        throw new IOException("http login response is not successful");
                    } else {
                        String jsonData = response.body().string();
                        JSONObject jsonResponse = new JSONObject(jsonData);
                        JSONObject object = jsonResponse.getJSONObject("data");

                        AppConfig.setAccessToken(object.getString("token"), getApplicationContext());
                        AppConfig.setUserId(String.valueOf(object.getInt("userId")), getApplicationContext());

                        CharSequence text = "Login com sucesso!";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        // ##### requisição para pegar lista de filiais //
                        endPoint = AppConfig.getServerHost() + "/" + AppConfig.getGetFiliaisEndPoint() + "1";

                        request = new Request.Builder()
                                .url(endPoint)
                                .get()
                                .addHeader("Accept-Encoding", "gzip")
                                .build();

                        call = client.newCall(request);
                        Response response2 = call.execute();

                        jsonData = response2.body().string();
                        final ResponseBody responseBody2 = response2.body();
                        if (responseBody2 != null) {
                            responseBody2.close();
                        }

                        if ( !response.isSuccessful()){
                            text = "Falha ao carregar Filiais!";
                            toast = Toast.makeText(context, text, duration);
                            toast.show();
                            throw new IOException("http getFiliais response is not successful");
                        }

                        Intent intent = new Intent(this, ListFiliais.class);
                        intent.putExtra(MainActivity.DATA, jsonData);
                        startActivity(intent);
                    }

                    final ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        responseBody.close();
                    }

                    Looper.loop();
                } catch (Exception e) {
                    Log.e("testeFilial:::", e + " MainActivity.myExecutor.execute(()");
                }
            });
        } catch (Exception e) {
            Log.e("testeFilial:::" , "MainActivity.redirectToActivity()");
        }
    }

    public void createUser( View view ) {
        Intent intent = new Intent(this, CreateUser.class);
        startActivity(intent);
    }
}