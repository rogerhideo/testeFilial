package com.example.testesoftsul.views.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testesoftsul.MainActivity;
import com.example.testesoftsul.R;
import com.example.testesoftsul.config.AppConfig;

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
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
    }


    public void onClickCriar( View view ) {
        try {
            EditText nomeView = findViewById(R.id.nome);
            EditText emailView = findViewById(R.id.email);
            EditText senhaView = findViewById(R.id.senha);
            String nome = nomeView.getText().toString().trim();
            String senha = nomeView.getText().toString().trim();

            EditText emailValidate = (EditText)findViewById(R.id.email);
            String email = emailValidate.getText().toString().trim();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if ( email.matches(emailPattern) && email.length() > 0) {
                Log.d("testeFilial::", "Email válido");
            }
            else {
                Toast.makeText(getApplicationContext(),"Email inválido",Toast.LENGTH_SHORT).show();
                throw new IOException("Email inválido");
            }

            if ( nome.isEmpty() || senha.isEmpty()) {
                Toast.makeText(getApplicationContext(),"Prencha Todos os campos",Toast.LENGTH_SHORT).show();
                throw new IOException("Formulário inválido");
            }

            Executor myExecutor = Executors.newSingleThreadExecutor();
            myExecutor.execute(() -> {
                try {
                    Looper.prepare();


                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", nomeView.getText());
                    jsonObject.put("email", emailView.getText());
                    jsonObject.put("password", senhaView.getText());

                    RequestBody body = create(
                            MediaType.get("application/json; charset=utf-8"),
                            jsonObject.toString()
                    );

                    String endPoint = AppConfig.getServerHost() + "/" + AppConfig.getCreateUserEndPoint();

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
                    System.out.println(" createUSer responseee -> " + response.body().string());

                    final ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        responseBody.close();
                    }

                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;

                    if (!response.isSuccessful()) {
                        CharSequence text = "Falha ao criar usuário!";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        throw new IOException("http response is not successful");
                    } else {
                        CharSequence text = "Usuário Criado com Sucesso!";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    Looper.loop();
                } catch (Exception e) {
                    Log.e("testeSoftSul:::", e + " CreateUser->myExecutor.execute(()");
                }
            });
        } catch (Exception e) {
            Log.e("testeSoftSul:::", e + " CreateUser->onClickCriar()");
        }
    }
}