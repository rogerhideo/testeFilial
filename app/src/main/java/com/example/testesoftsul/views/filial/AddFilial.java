package com.example.testesoftsul.views.filial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        try {
            EditText nomeView = findViewById(R.id.nome);
            EditText cidadeView = findViewById(R.id.cidade);
            EditText latitudeView = findViewById(R.id.latitude);
            EditText longitudeView = findViewById(R.id.longitude);
            String nome = nomeView.getText().toString().trim();
            String cidade = cidadeView.getText().toString().trim();
            String latitude = latitudeView.getText().toString().trim();
            String longitude = longitudeView.getText().toString().trim();

            if ( nome.isEmpty() || cidade.isEmpty() || latitude.isEmpty() || longitude.isEmpty()) {
                Toast.makeText(getApplicationContext(),"Prencha Todos os campos",Toast.LENGTH_SHORT).show();
                throw new IOException("Formulário inválido");
            }

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            try {
                Looper.prepare();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("nome", nome);
                jsonObject.put("cidade", cidade);
                jsonObject.put("latitude", latitude);
                jsonObject.put("longitude", longitude);
                jsonObject.put("user_id", AppConfig.getUserId(getApplicationContext()));

                RequestBody body = create(
                        MediaType.get("application/json; charset=utf-8"),
                        jsonObject.toString()
                );

                String endPoint = AppConfig.getServerHost() + "/" + AppConfig.getCreateFilialEnndPoint();
                String accesToken = "Bearer " + AppConfig.getAccessToken(getApplicationContext());

                Request request = new Request.Builder()
                        .url(endPoint)
                        .post(body)
                        .addHeader("Accept-Encoding", "gzip")
                        .addHeader("Authorization", accesToken)
                        .build();

                OkHttpClient client = new OkHttpClient.Builder()
                        .cache(null)
                        .build();

                Call call = client.newCall(request);
                Response response = call.execute();

                final ResponseBody responseBody = response.body();
                if ( responseBody != null ) {
                    responseBody.close();
                }

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                if ( !response.isSuccessful() ) {
                    CharSequence text = "Falha ao criar Filial!";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    throw new IOException("http response is not successful");
                } else {
                    CharSequence text = "Filial criada com sucesso!";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show() ;
                }

                Intent intent = new Intent(this, HomeScreen.class);
                startActivity(intent);
                Looper.loop();
            } catch ( Exception e ) {
                Log.e("testeSoftSul:::", e + " AddFilial->myExecutor.execute(()");
            }
        });
        } catch (Exception e) {
            Log.e("testeSoftSul:::", e + " AddFilial->onCreate()");
        }
    }
}