package kazpost.kz.mobterminal.ui.scan;

import kazpost.kz.mobterminal.di.PerActivity;
import kazpost.kz.mobterminal.ui.base.MvpPresenter;

/**
 * Created by root on 4/14/17.
 */

@PerActivity
public interface ScanMvpPresenter<V extends ScanMvpView> extends MvpPresenter<V> {

    void onScan(String number);

    void onBagScan(String parcelBarcode, String bagBarcode);
}
