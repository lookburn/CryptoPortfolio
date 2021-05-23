package com.example.cryptoportfolio;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.cryptoportfolio.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cryptoportfolio.databinding.ActivityMainBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.provider.Telephony.Mms.Part.TEXT;

public class MainActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {

    private ActivityMainBinding binding;

    private TextView textViewUsername;
    private TextView textViewPassword;
    private FloatingActionButton addButton;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        textViewUsername = (TextView) findViewById(R.id.textview_username);
        textViewPassword = (TextView) findViewById(R.id.textview_password);

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
    public void saveData(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // save to shared prefernces (local storage)
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, password);

        editor.apply();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();

        loadData();
        updateViews();
    }

    // load data back into app
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        username = sharedPreferences.getString(USERNAME, "default");
        password = sharedPreferences.getString(PASSWORD, "default");
    }

    // update values on the app
    public void updateViews() {
        textViewUsername.setText(username);
        textViewPassword.setText(password);
    }


}