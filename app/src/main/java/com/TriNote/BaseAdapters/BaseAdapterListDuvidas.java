package com.TriNote.BaseAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.TriNote.Adapters.AdapterDuvidas;
import com.TriNote.R;

import java.util.List;

public class BaseAdapterListDuvidas extends BaseAdapter {
    List<AdapterDuvidas> duvidasList;
    Context context;

    public BaseAdapterListDuvidas(Context context, List<AdapterDuvidas> duvidasList){
        this.context = context;
        this.duvidasList = duvidasList;
    }

    @Override
    public int getCount() {
        return duvidasList.size();
    }

    @Override
    public Object getItem(int position) {
        return duvidasList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;

        if (convertView == null) {
            view = inflater.inflate(R.layout.linha_duvida, parent, false);
        } else {
            view = convertView;
        }

        TextView txtTitulo = view.findViewById(R.id.TxtDuvidaTitulo);
        TextView txtConteudo = view.findViewById(R.id.TxtDuvidaConteudo);

        AdapterDuvidas adapter = new AdapterDuvidas();
        txtTitulo.setText(adapter.getDuvidaTitulo());
        txtConteudo.setText(adapter.getDuvidaConteudo());

        return view;
    }
}
