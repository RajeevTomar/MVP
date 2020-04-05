package com.positivemind.newsapp.di;

import android.content.Context;

import com.positivemind.newsapp.NewsApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    Context provideContext(NewsApplication application) {
        return application.getApplicationContext();
    }
}
