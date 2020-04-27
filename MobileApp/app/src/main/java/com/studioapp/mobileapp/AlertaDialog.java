package com.studioapp.mobileapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AlertaDialog extends AppCompatDialogFragment {
    private AlertaDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AlertDialogTheme);
        builder.setTitle("Atentie!")
                .setMessage("Esti sigur ca vrei sa salvezi noua parola?")
                .setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onYesClicked();
                    }
                });

        return builder.create();
    }
    public interface AlertaDialogListener
    {
        void onYesClicked();
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        try{
            listener=(AlertaDialogListener)context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement AlergDialogListener");
        }
    }
}
