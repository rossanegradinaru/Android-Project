package ro.ase.echipa_4;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
        Load_setting();
    }
    private void Load_setting(){
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
        boolean chk_night=sp.getBoolean("NIGHT",false);
        if(chk_night){
            getListView().setBackgroundColor(Color.parseColor("#222222"));
        }else{
            getListView().setBackgroundColor(Color.parseColor("#ffffff"));
        }

        CheckBoxPreference chk_night_instant=(CheckBoxPreference)findPreference("NIGHT");
        chk_night_instant.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener(){
            @Override
            public boolean onPreferenceChange(android.preference.Preference prefs, Object obj) {
               boolean yes=(boolean)obj;
               if(yes){
                   getListView().setBackgroundColor(Color.parseColor("#222222"));
               }else{
                   getListView().setBackgroundColor(Color.parseColor("#ffffff"));
               }
                return true;
            }

        });

        ListPreference LP=(ListPreference)findPreference("ORIENTARE");
        String or=sp.getString("ORIENTARE","false");
        if("1".equals(or)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
            LP.setSummary(LP.getEntry());
        }
        else if("2".equals(or)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            LP.setSummary(LP.getEntry());
        }
        else if("3".equals(or)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            LP.setSummary(LP.getEntry());
        }
        LP.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference prefs, Object obj) {
                String items=(String)obj;
                if(prefs.getKey().equals("ORIENTARE"))
                {
                    switch(items){
                        case "1":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
                            break;
                        case "2":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            break;
                        case "3":
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            break;
                    }
                    ListPreference LPP=(ListPreference) prefs;
                    LPP.setSummary(LPP.getEntries()[LPP.findIndexOfValue(items)]);
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        Load_setting();
        super.onResume();
    }
}