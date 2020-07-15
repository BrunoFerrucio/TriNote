package com.TriNote.BaseAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.TriNote.Adapters.AdaptersUsuario;
import com.TriNote.R;
import com.TriNote.Usuario.Dados;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

public class BaseAdapterListAmigos extends BaseAdapter {
    List<AdaptersUsuario> usuarios;
    Context context;

    public BaseAdapterListAmigos(Context context, List<AdaptersUsuario> usuarios) {
        this.usuarios = usuarios;
        this.context = context;
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return usuarios.get(position);
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
            view = inflater.inflate(R.layout.linha_amigos, parent, false);
        } else {
            view = convertView;
        }

        ImageView imgPerfil = view.findViewById(R.id.linhaImg);
        TextView txtNome = view.findViewById(R.id.linhaNome);

        AdaptersUsuario adaptersUsuario = usuarios.get(position);
        txtNome.setText(adaptersUsuario.getLoginUsuario());

        if (adaptersUsuario.getFoto() != null) {
            byte[] imgFile = adaptersUsuario.getFoto();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgFile, 0, imgFile.length);
            imgPerfil.setImageBitmap(bitmap);
        }

        return view;
    }
}