package com.TriNote.BaseAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.TriNote.Adapters.AdapterAnotacoes;
import com.TriNote.R;
import com.TriNote.Usuario.Dados;

import java.util.List;

public class BaseAdapterGridAnotacoes extends BaseAdapter { //Adiciona conteudo no GridView AdapterAnotacoes
    List<AdapterAnotacoes> nota;
    Context context;

    public BaseAdapterGridAnotacoes(Context context, List<AdapterAnotacoes> nota) {
        this.context = context;
        this.nota = nota;
    }

    @Override
    public int getCount() {
        return nota.size();
    } //pega a quantidade de objetos da lista

    @Override
    public Object getItem(int position) {
        return nota.get(position);
    } //para pegar um item de acordo com a posição

    @Override
    public long getItemId(int position) {
        return -1;
    } //se o retorno não for um long colocar -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);//Para desenhar view usa o LayoutInflater
        AdapterAnotacoes adapterAnotacoes = nota.get(position); //manda as informações para os campos certos do layout
        View view;

        if (convertView == null) {
            view = inflater.inflate(R.layout.bloco_notacao, parent, false); //false para ele não criar antes da hora a view
        } //cria uma nova viu apenas se estiver vazia
        else {
            view = convertView;
        }

        TextView txtTitulo = view.findViewById(R.id.NotaTitulo);
        TextView txtCorpo = view.findViewById(R.id.NotaCorpo);
        TextView txtNota = view.findViewById(R.id.IDNotas);
        TextView txtTema = view.findViewById(R.id.TemaNotas);
        ImageView imgFoto = view.findViewById(R.id.NotaImagem);

        txtNota.setText(adapterAnotacoes.getIdAnotacao());
        txtTitulo.setText(adapterAnotacoes.getTitulo());
        txtCorpo.setText(adapterAnotacoes.getConteudo());
        txtTema.setText(adapterAnotacoes.getFont());

        if (txtTitulo.equals("") || txtCorpo.equals("")) {
            if (txtTitulo.getText().equals("")) {
                txtTitulo.setHeight(0);
                txtTitulo.setWidth(0);
            }
            if (txtCorpo.getText().equals("")) {
                txtCorpo.setHeight(0);
                txtCorpo.setWidth(0);
            }
            if (txtCorpo.getText().equals("") && txtTitulo.getText().equals("")) {
                View viewD = view.findViewById(R.id.divider2);
                viewD.setVisibility(View.INVISIBLE);
            }
        }

        if (!txtTitulo.equals("") && !txtCorpo.equals("") && adapterAnotacoes.getImagem() != null) {
            byte[] imgByte = adapterAnotacoes.getImagem();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            imgFoto.setImageBitmap(bitmap);
        } else if (!txtTitulo.equals("") && !txtCorpo.equals("") && adapterAnotacoes.getImagem() == null) {
            imgFoto.setVisibility(View.INVISIBLE);
        } else if (txtTitulo.equals("") && txtCorpo.equals("") && adapterAnotacoes.getImagem() != null) {
            byte[] imgByte = adapterAnotacoes.getImagem();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            imgFoto.setImageBitmap(bitmap);
        }

        if (txtTema.getText().toString().equals("padrao") || txtTema.getText().toString().equals("")) {
            txtTitulo.setTypeface(null);
            txtCorpo.setTypeface(null);
        } else if (txtTema.getText().toString().equals("Amatic")) {
            Typeface font = ResourcesCompat.getFont(context, R.font.amatic_sc);
            txtTitulo.setTypeface(font);
            txtCorpo.setTypeface(font);
        } else if (txtTema.getText().toString().equals("dancing")) {
            Typeface font = ResourcesCompat.getFont(context, R.font.dancing_script);
            txtTitulo.setTypeface(font);
            txtCorpo.setTypeface(font);
        } else if (txtTema.getText().toString().equals("pinyon")) {
            Typeface font = ResourcesCompat.getFont(context, R.font.pinyon_script);
            txtTitulo.setTypeface(font);
            txtCorpo.setTypeface(font);
        } else if (txtTema.getText().toString().equals("press start")) {
            Typeface font = ResourcesCompat.getFont(context, R.font.press_start_2p);
            txtTitulo.setTypeface(font);
            txtCorpo.setTypeface(font);
        } else if (txtTema.getText().toString().equals("sacramento")) {
            Typeface font = ResourcesCompat.getFont(context, R.font.sacramento);
            txtTitulo.setTypeface(font);
            txtCorpo.setTypeface(font);
            txtTitulo.setTextSize(23);
            txtCorpo.setTextSize(23);
        } else if (txtTema.getText().toString().equals("tangerine")) {
            Typeface font = ResourcesCompat.getFont(context, R.font.tangerine);
            txtTitulo.setTypeface(font);
            txtCorpo.setTypeface(font);
            txtTitulo.setTextSize(23);
            txtCorpo.setTextSize(23);
        }

        return view;
    }
}