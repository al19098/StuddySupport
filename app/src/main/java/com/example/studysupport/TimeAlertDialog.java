/********************************************************************
 ***  ModuleName  :Percentcal.java
 ***  Version     :V1.0
 ***  Designer    :桑原　赳
 ***  Date        :2021.06.16
 ***  Purpose     :目標時間入力ダイアログ
 ***
 ********************************************************************/
/*
 ***Revision :
 *** V1.0 : 桑原赳 2021.06.16
 */

package com.example.studysupport;

/*********************************************/
/*   import file（ファイルの取り込み）    */
/*********************************************/
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
