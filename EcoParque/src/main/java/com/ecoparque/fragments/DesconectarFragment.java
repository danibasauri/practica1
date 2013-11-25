package com.ecoparque.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.ecoparque.R;
import com.ecoparque.activites.LoginActivity;


public class DesconectarFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.confirmacion)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intentDesconectar = new Intent(getActivity(), LoginActivity.class);
                        intentDesconectar.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intentDesconectar.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intentDesconectar.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentDesconectar);
                        getActivity().finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        return builder.create();
    }
}