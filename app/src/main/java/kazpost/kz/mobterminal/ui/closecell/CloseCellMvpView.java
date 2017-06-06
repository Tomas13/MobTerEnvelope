package kazpost.kz.mobterminal.ui.closecell;

import android.os.Bundle;

import kazpost.kz.mobterminal.ui.base.MvpView;

/**
 * Created by root on 4/14/17.
 */

public interface CloseCellMvpView extends MvpView {

    void openPrintActivity(Bundle bundle);

    void startLoginActivity();

}
