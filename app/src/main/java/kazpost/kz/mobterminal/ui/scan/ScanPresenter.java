package kazpost.kz.mobterminal.ui.scan;

import android.util.Log;

import java.util.concurrent.TimeUnit;

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
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by root on 4/17/17.
 */

public class ScanPresenter<V extends ScanMvpView> extends BasePresenter<V> implements ScanMvpPresenter<V> {

    private static final String TAG = "ScanPresenter";

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

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

        Subscription subscription = observable
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
                                    getMvpView().showMistakeDialog(text);
                                    getMvpView().startLoginActivity();
                                    break;

                                case "106": //Session expired
                                    getMvpView().showMistakeDialog(text);
                                    getMvpView().startLoginActivity();
                                    break;
                                case "300": //User not authorized
                                    getMvpView().showMistakeDialog(text);
                                    getMvpView().clearBagEditText();
                                    break;

                                case "MD-07001":    //ШПИ не найден
                                    getMvpView().showMistakeDialog(text);
                                    getMvpView().readyForNextScan();

                                    break;

                                default:
                                    Log.d(TAG, "default " + responseInfo.getResponseText());

                                    getMvpView().showMistakeDialog(responseInfo.getResponseText());
                                    getMvpView().readyForNextScan();

                                    break;
                            }

                            getMvpView().hideLoading();

                        },
                        throwable -> {
                            Log.d(TAG, "throwable " + throwable.getMessage());

                            getMvpView().showMistakeDialog(throwable.getMessage());
                            getMvpView().hideLoading();

                        });


        compositeSubscription.add(subscription);

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

        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(envelope -> {

                            String responseCode = envelope.getBody().getParcelToBagResponse().getResponseInfo().getResponseCode();
                            String text = envelope.getBody().getParcelToBagResponse().getResponseInfo().getResponseText();
                            String bagWeight = envelope.getBody().getParcelToBagResponse().getResponseInfo().getBagWeight();
                            String quantity = envelope.getBody().getParcelToBagResponse().getResponseInfo().getMailQuantity();

                            switch (responseCode) {
                                case "0":

                                    //success
                                    getMvpView().onErrorToast(text + "    Вес: " + bagWeight + "  Кол-во: " + quantity);
                                    getMvpView().readyForNextScan();
                                    break;

                                case "103": //User not authorized
                                    getMvpView().showMistakeDialog(text);
                                    getMvpView().startLoginActivity();
                                    break;

                                case "106": //Session expired
                                    getMvpView().showMistakeDialog(text);
                                    getMvpView().startLoginActivity();
                                    break;
                                case "300": //Мешок не найден
                                    getMvpView().showMistakeDialog(text);
                                    getMvpView().clearBagEditText();
                                    break;

                                case "6":
                                    //ШПИ уже добавлен в другой документ
                                    getMvpView().showMistakeDialog(text);
                                    getMvpView().readyForNextScan();
                                    break;

                                default:
                                    getMvpView().readyForNextScan();
                                    break;
                            }
                            getMvpView().hideLoading();

                        },
                        throwable -> {
                            getMvpView().showMistakeDialog(throwable.getMessage());
                            getMvpView().hideLoading();
                        });

        compositeSubscription.add(subscription);

    }


    @Override
    public void onDetach() {
        compositeSubscription.clear();
//        Log.d(TAG, "onDetach: called from ScanPresent, compositeSubsctiption.clear()");
    }
}