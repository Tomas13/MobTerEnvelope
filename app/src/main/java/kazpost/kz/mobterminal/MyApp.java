package kazpost.kz.mobterminal;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import kazpost.kz.mobterminal.data.DataManager;
import kazpost.kz.mobterminal.di.component.ApplicationComponent;
import kazpost.kz.mobterminal.di.component.DaggerApplicationComponent;
import kazpost.kz.mobterminal.di.module.ApplicationModule;

/**
 * Created by root on 4/11/17.
 */

public class MyApp extends Application{


    @Inject
    DataManager mDataManager;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);

        Stetho.initializeWithDefaults(this);
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    public static MyApp get(Context context) {
        return (MyApp) context.getApplicationContext();
    }
}
