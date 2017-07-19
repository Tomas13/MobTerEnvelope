package kazpost.kz.mobterminal.data.prefs;

/**
 * Created by root on 4/12/17.
 */

public interface PreferencesHelper {

    void saveSessionId(String sessionId);

    void saveLastLoginTime(String dateTime);

    void removeLastLoginTime();

    void savePrinter(String serverIp, String ipAddress, String printerName);

    String getPrinterIp();

    String getPrinterName();

    String getSessionId();

    String getLastLoginTime();

    String getServerIp();
}
