package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    ListPreference colorPref;
    EditTextPreference numPref;
    private String sharedPrefsFile = "com.example.android.settingsapp";
    SharedPreferences mPreference;
    String currentColor, currentNum;

    @Override
    public void onPause(){
        super.onPause();
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putString("num", currentNum);
        editor.putString("current_color", currentColor);
        editor.clear();
        editor.apply();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        colorPref = (ListPreference) findPreference("color");

        numPref = (EditTextPreference) findPreference("number");

        mPreference = getActivity().getSharedPreferences(sharedPrefsFile, MODE_PRIVATE);
        currentNum = mPreference.getString("num", "0");
        numPref.setSummary("Your start number is: " + currentNum);
        numPref.setDefaultValue(currentNum);

        currentColor = mPreference.getString("current_color", currentColor);
        colorPref.setSummary("Your default color is: " + currentColor);
        colorPref.setDefaultValue(currentColor);

        colorPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                colorPref = (ListPreference) preference;
                preference.setSummary("Your default color is: " + String.valueOf(newValue));
                colorPref.setDefaultValue(newValue);
                currentColor = String.valueOf(newValue);
                return true;
            }
        });
        numPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                numPref = (EditTextPreference) preference;
                preference.setSummary("Your start number is: " + String.valueOf(newValue));
                numPref.setDefaultValue(newValue);
                currentNum = String.valueOf(newValue);
                return true;
            }
        });
    }
}