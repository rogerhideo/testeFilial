package com.example.testesoftsul.views.filial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testesoftsul.MainActivity;
import com.example.testesoftsul.R;
import com.example.testesoftsul.config.AppConfig;
import com.example.testesoftsul.models.Filial;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DetailsFilial extends AppCompatActivity {
    private static Filial selectedFilial;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details_filial);
        getSelectedFilial();
        setValues();
    }

    private void getSelectedFilial( ) {
        try {
            Intent previousIntent = getIntent();
            String parsedStringID = previousIntent.getStringExtra("id");

            if ( parsedStringID != null ) {
                ArrayList<Filial> auxArray = ListFiliais.getFiliaisList();
                for( Filial filial : auxArray ) {
                    if ( filial.getId() == Integer.valueOf(parsedStringID) ) {
                        selectedFilial = filial;
                    }
                }
            }
        } catch (Exception e ) {
            Log.e("testeSoftSutl::", e + " DetailsFilial.getSelectedShape()");
        }
    }

    private void setValues( ) {
        try {
            TextView nome = (TextView) findViewById(R.id.nome);
            TextView cidade = (TextView) findViewById(R.id.cidade);
            TextView latitude = (TextView) findViewById(R.id.latitude);
            TextView longitude = (TextView) findViewById(R.id.longitude);

            nome.setText(selectedFilial.getNome());
            cidade.setText(selectedFilial.getCidade());
            latitude.setText(selectedFilial.getLatitude());
            longitude.setText(selectedFilial.getLongitude());
        } catch ( Exception e ) {
            Log.e("testeSoftSutl::", e + " DetailsFilial.setValues()");
        }
    }

    public void edit( View view ) {
        try {
            Intent editFilial = new Intent(this, EditFilial.class);
            editFilial.putExtra("id", String.valueOf(selectedFilial.getId()));
            startActivity(editFilial);
        } catch ( Exception e ) {
            Log.e("testeSoftSutl::", e + " DetailsFilial.edit()");
        }
    }

    public void open( View view ) {
        try {
            String coords = selectedFilial.getLatitude() + "," + selectedFilial.getLongitude();
            Uri gmmIntentUri = Uri.parse("geo:" + coords +"?q= " + coords);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if ( mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        } catch ( Exception e ) {
            Log.e("testeSoftSutl::", e + " DetailsFilial.open()");
        }
    }

    public void delete( View view ) {
        try {
            Executor myExecutor = Executors.newSingleThreadExecutor();
            myExecutor.execute(() -> {
                try {
                    Looper.prepare();
                    String endPoint = AppConfig.getServerHost() + "/" + AppConfig.getDeleteFilialEndPoint() + selectedFilial.getId();

                    Request request = new Request.Builder()
                            .url(endPoint)
                            .delete()
                            .addHeader("Accept-Encoding", "gzip")
                            .build();

                    OkHttpClient client = new OkHttpClient.Builder()
                            .cache(null)
                            .build();

                    Call call = client.newCall(request);
                    Response response = call.execute();

                    final ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        responseBody.close();
                    }

                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    if ( !response.isSuccessful()){
                        CharSequence text = "Falha ao deletar Filial!";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        throw new IOException("http response is not successful");
                    } else {
                        CharSequence text = "Filial Deletada com Sucesso!";
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }

                    ArrayList<Filial> filiaisList = ListFiliais.getFiliaisList();
                    ArrayList<Filial> auxArray = new ArrayList<Filial>();
                    for( int i = 0; i < filiaisList.size(); i++ ) {
                        if ( selectedFilial.getId() != filiaisList.get(i).getId() ) {
                            auxArray.add(filiaisList.get(i));
                        }
                    }
                    ListFiliais.setFiliaisList(auxArray);

                    Intent intent = new Intent(this, ListFiliais.class);
                    startActivity(intent);
                    Looper.loop();
                } catch (Exception e) {
                    Log.e("testeSoftSul:::", e + " DetailsFilial.myExecutor.execute(()");
                }
            });
        } catch ( Exception e ) {
            Log.e("testeSoftSutl::", e + " DetailsFilial.delete()");
        }
    }
}