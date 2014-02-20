package ru.vorobjev.rbcnews.activities;

import ru.vorobjev.rbknews.R;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

public class PreferencesActivity extends PreferenceActivity {
	
		// API < 11 :(
//		@Override
//		public void onBuildHeaders(List<Header> target) {
//			loadHeadersFromResource(R.xml.preference_headers, target);
//		}
	
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            
            addPreferencesFromResource(R.layout.preferences);
            Preference category = (Preference) findPreference("category");
            category.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
				
				@Override
				public boolean onPreferenceChange(Preference preference, Object newValue) {
					
					return true;
				}
			});
    }
}
