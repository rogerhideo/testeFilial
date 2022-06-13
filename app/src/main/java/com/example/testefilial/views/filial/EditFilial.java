package com.example.testefilial.views.filial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testefilial.R;
import com.example.testefilial.config.AppConfig;
import com.example.testefilial.models.Filial;

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

public class EditFilial extends AppCompatActivity {

    private static Filial selectedFilial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_filial);

        try {
            Intent previousIntent = getIntent();
            String parsedStringID = previousIntent.getStringExtra("id");
            ArrayList<Filial> auxArray = ListFiliais.getFiliaisList();
            for( Filial filial : auxArray ) {
                if ( filial.getId() == Integer.valueOf(parsedStringID) ) {
                    selectedFilial = filial;
                }
            }

            EditText nomeView = findViewById(R.id.nome);
            nomeView.setText(selectedFilial.getNome());

            EditText cidadeView = findViewById(R.id.cidade);
            cidadeView.setText(selectedFilial.getCidade());

            EditText latitudeView = findViewById(R.id.latitude);
            latitudeView.setText(selectedFilial.getLatitude());

            EditText longitudeView = findViewById(R.id.longitude);
            longitudeView.setText(selectedFilial.getLongitude());

        } catch ( Exception e ) {
            Log.e("testeFilial::", e + " EditFilial.Oncreate()");
        }
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

                RequestBody body = create(
                        MediaType.get("application/json; charset=utf-8"),
                        jsonObject.toString()
                );

                String endPoint = AppConfig.getServerHost() + "/"
                                    + AppConfig.getUpdateFilialEndPoint()
                                    +  selectedFilial.getId();
                String accesToken = "Bearer " + AppConfig.getAccessToken(getApplicationContext());

                Request request = new Request.Builder()
                        .url(endPoint)
                        .put(body)
                        .addHeader("Accept-Encoding", "gzip")
                        .addHeader("Authorization", accesToken)
                        .build();

                OkHttpClient client = new OkHttpClient.Builder()
                        .cache(null)
                        .build();

                Call call = client.newCall(request);
                Response response = call.execute();

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                if ( response.isSuccessful() ){
                    CharSequence text = "Filial Editada com Sucesso!";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    CharSequence text = "Falha salvar edição!";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    throw new IOException("http response is not successful");
                }

                final ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    responseBody.close();
                }

                selectedFilial.setNome(nome);
                selectedFilial.setCidade(cidade);
                selectedFilial.setLatitude(latitude);
                selectedFilial.setLongitude(longitude);

                ArrayList<Filial> filiaisList = ListFiliais.getFiliaisList();
                ArrayList<Filial> auxArray = new ArrayList<Filial>();
                for( int i = 0; i < filiaisList.size(); i++ ) {
                    if ( selectedFilial.getId() == filiaisList.get(i).getId() ) {
                        auxArray.add(selectedFilial);
                    } else {
                        auxArray.add(filiaisList.get(i));
                    }
                }
                ListFiliais.setFiliaisList(auxArray);

                Intent showDetail = new Intent(getApplicationContext(), DetailsFilial.class);
                showDetail.putExtra("id", String.valueOf(selectedFilial.getId()));
                startActivity(showDetail);
                Looper.loop();
            } catch (Exception e) {
                Log.e("testeFilial:::", e + " EditFilial.myExecutor.execute(()");
            }
        });
        } catch (Exception e ){
            Log.e("testeFilial:::", e + " EditFilial.onClickCriar()");
        }
    }
}