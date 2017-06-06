package kazpost.kz.mobterminal.ui.closecell;

import android.os.Bundle;

import javax.inject.Inject;

import kazpost.kz.mobterminal.data.DataManager;
import kazpost.kz.mobterminal.data.network.model.closebag.CloseBagBody;
import kazpost.kz.mobterminal.data.network.model.closebag.CloseBagData;
import kazpost.kz.mobterminal.data.network.model.closebag.CloseBagEnvelope;
import kazpost.kz.mobterminal.data.network.model.closebag.Envelope;
import kazpost.kz.mobterminal.ui.base.BasePresenter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static kazpost.kz.mobterminal.utils.AppConstants.BAG_TYPE;
import static kazpost.kz.mobterminal.utils.AppConstants.CLOSE_BAG_TIME;
import static kazpost.kz.mobterminal.utils.AppConstants.FROM_DEP;
import static kazpost.kz.mobterminal.utils.AppConstants.G_NUMBER;
import static kazpost.kz.mobterminal.utils.AppConstants.OPERATOR_NAME;
import static kazpost.kz.mobterminal.utils.AppConstants.SEAL_NUMBER;
import static kazpost.kz.mobterminal.utils.AppConstants.SEND_METHOD;
import static kazpost.kz.mobterminal.utils.AppConstants.TO_DEP;
import static kazpost.kz.mobterminal.utils.AppConstants.WEIGHT_RESPONSE;

/**
 * Created by root on 4/14/17.
 */

public class CloseCellPresenter<V extends CloseCellMvpView> extends BasePresenter<V> implements CloseCellMvpPresenter<V> {

    @Inject
    public CloseCellPresenter(DataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void openPrintActivity() {

    }


    @Override
    public void closeBagRequest(String bagBar, String sealNum, String weight) {

        getMvpView().showLoading();

       /* Bundle bundle = new Bundle();
        bundle.putString(G_NUMBER, "G201704250001179");
        bundle.putString(SEAL_NUMBER, "1223");
        bundle.putString(WEIGHT_RESPONSE, "1");
        bundle.putString(FROM_DEP, "Участок мжд. почты г.Алматы [220096]");
        bundle.putString(TO_DEP, "Участок МЖД и ПК г. Астана [220081]");
        bundle.putString(SEND_METHOD, "Наземный");
        bundle.putString(BAG_TYPE, "Мешок \"Сақтандыру\"");
        bundle.putString(OPERATOR_NAME, "Новосельцева Евгения");
        bundle.putString(CLOSE_BAG_TIME, "2017-01-12T17:39:50.996+06:00");

        getMvpView().openPrintActivity(bundle);
        getMvpView().hideLoading();*/


        CloseBagEnvelope closeBagEnvelope = createBagEnvelope(bagBar, sealNum, weight);

        Observable<Envelope> observable = getDataManager().doCloseBag(closeBagEnvelope);
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(envelope -> {

                            String responseCode = envelope.getBody().getCloseBagResponse().getResponseInfo().getResponseCode();
                            String text = envelope.getBody().getCloseBagResponse().getResponseInfo().getResponseText();

                            switch (responseCode) {
                                case "0":

                                    Envelope.CloseBagResponse closeBagResponse = envelope.getBody().getCloseBagResponse();
                                    getMvpView().openPrintActivity(createBundle(closeBagResponse));
                                    break;

                                case "301"://Б накладная не создана
                                    getMvpView().onErrorToast(text);
                                    break;

                                case "103": //User not authorized
                                    getMvpView().onErrorToast(text);
                                    getMvpView().startLoginActivity();
                                    break;

                                case "106": //Session expired
                                    getMvpView().onErrorToast(text);
                                    getMvpView().startLoginActivity();
                                    break;

                                case "300": //bag not found
                                    getMvpView().onErrorToast(text);
                                    break;

                                default:
                                    getMvpView().onErrorToast(text);
                                    break;
                            }


                            getMvpView().hideLoading();
                        },
                        throwable -> {
                            getMvpView().onErrorToast(throwable.getMessage());
                            getMvpView().hideLoading();
                        });


    }

    private CloseBagEnvelope createBagEnvelope(String bagBar, String sealNum, String weight) {
        CloseBagEnvelope closeBagEnvelope = new CloseBagEnvelope();
        CloseBagBody closeBagBody = new CloseBagBody();
        CloseBagData closeBagData = new CloseBagData();

        closeBagData.setASessionId(getDataManager().getSessionId());
        closeBagData.setBBagBarcode(bagBar);
        closeBagData.setCSealNumber(sealNum);
        closeBagData.setDWeight(weight);

        closeBagBody.setCloseBagData(closeBagData);
        closeBagEnvelope.setCloseBagBody(closeBagBody);

        return closeBagEnvelope;
    }


    private Bundle createBundle(Envelope.CloseBagResponse closeBagResponse) {
        Bundle bundle = new Bundle();
        bundle.putString(G_NUMBER, closeBagResponse.getGNumber());
        bundle.putString(SEAL_NUMBER, closeBagResponse.getSealNumber());
        bundle.putString(WEIGHT_RESPONSE, closeBagResponse.getWeight());
        bundle.putString(FROM_DEP, closeBagResponse.getFromDep());
        bundle.putString(TO_DEP, closeBagResponse.getToDep());
        bundle.putString(SEND_METHOD, closeBagResponse.getSendMethod());
        bundle.putString(BAG_TYPE, closeBagResponse.getBagType());
        bundle.putString(OPERATOR_NAME, closeBagResponse.getOperatorName());
        bundle.putString(CLOSE_BAG_TIME, closeBagResponse.getResponseInfo().getResponseGenTime());

        return bundle;
    }

}
