package com.example.cryptoportfolio;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ExampleDialog extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {
    private EditText editTextAmount;
    private ExampleDialogListener listener;
    private Spinner cryptoSpinner;

    private String cryptoSelection;
    private int cryptoPosition;

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
                        // get item at position??
//                        String cryptoSpinner = cryptoSelection;
                        String amount = editTextAmount.getText().toString();

                        // allows cryptoSpinner and amount to be pulled into main and used there
                        listener.saveData(cryptoPosition, amount);
                    }
                });

        cryptoSpinner = (Spinner) view.findViewById(R.id.crypto_spinner);
        editTextAmount = (EditText) view.findViewById(R.id.edit_amount);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        // tell it its drop down
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // set spinner to adapter
        cryptoSpinner.setAdapter(arrayAdapter);
        cryptoSpinner.setOnItemSelectedListener(this);

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

    // selects spinner item
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cryptoSelection = parent.getItemAtPosition(position).toString();
        cryptoPosition = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface ExampleDialogListener {
        void saveData(int position, String amount);
    }
}
