package kazpost.kz.mobterminal.ui.scan;

import android.util.Log;

import javax.inject.Inject;

import kazpost.kz.mobterminal.data.DataManager;
import kazpost.kz.mobterminal.data.network.model.findplan.Envelope;
import kazpost.kz.mobterminal.data.network.model.findplan.FindPlanBody;
import kazpost.kz.mobterminal.data.network.model.findplan.FindPlanData;
import kazpost.kz.mobterminal.data.network.model.findplan.FindPlanEnvelope;
import kazpost.kz.mobterminal.data.network.model.parcel.ParcelBody;
import kazpost.kz.mobterminal.data.network.model.parcel.ParcelData;
import kazpost.kz.mobterminal.data.network.model.parcel.ParcelEnvelope;
import kazpost.kz.mobterminal.ui.base.BasePresenter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by root on 4/17/17.
 */

public class ScanPresenter<V extends ScanMvpView> extends BasePresenter<V> implements ScanMvpPresenter<V> {

    private static final String TAG = "ScanPresenter";


    @Inject
    public ScanPresenter(DataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void onScan(String number) {

        getMvpView().showLoading();

        FindPlanEnvelope findPlanEnvelope = new FindPlanEnvelope();

        FindPlanBody findPlanBody = new FindPlanBody();
        FindPlanData findPlanData = new FindPlanData();
        findPlanData.setBParcelBarcode(number);
        findPlanData.setASessionId(getDataManager().getSessionId());
//        findPlanData.setBParcelBarcode("RR460877842BY");

        findPlanBody.setFindPlanData(findPlanData);
        findPlanEnvelope.setFindPlanBody(findPlanBody);

        Observable<Envelope> observable = getDataManager().doFindPlan(findPlanEnvelope);

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        envelope -> {
                            Envelope.ResponseInfo responseInfo = envelope.getBody().getFindPlanResponse().getResponseInfo();
                            String text = envelope.getBody().getFindPlanResponse().getResponseInfo().getResponseText();

                            switch (responseInfo.getResponseCode()) {
                                case "0":

//                                String responseGenTime = responseInfo.getResponseGenTime();

                                    getMvpView().showBagTrackNumber(envelope.getBody().getFindPlanResponse().getBagBarcode(),
                                            envelope.getBody().getFindPlanResponse().getBagNumber());

                                    break;

                                case "103": //User not authorized
                                    getMvpView().onErrorToast(text);
                                    getMvpView().startLoginActivity();
                                    break;

                                case "106": //Session expired
                                    getMvpView().onErrorToast(text);
                                    getMvpView().startLoginActivity();
                                    break;


                                default:
                                    Log.d(TAG, "default " + responseInfo.getResponseText());

                                    getMvpView().onErrorToast(responseInfo.getResponseText());
                                    getMvpView().readyForNextScan();

                                    break;
                            }

                            getMvpView().hideLoading();
                        },
                        throwable -> {
                            Log.d(TAG, "throwable " + throwable.getMessage());

                            getMvpView().onErrorToast(throwable.getMessage());
                            getMvpView().hideLoading();

                        });


    }

    @Override
    public void onBagScan(String parcelBarcode, String bagBarcode) {

        getMvpView().showLoading();

        ParcelEnvelope parcelEnvelope = new ParcelEnvelope();

        ParcelBody parcelBody = new ParcelBody();
        ParcelData parcelData = new ParcelData();
        parcelData.setASessionId(getDataManager().getSessionId());
//        parcelData.setBParcelBarcode("RR460877842BY");
        parcelData.setBParcelBarcode(parcelBarcode);
        parcelData.setCBagBarcode(bagBarcode);

        parcelBody.setParcelData(parcelData);
        parcelEnvelope.setParcelBody(parcelBody);

        Observable<kazpost.kz.mobterminal.data.network.model.parcel.Envelope> observable = getDataManager().doParcelToBag(parcelEnvelope);

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(envelope -> {

                            String responseCode = envelope.getBody().getParcelToBagResponse().getResponseInfo().getResponseCode();
                            String text = envelope.getBody().getParcelToBagResponse().getResponseInfo().getResponseText();

                            switch (responseCode) {
                                case "0":

                                    //success
                                    getMvpView().onErrorToast(envelope.getBody().getParcelToBagResponse().getResponseInfo().getResponseText());

                                    getMvpView().readyForNextScan();

                                    break;

                                case "103": //User not authorized
                                    getMvpView().onErrorToast(text);
                                    getMvpView().startLoginActivity();
                                    break;

                                case "106": //Session expired
                                    getMvpView().onErrorToast(text);
                                    getMvpView().startLoginActivity();
                                    break;

                                case "6":
                                    //ШПИ уже добавлен в другой документ
                                    getMvpView().onErrorToast(envelope.getBody().getParcelToBagResponse().getResponseInfo().getResponseText());
                                    getMvpView().readyForNextScan();

                                    break;

                                default:
                                    getMvpView().readyForNextScan();
                                    break;
                            }
                            getMvpView().hideLoading();

                        },


                        throwable -> {

                            getMvpView().onErrorToast(throwable.getMessage());
                            getMvpView().hideLoading();

                        });
    }

}