package com.example.testesoftsul;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void redirectToActivity (View view) {
        System.out.println("redirectToActivity");
        Class newClass = null;
        System.out.println("viewID -> " + view.getId());
        switch (view.getId()) {
            case (R.id.buttonAdicionar):
                newClass = AddFilial.class;
                break;
            case (R.id.buttonListar):
                newClass = ListFiliais.class;
                break;
            case (R.id.buttonDetalhes):
                newClass = DetailsFilial.class;
                break;
            case (R.id.buttonEditar):
                newClass = EditFilial.class;
                break;
        }
        Intent intent = new Intent(this, newClass);
        startActivity(intent);
    }

    public void onCLickAdicionar(View view) {
        System.out.println("clicouu adicionar -> ");
        Intent intent = new Intent(this, AddFilial.class);
        startActivity(intent);
    }

    public void onCLickListar(View view) {
        System.out.println("clicouu Listar -> ");
        Intent intent = new Intent(this, ListFiliais.class);
        startActivity(intent);
    }

    public void onCLickDetalhes(View view) {
        System.out.println("clicouu Detalhes -> ");
        Intent intent = new Intent(this, DetailsFilial.class);
        startActivity(intent);
    }

    public void onCLickEditar(View view) {
        System.out.println("clicouu Editar -> ");
        Intent intent = new Intent(this, EditFilial.class);
        startActivity(intent);
    }
}