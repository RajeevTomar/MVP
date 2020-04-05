package com.positivemind.newsapp.di;

import android.content.Context;


import com.positivemind.newsapp.data.local.HeadlineLocalDataSource;
import com.positivemind.newsapp.db.AppDatabase;
import com.positivemind.newsapp.db.headline.HeadlineDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class DBServiceModule {

    @Provides
    @Singleton
    public AppDatabase provideDatabase(Context context)
    {
        return AppDatabase.getInstance(context);
    }

    @Provides
    @Singleton
    public HeadlineDataSource provideHeadlineDataSource(AppDatabase appDatabase)
    {
        return new HeadlineLocalDataSource(appDatabase.headlineDao());
    }
}
