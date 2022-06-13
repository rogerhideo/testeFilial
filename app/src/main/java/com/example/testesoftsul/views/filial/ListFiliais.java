package com.example.testesoftsul.views.filial;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.testesoftsul.MainActivity;
import com.example.testesoftsul.R;
import com.example.testesoftsul.components.FilialAdapter;
import com.example.testesoftsul.config.AppConfig;
import com.example.testesoftsul.models.Filial;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ListFiliais extends AppCompatActivity {

    private static ArrayList<Filial> filiaisList = new ArrayList<Filial>();
    private ListView listView;
    private String currentSearchText = "";
    private String jsonData;
    private SearchView searchView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_filiais);
        initSearchWidgets();
        setupData();
        setUpList();
        setUpOnclickListener();
    }

    private void initSearchWidgets( ) {
        try {
            searchView = (SearchView) findViewById(R.id.filialListSearchView);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    Log.d("app::", "searchfield submit");
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    currentSearchText = s;
                    ArrayList<Filial> filteredFiliais = new ArrayList<Filial>();
                    for( Filial filial: filiaisList ) {
                        if(filial.getNome().toLowerCase().contains(s.toLowerCase())) {
                            filteredFiliais.add(filial);
                        }
                    }
                    FilialAdapter adapter = new FilialAdapter(getApplicationContext(), 0, filteredFiliais);
                    listView.setAdapter(adapter);
                    return false;
                }
            });

        } catch (Exception e) {
            Log.e("testeSoftSul:::" , e + "ListFiliais->initSearchWidgets()");
        }
    }

    private void setupData() {
        try {
            Intent intent = getIntent();
            String message = intent.getStringExtra(MainActivity.DATA);
            System.out.println("setup data " + message);
            if ( message != null && !message.equals(jsonData) )  {
                jsonData = message;
                JSONObject Jobject = new JSONObject(message);
                JSONArray Jarray = Jobject.getJSONArray("data");
                filiaisList = new ArrayList<Filial>();
                for (int i = 0; i < Jarray.length(); i++) {
                    JSONObject object = Jarray.getJSONObject(i);
                    Filial newFilial = new Filial(object.getInt("id"),
                            object.getString("nome"),
                            object.getString("cidade"),
                            object.getString("latitude"),
                            object.getString("longitude")
                    );
                    filiaisList.add(newFilial);
                }
            } else {
                updateData();
            }
            setUpList();
        } catch (Exception e) {
            Log.e("testeSoftSul:::" , e + "ListFiliais->setupData()");
        }
    }

    private void setUpList( ) {
        try {
            listView = (ListView) findViewById(R.id.filialListView);
            FilialAdapter adapter = new FilialAdapter(getApplicationContext(), 0, filiaisList);
            listView.setAdapter(adapter);
        } catch ( Exception e ) {
            Log.e("testeSoftSul:::" , e + " ListFiliais->setUpList()");
        }
    }

    private void setUpOnclickListener( ) {
        try {
            listView.setOnItemClickListener( new AdapterView.OnItemClickListener( ) {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    try {
                        Filial selectFilial = (Filial) (listView.getItemAtPosition(position));
                        Intent showDetail = new Intent(getApplicationContext(), DetailsFilial.class);
                        showDetail.putExtra("id", String.valueOf(selectFilial.getId()));
                        startActivity(showDetail);
                    } catch ( Exception e ) {
                        Log.e("testeSoftSul:::" , e + "ListFiliais->onItemClick()");
                    }
                }
            });
        } catch ( Exception e ) {
            Log.e("testeSoftSul:::" , e + "ListFiliais->setUpOnclickListener()");
        }
    }

    private void updateData( ) {
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
                    System.out.println(" list Filiais responseee -> " + response.body().string());



                    if ( response.isSuccessful() ){
                        System.out.println("list Filial SUCESS -> " );
                        String responseString = response.body().string();
                        JSONArray Jarray = new JSONArray(responseString);
                        filiaisList = new ArrayList<Filial>();

                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject object = Jarray.getJSONObject(i);
                            Filial newFilial = new Filial(object.getInt("id"),
                                    object.getString("nome"),
                                    object.getString("cidade"),
                                    object.getString("latitude"),
                                    object.getString("longitude")
                            );
                            filiaisList.add(newFilial);
                        }
                    } else {
                        System.out.println("list Filial  FAILUREE-> " );
                        throw new IOException("http response is not successful");
                    }

                    final ResponseBody responseBody = response.body();
                    if ( responseBody != null ) {
                        responseBody.close();
                    }

                } catch (Exception e) {
                    Log.e("testeSoftSul:::", e + " ListFiliais.myExecutor.execute()");
                }
            });
        } catch (Exception e) {
            Log.e("testeSoftSul:::" , e + " ListFiliais.updateData()");
        }
    }

    public static ArrayList<Filial> getFiliaisList() {
        return filiaisList;
    }
    public static void setFiliaisList(  ArrayList<Filial>  newList ) {
        filiaisList = newList;
    }
}