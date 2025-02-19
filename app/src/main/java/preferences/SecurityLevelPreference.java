package preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.ihm15.project.phonetection.Data;
import com.ihm15.project.phonetection.R;

import dialogs.SecurityLevelDialog;

public class SecurityLevelPreference extends Preference implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    public SecurityLevelPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        View view = super.onCreateView(parent);
        Data.getInstance(getContext());
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        sp.registerOnSharedPreferenceChangeListener(this);

        setSecurityLevelSummary();

        return view;
    }

    @Override
    protected void onClick() {
        SecurityLevelDialog cpd = new SecurityLevelDialog();
        cpd.show(((FragmentActivity) getContext()).getSupportFragmentManager(), "security_level");
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.println(Log.DEBUG, "", "BANANA :" + key);
        if (key.equals(getContext().getString(R.string.pref_key_security_level))){
            setSecurityLevelSummary();
        }
    }


    public void setSecurityLevelSummary(){
        Data.getInstance(getContext());
        String sl = Data.getSecurityLevel();
        if (sl == getContext().getString(R.string.pref_security_level_low))
            setSummary(getContext().getString(R.string.security_level_low));
        else if (sl == getContext().getString(R.string.pref_security_level_medium))
            setSummary(getContext().getString(R.string.security_level_medium));
        else setSummary(getContext().getString(R.string.security_level_high));
    }
}