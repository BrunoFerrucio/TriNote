package com.TriNote.Alerts;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import com.TriNote.R;

public class AlertaFontes extends DialogFragment {
    int position = 0;

    public interface  SingleChoiceListener{
        void onPositiveButtonClicked(String [] list, int position);
    }
    SingleChoiceListener mlistener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mlistener = (SingleChoiceListener) context;
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString()+" SingleChoiceListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] fontes = getActivity().getResources().getStringArray(R.array.fontesAlert);
        builder.setTitle("Selecione sua fonte")
                .setSingleChoiceItems(fontes, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        position = i;
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        mlistener.onPositiveButtonClicked(fontes, position);
                    }
                })
                .setNegativeButton("Cancele", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                });
        return builder.create();
    }
}