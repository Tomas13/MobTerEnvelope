package kazpost.kz.mobterminal.data.network;

import kazpost.kz.mobterminal.data.network.model.Envelope;
import kazpost.kz.mobterminal.data.network.model.closebag.CloseBagEnvelope;
import kazpost.kz.mobterminal.data.network.model.findplan.FindPlanEnvelope;
import kazpost.kz.mobterminal.data.network.model.parcel.ParcelEnvelope;
import kazpost.kz.mobterminal.data.network.model.request.RequestEnvelope;
import rx.Observable;

/**
 * Created by root on 4/12/17.
 */

public interface ApiHelper {

    Observable<Envelope> doAuthorizeOnServer(RequestEnvelope requestEnvelope);

    Observable<kazpost.kz.mobterminal.data.network.model.findplan.Envelope> doFindPlan(FindPlanEnvelope findPlanEnvelope);

    Observable<kazpost.kz.mobterminal.data.network.model.parcel.Envelope> doParcelToBag(ParcelEnvelope parcelEnvelope);

    Observable<kazpost.kz.mobterminal.data.network.model.closebag.Envelope> doCloseBag(CloseBagEnvelope closeBagEnvelope);

}
