package com.example.testesoftsul.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testesoftsul.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static okhttp3.RequestBody.create;

public class ListFiliais extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.testesoftsul.ListFiliais.MESSAGE";
    public static String jsonData;
    ListView simpleList;
    String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};
    //    List<String> nomeFiliais;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_filiais);

        try {
            // Get the Intent that started this activity and extract the string
            Intent intent = getIntent();
            String message = intent.getStringExtra(HomeScreen.EXTRA_MESSAGE);

            if ( message != null && !message.equals(jsonData) )  {
                jsonData = message;
            }

            JSONArray Jarray = new JSONArray(jsonData);
            List<String> nomeFiliais = new ArrayList<>();
            for (int i = 0; i < Jarray.length(); i++) {
                JSONObject object = Jarray.getJSONObject(i);
                String nome = Jarray.getJSONObject(i).getString("nome");
                nomeFiliais.add(nome);
            }

            simpleList = (ListView)findViewById(R.id.simpleListView);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_list_filiais, R.id.textView, nomeFiliais);
            simpleList.setAdapter(arrayAdapter);
            simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {

                        String item = (String) parent.getItemAtPosition(position);

                        String object = new String();
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject auxObject = Jarray.getJSONObject(i);
                            String nome = Jarray.getJSONObject(i).getString("nome");

                            if (item.equals(nome)) {
                                object = auxObject.toString();
                            }
                        }

                        callActivity(object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callActivity (String message) {
        try {
            Intent intent = new Intent(this, DetailsFilial.class);
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        } catch (Exception e) {
            Log.e("testeSoftSul:::" , "HomeScreen->filterActivity()");
        }
    }
}