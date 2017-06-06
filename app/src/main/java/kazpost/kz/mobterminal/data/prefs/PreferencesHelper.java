package kazpost.kz.mobterminal.data.prefs;

/**
 * Created by root on 4/12/17.
 */

public interface PreferencesHelper {

    void saveSessionId(String sessionId);

    void saveLastLoginTime(String dateTime);

    String getSessionId();

    String getLastLoginTime();
}
