package com.example.yukisnote.ui;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.text.TextUtils;

import com.example.yukisnote.R;
import com.example.yukisnote.util.PreferenceUtils;
import com.jenzz.materialpreference.SwitchPreference;


public class SettingFragment extends PreferenceFragment {

    public static final String PREFERENCE_FILE_NAME = "note.settings";

    private PreferenceUtils preferenceUtils;

    private SwitchPreference rightmode;

    boolean isRightmode;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        preferenceUtils = PreferenceUtils.getInstance(getActivity());
        getPreferenceManager().setSharedPreferencesName(PREFERENCE_FILE_NAME);

        isRightmode = preferenceUtils.getBooleanParam(getString(R.string.right_key));

        rightmode = (SwitchPreference) findPreference(getString(R.string.right_key));

        rightmode.setChecked(isRightmode);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {

        if (preference == null)
            return super.onPreferenceTreeClick(preferenceScreen, preference);
        String key = preference.getKey();

        if(TextUtils.equals(key,getString(R.string.right_key)))
        {
           isRightmode = !isRightmode;
           preferenceUtils.saveParam(getString(R.string.right_key),isRightmode);

        }
        if(TextUtils.equals(key,getString(R.string.advice_key)))
        {
            showFeedback();
        }
        return true;
    }

    public void showFeedback()
    {
        Intent i = new Intent(getActivity(),FeedbackActivity.class);
        startActivity(i);
    }

}
