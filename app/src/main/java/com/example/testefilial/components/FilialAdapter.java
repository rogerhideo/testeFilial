package com.example.testefilial.components;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.testefilial.R;
import com.example.testefilial.models.Filial;

import java.util.List;

public class FilialAdapter extends ArrayAdapter<Filial>  {

    public FilialAdapter( Context context, int resource, List<Filial> filialList ) {
        super(context,resource,filialList);
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {
        try {
            Filial filial = getItem(position);

            if ( convertView == null ) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.filial_cell, parent, false);
            }

            TextView nome = (TextView) convertView.findViewById(R.id.nome);
            TextView cidade = (TextView) convertView.findViewById(R.id.cidade);

            nome.setText(filial.getNome());
            cidade.setText(filial.getCidade());
        } catch (Exception e) {
            Log.e("testeFilial::", e + " FilialAdapter.getView()");
        }
        return convertView;
    }
}
