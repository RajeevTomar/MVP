package com.positivemind.newsapp;

import android.app.Application;


import androidx.fragment.app.Fragment;

import com.positivemind.newsapp.di.AppComponent;
import com.positivemind.newsapp.di.AppModule;
import com.positivemind.newsapp.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by Rajeev Tomar on 21,December,2019
 */
public class NewsApplication extends Application implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        // init app component
        DaggerAppComponent.builder().application(this).build().inject(this);
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
