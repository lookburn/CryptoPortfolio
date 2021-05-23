package com.example.cryptoportfolio;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.cryptoportfolio.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {

    private ActivityMainBinding binding;

    private TextView textViewCrypto;
    private TextView textViewAmount;
    private FloatingActionButton addButton;

    public static final String SHARED_PREFS = "sharedPrefs";
//    public static final String[] CRYPTO_SPINNER = new String[3];
//    public static final Double[] AMOUNT = new Double[3];
    public static final String CRYPTO_SPINNER = "crypto";
    public static final String AMOUNT = "1";

    private String cryptoSpinner;
    private String amount;
    private ArrayList<ExampleItem> exampleList;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initialise recyclerview
        exampleList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recyclerView);
//        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

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
    public void saveData(int position, String cryptoSpinner, String amount) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // save to shared preferences (local storage)
        editor.putString(CRYPTO_SPINNER, cryptoSpinner);
        editor.putString(AMOUNT, amount);

        editor.apply();

        Toast.makeText(this, sharedPreferences.getString(CRYPTO_SPINNER, "default") + " added", Toast.LENGTH_SHORT).show();

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
        // update recycler view
        exampleList.add(new ExampleItem(R.drawable.ic_baseline_add_24, cryptoSpinner, amount));

//        textViewCrypto.setText(cryptoSpinner);
//        textViewAmount.setText(amount);
    }


}