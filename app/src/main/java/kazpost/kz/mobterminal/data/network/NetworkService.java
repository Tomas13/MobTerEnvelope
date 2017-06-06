package kazpost.kz.mobterminal.data.network;

import kazpost.kz.mobterminal.data.network.model.Envelope;
import kazpost.kz.mobterminal.data.network.model.closebag.CloseBagEnvelope;
import kazpost.kz.mobterminal.data.network.model.findplan.FindPlanEnvelope;
import kazpost.kz.mobterminal.data.network.model.parcel.ParcelEnvelope;
import kazpost.kz.mobterminal.data.network.model.request.RequestEnvelope;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by root on 4/18/17.
 */

public interface NetworkService {

    @POST("mobiterminal/Terminal.wsdl")
    @Headers("Content-Type: text/xml")
    Observable<Envelope> requestStateInfoObs(@Body RequestEnvelope requestEnvelope);


    @POST("mobiterminal/Terminal.wsdl")
    @Headers("Content-Type: text/xml")
    Observable<kazpost.kz.mobterminal.data.network.model.findplan.Envelope> findPlanRequest(@Body FindPlanEnvelope findPlanEnvelope);

    @POST("mobiterminal/Terminal.wsdl")
    @Headers("Content-Type: text/xml")
    Observable<kazpost.kz.mobterminal.data.network.model.parcel.Envelope> parcelToBagRequest(@Body ParcelEnvelope parcelEnvelope);

    @POST("mobiterminal/Terminal.wsdl")
    @Headers("Content-Type: text/xml")
    Observable<kazpost.kz.mobterminal.data.network.model.closebag.Envelope> closeBagRequest(@Body CloseBagEnvelope closeBagEnvelope);

}
