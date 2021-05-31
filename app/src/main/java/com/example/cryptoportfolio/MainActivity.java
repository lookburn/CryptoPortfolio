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
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {

    private ActivityMainBinding binding;

    private TextView textViewCrypto;
    private TextView textViewAmount;
    private FloatingActionButton addButton;

    public static final String SHARED_PREFS = "sharedPrefs";
//    public static final String[] CRYPTO_SPINNER = new String[3];
//    public static final Double[] AMOUNFT = new Double[3];
    public static final String AMOUNT = "0,0,0";

    private String cryptoSpinner;
    public String[] crypto;
    private String[] amount;
    private ArrayList<ExampleItem> exampleList;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // initialise amount
        amount = new String[]{"0","0","0"};
        crypto = new String[]{"BTC","ETH","ADA"};

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

        // for first run through; need to pre-load
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

        /* TODO:
                saves only one var, not array
                data is there but doesn't preload on recyclerview

        */
        // save to shared preferences (local storage)
//        editor.putString(CRYPTO_SPINNER[position], cryptoSpinner);

        // turn AMOUNT into array
        String[] ar = sharedPreferences.getString(AMOUNT, "0,0,0").split(",");
        // change value
        ar[position] = amount;
        // turn back into string
        // update prefs
        // amount = [0,0,0], so need to remove first and last char before updating pref
        String amountPref = Arrays.toString(ar);
        editor.putString(AMOUNT, amountPref.substring( 1, amountPref.length() - 1 ));

        editor.apply();

        Toast.makeText(this, amount + " of " + crypto[position] + " @ " + position + " added", Toast.LENGTH_SHORT).show();

        loadData();
        updateViews();
    }

    // load data back into app
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        String amountPref = sharedPreferences.getString(AMOUNT,"0,0,0");

        System.out.println("PRESUBSTRING..."+amountPref);

        // replace whitespace
        amountPref = amountPref.replaceAll("\\s+","");

        System.out.println(amountPref);

        // turn AMOUNT into array
        String[] ar = amountPref.split(",");

        System.out.println("LEN..." + amount.length + " AND " + ar.length + " OF " + ar[0]+ar[1]);

        for (int i = 0; i < 3; i++) { // TODO: MAGIC NUMBER
//            cryptoSpinner = sharedPreferences.getString(CRYPTO_SPINNER[i], "default");
            amount[i] = ar[i];
        }
    }

    // update recycler view
    public void updateViews() {
        for (int i = 0; i < 3; i++) { // TODO: MAGIC NUMBER
            if (Integer.parseInt(amount[i]) > 0) {
                Toast.makeText(this, "loading data..." + amount[i] + " of " + crypto[i] + "...", Toast.LENGTH_SHORT).show();
                exampleList.add(new ExampleItem(R.drawable.ic_baseline_add_24, crypto[i], amount[i]));
            }
        }

//        textViewCrypto.setText(cryptoSpinner);
//        textViewAmount.setText(amount);

        Toast.makeText(this, "updating view..."+exampleList.size(), Toast.LENGTH_SHORT).show();

    }


}