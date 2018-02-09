package kazpost.kz.mobterminal.ui.openbagscan;

import kazpost.kz.mobterminal.ui.base.MvpView;

/**
 * Created by root on 1/8/18.
 */

public interface OpenBagScanView extends MvpView {

    void showMistakeDialog(String msg);

    void showMistakeDialog(boolean success);

    void startLoginActivity();

    void clearEditText();

    void showLoading();

    void hideLoading();
}
