package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    TextView tvNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNum = findViewById(R.id.tv_num);

        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        String numPref = sharedPreferences.getString("number","0");
        updateNum(numPref);

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        String colorPref = sharedPreferences.getString("color", "");
        updateBackgroundColor(colorPref);

    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            default: return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("color")){
            String value = sharedPreferences.getString(key, "blue");
            updateBackgroundColor(value);
        }
        if(key.equals("number")){
            String value = sharedPreferences.getString(key, "0");
            updateNum(value);
        }
    }
    public void updateBackgroundColor(String value){
        if(value != null || !value.isEmpty()){
            int colorId = getResources().getIdentifier(value, "color", getPackageName());
            tvNum.setBackgroundColor(ContextCompat.getColor(this, colorId));
        }
    }
    public void updateNum(String value){
        if(value != null || !value.isEmpty()){
            tvNum.setText(String.valueOf(value));
        }
    }
}