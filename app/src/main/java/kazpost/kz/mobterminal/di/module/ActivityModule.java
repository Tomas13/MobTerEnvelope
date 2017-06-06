/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package kazpost.kz.mobterminal.di.module;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import kazpost.kz.mobterminal.di.ActivityContext;
import kazpost.kz.mobterminal.di.PerActivity;
import kazpost.kz.mobterminal.ui.closecell.CloseCellMvpPresenter;
import kazpost.kz.mobterminal.ui.closecell.CloseCellMvpView;
import kazpost.kz.mobterminal.ui.closecell.CloseCellPresenter;
import kazpost.kz.mobterminal.ui.login.LoginMvpPresenter;
import kazpost.kz.mobterminal.ui.login.LoginMvpView;
import kazpost.kz.mobterminal.ui.login.LoginPresenter;
import kazpost.kz.mobterminal.ui.main.MainMvpPresenter;
import kazpost.kz.mobterminal.ui.main.MainMvpView;
import kazpost.kz.mobterminal.ui.main.MainPresenter;
import kazpost.kz.mobterminal.ui.scan.ScanMvpPresenter;
import kazpost.kz.mobterminal.ui.scan.ScanMvpView;
import kazpost.kz.mobterminal.ui.scan.ScanPresenter;

/**
 * Created by janisharali on 27/01/17.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }


    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(LoginPresenter<LoginMvpView>
                                                                  presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView>
                                                               presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    CloseCellMvpPresenter<CloseCellMvpView> provideCloseCellPresenter(CloseCellPresenter<CloseCellMvpView>
                                                                              presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ScanMvpPresenter<ScanMvpView> provideScanPresenter(ScanPresenter<ScanMvpView> presenter) {
        return presenter;
    }
}
