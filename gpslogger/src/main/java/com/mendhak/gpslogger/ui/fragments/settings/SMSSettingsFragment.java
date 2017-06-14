package com.mendhak.gpslogger.ui.fragments.settings;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;

import com.canelmas.let.AskPermission;
import com.mendhak.gpslogger.R;
import com.mendhak.gpslogger.common.PreferenceHelper;
import com.mendhak.gpslogger.common.PreferenceNames;
import com.mendhak.gpslogger.common.Strings;
import com.mendhak.gpslogger.common.slf4j.Logs;
import com.mendhak.gpslogger.senders.PreferenceValidator;
import com.mendhak.gpslogger.senders.sftp.SFTPManager;
import com.mendhak.gpslogger.ui.fragments.PermissionedPreferenceFragment;
import com.nononsenseapps.filepicker.FilePickerActivity;

import org.slf4j.Logger;

public class SMSSettingsFragment extends PermissionedPreferenceFragment implements Preference.OnPreferenceChangeListener, PreferenceValidator {

    private static final Logger LOG = Logs.of(SMSSettingsFragment.class);
    PreferenceHelper preferenceHelper = PreferenceHelper.getInstance();

    public void onCreate(Bundle savedInstanceState) {
        LOG.debug("on create sms prefs");
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.smssettings);

        findPreference(PreferenceNames.SMS_ENABLED).setOnPreferenceChangeListener(this);
        findPreference(PreferenceNames.SMS_NUM1).setOnPreferenceChangeListener(this);
        findPreference(PreferenceNames.SMS_NUM2).setOnPreferenceChangeListener(this);
        findPreference(PreferenceNames.SMS_NUM3).setOnPreferenceChangeListener(this);
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }


    @Override
    @AskPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference.getKey().equals(PreferenceNames.SMS_ENABLED)) {
            preferenceHelper.setSmsFeatureEnabled((Boolean)newValue);
        }
        else if (preference.getKey().equals(PreferenceNames.SMS_NUM1)) {
            preferenceHelper.setSmsNum1((String)newValue);
        }
        else if (preference.getKey().equals(PreferenceNames.SMS_NUM2)) {
            preferenceHelper.setSmsNum2((String)newValue);
        }
        else if (preference.getKey().equals(PreferenceNames.SMS_NUM3)) {
            preferenceHelper.setSmsNum3((String)newValue);
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public boolean isValid() {
        return true;
    }
}
