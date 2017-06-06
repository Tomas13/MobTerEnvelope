package kazpost.kz.mobterminal.data.network;

import javax.inject.Inject;
import javax.inject.Singleton;

import kazpost.kz.mobterminal.data.network.model.Envelope;
import kazpost.kz.mobterminal.data.network.model.closebag.CloseBagEnvelope;
import kazpost.kz.mobterminal.data.network.model.findplan.FindPlanEnvelope;
import kazpost.kz.mobterminal.data.network.model.parcel.ParcelEnvelope;
import kazpost.kz.mobterminal.data.network.model.request.RequestEnvelope;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by root on 4/12/17.
 */

@Singleton
public class AppApiHelper implements ApiHelper {

    @Inject
    NetworkService networkService;

    @Override
    public Observable<Envelope> doAuthorizeOnServer(RequestEnvelope requestEnvelope) {
        return networkService.requestStateInfoObs(requestEnvelope);
    }

    @Override
    public Observable<kazpost.kz.mobterminal.data.network.model.findplan.Envelope> doFindPlan(FindPlanEnvelope findPlanEnvelope) {
        return networkService.findPlanRequest(findPlanEnvelope);
    }

    @Override
    public Observable<kazpost.kz.mobterminal.data.network.model.parcel.Envelope> doParcelToBag(ParcelEnvelope parcelEnvelope) {
        return networkService.parcelToBagRequest(parcelEnvelope);
    }

    @Override
    public Observable<kazpost.kz.mobterminal.data.network.model.closebag.Envelope> doCloseBag(CloseBagEnvelope closeBagEnvelope) {
        return networkService.closeBagRequest(closeBagEnvelope);
    }

    @Inject
    public AppApiHelper() {
    }
    //    @Inject
//    public AppApiHelper(ApiHeader apiHeader) {
//        mApiHeader = apiHeader;
//    }
}
