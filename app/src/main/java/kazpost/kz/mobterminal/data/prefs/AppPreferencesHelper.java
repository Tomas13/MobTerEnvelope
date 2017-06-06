package kazpost.kz.mobterminal.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import kazpost.kz.mobterminal.di.ApplicationContext;
import kazpost.kz.mobterminal.di.PreferenceInfo;

/**
 * Created by root on 4/12/17.
 */

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private final SharedPreferences mPrefs;

    private static final String PREF_KEY_SESSION_ID = "PREF_KEY_SESSION_ID";
    private static final String PREF_KEY_DATE_TIME = "PREF_KEY_DATE_TIME";


    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public void saveSessionId(String sessionId) {
        mPrefs.edit().putString(PREF_KEY_SESSION_ID, sessionId).apply();
    }

    @Override
    public void saveLastLoginTime(String dateTime) {
        mPrefs.edit().putString(PREF_KEY_DATE_TIME, dateTime).apply();
    }

    @Override
    public String getSessionId() {
        return mPrefs.getString(PREF_KEY_SESSION_ID, "no_session_id");
    }

    @Override
    public String getLastLoginTime() {
        return mPrefs.getString(PREF_KEY_DATE_TIME, "no_login_time");
    }
}
