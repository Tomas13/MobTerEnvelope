package kazpost.kz.mobterminal.ui.closecell;

import kazpost.kz.mobterminal.di.PerActivity;
import kazpost.kz.mobterminal.ui.base.MvpPresenter;

/**
 * Created by root on 4/14/17.
 */

@PerActivity
public interface CloseCellMvpPresenter<V extends CloseCellMvpView> extends MvpPresenter<V> {

    void openPrintActivity();


    void closeBagRequest(String bagBar, String sealNum, String weight);
}
