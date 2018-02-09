package kazpost.kz.mobterminal.ui.scan;

import kazpost.kz.mobterminal.ui.base.MvpView;

/**
 * Created by root on 4/14/17.
 */

public interface ScanMvpView extends MvpView {

    void clearEditText();

    void clearBagEditText();

    void showBagTrackNumber(String bagBarcode, String bagNumber);

    void readyForNextScan();

    void startLoginActivity();

    void showMistakeDialog(String msg);

    void showLoading();

    void hideLoading();

}
