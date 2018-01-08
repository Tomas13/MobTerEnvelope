package kazpost.kz.mobterminal.ui.openbagscan;

import kazpost.kz.mobterminal.di.PerActivity;
import kazpost.kz.mobterminal.ui.base.MvpPresenter;

/**
 * Created by root on 1/8/18.
 */

@PerActivity
public interface OpenBagMvpPresenter<V extends OpenBagScanView> extends MvpPresenter<V> {
    void onOpenBagScan(String number);

}
