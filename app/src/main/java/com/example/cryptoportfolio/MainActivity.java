package com.example.cryptoportfolio;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.cryptoportfolio.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {

    private ActivityMainBinding binding;

    private TextView textViewCrypto;
    private TextView textViewAmount;
    private FloatingActionButton addButton;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String CRYPTO_SPINNER = "crpyto";
    public static final String AMOUNT = "1";

    private String cryptoSpinner;
    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        textViewCrypto = (TextView) findViewById(R.id.textview_crypto);
        textViewAmount = (TextView) findViewById(R.id.textview_amount);

        setSupportActionBar(binding.toolbar);

        addButton = (FloatingActionButton) findViewById(R.id.addButton);
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        loadData();
        updateViews();
    }

    public void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    // save data to local storage
    @Override
    public void saveData(String cryptoSpinner, String amount) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // save to shared prefernces (local storage)
        editor.putString(CRYPTO_SPINNER, cryptoSpinner);
        editor.putString(AMOUNT, amount);

        editor.apply();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();

        loadData();
        updateViews();
    }

    // load data back into app
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        cryptoSpinner = sharedPreferences.getString(CRYPTO_SPINNER, "default");
        amount = sharedPreferences.getString(AMOUNT, "default");
    }

    // update values on the app
    public void updateViews() {
        textViewCrypto.setText(cryptoSpinner);
        textViewAmount.setText(amount);
    }


}