package com.example.testesoftsul.views.filial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.testesoftsul.R;
import com.example.testesoftsul.config.AppConfig;
import com.example.testesoftsul.models.Filial;

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
            Log.e("testeSoftSul::", e + " EditFilial.Oncreate()");
        }
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

                String endPoint = AppConfig.getServerHost() + "/"
                                    + AppConfig.getUpdateFilialEndPoint()
                                    +  selectedFilial.getId();
                System.out.println("editFilial endpoint -> " + endPoint);


                Request request = new Request.Builder()
                        .url(endPoint)
                        .put(body)
                        .addHeader("Accept-Encoding", "gzip")
                        .build();

                OkHttpClient client = new OkHttpClient.Builder()
                        .cache(null)
                        .build();

                Call call = client.newCall(request);
                Response response = call.execute();


                if ( response.isSuccessful() ){
                    System.out.println("Edit Filial SUCESS -> " );
                    Log.d("testeSoftSul::", "EditFilial.onClickCriar() Salvo com Sucesso!");
//                    Toast.makeText(this, "SALVO COM SUCESSO", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("Edit Filial  FAILUREE-> " );
//                    Toast.makeText(this,"ERRO AO SALVAR",Toast.LENGTH_SHORT).show();
                    throw new IOException("http response is not successful");
                }

                final ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    responseBody.close();
                }

                selectedFilial.setNome(nomeView.getText().toString());
                selectedFilial.setCidade(cidadeView.getText().toString());
                selectedFilial.setLatitude(latitudeView.getText().toString());
                selectedFilial.setLongitude(longitudeView.getText().toString());

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
            } catch (Exception e) {
                Log.e("testeSoftSul:::", e + " EditFilial->onClickCriar()");
            }
        });
    }
}