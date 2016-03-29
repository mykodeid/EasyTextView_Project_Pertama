package id.mykode.easytextview;
import android.preference.*;
import android.os.*;
import android.provider.*;
import android.text.*;
import android.content.*;

/**
 * Created by Suyono on 3/19/2016.
 * Copyright (c) 2016 by Suyono (ion).
 * All rights reserved.
 * This product is protected by copyright and distributed under
 * licenses restricting copying, distribution and decompilation.
 */

@SuppressWarnings("deprecation")
public class SettingTampilkanText extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    //membuat Bundle
    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        addPreferencesFromResource(setResource("setting_tampilkan_text","xml"));
        EditTextPreference edit_isi_text = (EditTextPreference)findPreference("keyeditisitext");
		String stringtxt_dari_setting = Settings.System.getString(getContentResolver(), "uri_edit_isi_text");
		if (!TextUtils.isEmpty(stringtxt_dari_setting)) {
			edit_isi_text.setSummary(stringtxt_dari_setting);
		}
        edit_isi_text.setDialogIcon(setResource("settings","drawable"));
    }

    //saat resume
    @Override
    public void onResume(){
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    //saat pause
    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    //saat preference telah di edit
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        /* jika key samadengan keyeditisitext di preferences xml */
        if (key.equals("keyeditisitext")){
            Preference pref_edit_isi_text= findPreference(key);
            String edit_isi_text = "";
            if(pref_edit_isi_text instanceof EditTextPreference){
                edit_isi_text = ((EditTextPreference)pref_edit_isi_text).getText();
            }
            pref_edit_isi_text.setSummary(edit_isi_text);
            Settings.System.putString(getContentResolver(), "uri_edit_isi_text", edit_isi_text);
        }
    }

    /** menghilangkan id public saat decompile apk
     ** R.tipe.nama menjadi setResource("nama","tipe")
     ** sehingga tidak perlu mengedit id public
     **/
    public int setResource(String Nama, String Tipe)
    {
        return getBaseContext().getResources().getIdentifier(Nama, Tipe, getBaseContext().getPackageName());
    }
}
