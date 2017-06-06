package kazpost.kz.mobterminal.data;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import kazpost.kz.mobterminal.data.network.ApiHelper;
import kazpost.kz.mobterminal.data.network.model.Envelope;
import kazpost.kz.mobterminal.data.network.model.closebag.CloseBagEnvelope;
import kazpost.kz.mobterminal.data.network.model.findplan.FindPlanEnvelope;
import kazpost.kz.mobterminal.data.network.model.parcel.ParcelEnvelope;
import kazpost.kz.mobterminal.data.network.model.request.RequestEnvelope;
import kazpost.kz.mobterminal.data.prefs.PreferencesHelper;
import kazpost.kz.mobterminal.di.ApplicationContext;
import rx.Observable;

/**
 * Created by root on 4/12/17.
 */

@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;


    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }


    public Context getmContext() {
        return mContext;
    }


    public PreferencesHelper getmPreferencesHelper() {
        return mPreferencesHelper;
    }

    @Override
    public rx.Observable<Envelope> doAuthorizeOnServer(RequestEnvelope requestEnvelope) {
        return mApiHelper.doAuthorizeOnServer(requestEnvelope);
    }

    @Override
    public Observable<kazpost.kz.mobterminal.data.network.model.findplan.Envelope> doFindPlan(FindPlanEnvelope findPlanEnvelope) {
        return mApiHelper.doFindPlan(findPlanEnvelope);
    }

    @Override
    public Observable<kazpost.kz.mobterminal.data.network.model.parcel.Envelope> doParcelToBag(ParcelEnvelope parcelEnvelope) {
        return mApiHelper.doParcelToBag(parcelEnvelope);
    }

    @Override
    public Observable<kazpost.kz.mobterminal.data.network.model.closebag.Envelope> doCloseBag(CloseBagEnvelope closeBagEnvelope) {
        return mApiHelper.doCloseBag(closeBagEnvelope);
    }

    @Override
    public void saveSessionId(String sessionId) {
        mPreferencesHelper.saveSessionId(sessionId);
    }

    @Override
    public void saveLastLoginTime(String dateTime) {
        mPreferencesHelper.saveLastLoginTime(dateTime);
    }

    @Override
    public String getSessionId() {
        return mPreferencesHelper.getSessionId();
    }

    @Override
    public String getLastLoginTime() {
        return mPreferencesHelper.getLastLoginTime();
    }
}
