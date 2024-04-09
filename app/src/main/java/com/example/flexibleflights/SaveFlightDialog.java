package com.example.flexibleflights;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public class SaveFlightDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        UserSingleton var = UserSingleton.getInstance();
        // Use the Builder class for convenient dialog construction.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Save offer?")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Save post to DB
                        if(var.getEmail() == ""){ //User not logged in, using guest
                            Toast.makeText(getActivity(), "Can not save as a guest", Toast.LENGTH_LONG).show();
                        }
                        else { //User is logged in, can save flights

                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancels the dialog.
                    }
                });
        // Create the AlertDialog object and return it.
        return builder.create();
    }
}