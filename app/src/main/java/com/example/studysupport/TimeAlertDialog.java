package com.example.studysupport;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;


import androidx.fragment.app.DialogFragment;

public class TimeAlertDialog extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialogtitle)
                .setMessage(R.string.dialogmsg)
                .setPositiveButton(R.string.dialogbutton,new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dismiss();
                    }
                });
        return builder.create();
    }
}
