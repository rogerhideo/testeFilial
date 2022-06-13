package com.example.testesoftsul.views.filial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.testesoftsul.R;
import com.example.testesoftsul.models.Filial;

import java.util.ArrayList;

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
            System.out.println("[delete]");
        } catch ( Exception e ) {
            Log.e("testeSoftSutl::", e + " DetailsFilial.delete()");
        }
    }
}