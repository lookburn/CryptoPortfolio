package com.example.cryptoportfolio;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ExampleDialog extends AppCompatDialogFragment {
    private EditText editTextUsername, editTextPassword;
    private ExampleDialogListener listener;

    public ExampleDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        // fill dialog with view
        builder.setView(view)
                .setTitle("Login")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // nothing will happen
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // pull text out
                        String username = editTextUsername.getText().toString();
                        String password = editTextPassword.getText().toString();

                        // allows username and password to be pulled into main and used there
                        listener.saveData(username, password);
                    }
                });

        editTextUsername = (EditText) view.findViewById(R.id.edit_username);
        editTextPassword = (EditText) view.findViewById(R.id.edit_password);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // so need to implement in main activity
        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException((context.toString() + "must implement ExampleDialogListener"));
        }
    }

    public interface ExampleDialogListener {
        void saveData(String username, String password);
    }
}
