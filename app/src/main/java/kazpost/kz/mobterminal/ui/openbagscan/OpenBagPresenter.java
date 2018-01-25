package kazpost.kz.mobterminal.ui.openbagscan;

import android.util.Log;

import javax.inject.Inject;

import kazpost.kz.mobterminal.data.DataManager;
import kazpost.kz.mobterminal.data.network.model.openbag.Envelope;
import kazpost.kz.mobterminal.data.network.model.openbag.ScanOpenBagData;
import kazpost.kz.mobterminal.data.network.model.openbag.ScanOpenBagEnvelope;
import kazpost.kz.mobterminal.data.network.model.openbag.ScanOpenBagRequestBody;
import kazpost.kz.mobterminal.ui.base.BasePresenter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by root on 1/8/18.
 */

public class OpenBagPresenter<V extends OpenBagScanView> extends BasePresenter<V> implements OpenBagMvpPresenter<V> {


    private static final String TAG = "OpenBagPresenter";

    @Inject
    public OpenBagPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void onOpenBagScan(String number) {
        getMvpView().showLoading();

        ScanOpenBagEnvelope findPlanEnvelope = new ScanOpenBagEnvelope();

        ScanOpenBagRequestBody findPlanBody = new ScanOpenBagRequestBody();
        ScanOpenBagData findPlanData = new ScanOpenBagData();
        findPlanData.setBParcelBarcode(number);
        findPlanData.setASessionId(getDataManager().getSessionId());
//        findPlanData.setBParcelBarcode("RR460877842BY");

        findPlanBody.setScanOpenBagData(findPlanData);
        findPlanEnvelope.setScanOpenBagRequestBody(findPlanBody);

        Observable<Envelope> observable = getDataManager().doFindOpenBagPlan(findPlanEnvelope);

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        envelope -> {
                            Envelope.ResponseInfo responseInfo = envelope.getBody().getRegWAYBILLOpResponse().getResponseInfo();
                            String text = envelope.getBody().getRegWAYBILLOpResponse().getResponseInfo().getResponseText();

                            switch (responseInfo.getResponseCode()) {
                                case "0":

//                                String responseGenTime = responseInfo.getResponseGenTime();

//                                    getMvpView().showBagTrackNumber(envelope.getBody().getFindPlanResponse().getBagBarcode(),
//                                            envelope.getBody().getFindPlanResponse().getBagNumber());

                                    getMvpView().showMistakeDialog(true);
                                    break;

                                case "103": //User not authorized
                                    getMvpView().showMistakeDialog(text);
                                    getMvpView().startLoginActivity();
                                    break;

                                case "106": //Session expired
                                    getMvpView().showMistakeDialog(text);
                                    getMvpView().startLoginActivity();
                                    break;
                                case "300": //User not authorized
                                    getMvpView().showMistakeDialog(text);
                                    getMvpView().clearEditText();
                                    break;

                                case "MD-07001":    //ШПИ не найден
                                    getMvpView().showMistakeDialog(text);
                                    getMvpView().clearEditText();
                                    break;

                                default:
                                    Log.d(TAG, "default " + responseInfo.getResponseText());

                                    getMvpView().showMistakeDialog(responseInfo.getResponseText());
                                    getMvpView().clearEditText();

                                    break;
                            }

                            getMvpView().hideLoading();
                        },
                        throwable -> {
                            Log.d(TAG, "throwable " + throwable.getMessage());

                            getMvpView().showMistakeDialog(throwable.getMessage());
                            getMvpView().hideLoading();

                        });
    }

}
