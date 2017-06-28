package kazpost.kz.mobterminal.ui.print;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by root on 12/26/16.
 */

public class Singleton {

    public static OkHttpClient getUserClient(String gnumber,
                                             String operatorname,
                                             String sealnumber,
                                             String deliverytype,
                                             String date,
                                             String weightkg,
                                             String weightgr,
                                             String fromdep,
                                             String todep,
                                             String deliverydoc, String printerip,
                                             String printername) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addNetworkInterceptor(new StethoInterceptor()); //подключаю Stetho
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("gnumber", URLEncoder.encode(gnumber, "UTF-8"))
                    .header("operatorname", URLEncoder.encode(operatorname, "UTF-8"))
                    .header("sealnumber", URLEncoder.encode(sealnumber, "UTF-8"))
                    .header("deliverytype", URLEncoder.encode(deliverytype, "UTF-8"))
                    .header("gdate", URLEncoder.encode(date, "UTF-8"))
                    .header("fromdep", URLEncoder.encode(fromdep, "UTF-8"))
                    .header("todep", URLEncoder.encode(todep, "UTF-8"))
                    .header("weightkg", URLEncoder.encode(weightkg, "UTF-8"))
                    .header("weightgr", URLEncoder.encode(weightgr, "UTF-8"))
                    .header("deliverydoc", URLEncoder.encode(deliverydoc, "UTF-8"))
                    .header("printerip", URLEncoder.encode(printerip, "UTF-8"))
                    .header("printername", URLEncoder.encode(printername, "UTF-8"))
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });

        return httpClient.build();
    }


    public static OkHttpClient getUserClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addNetworkInterceptor(new StethoInterceptor()); //подключаю Stetho
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
       /* httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
//                    .header("Authorization", credentials) //добавляю хедер
//                    .header("grant_type", "password")
//                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });*/

        return httpClient.build();
    }


}
