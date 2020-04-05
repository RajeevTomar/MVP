package com.positivemind.newsapp.di;

import com.positivemind.newsapp.data.remote.HeadlineRemoteService;
import com.positivemind.newsapp.db.headline.HeadlineDataSource;
import com.positivemind.newsapp.headline.list.HeadlineListContract;
import com.positivemind.newsapp.headline.list.HeadlineListPresenter;
import com.positivemind.newsapp.utils.rx.AppSchedulerProvider;
import com.positivemind.newsapp.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rajeev Tomar on 03,April,2020
 */
@Module
public class PresenterModule {


    @Provides
    public SchedulerProvider provideScheduler()
    {
        return new AppSchedulerProvider();
    }

    @Provides
    HeadlineListPresenter provideHeadlineListPresenter(HeadlineListContract.View view,
                                                       SchedulerProvider schedulerProvider,
                                                       HeadlineDataSource headlineDataSource,
                                                       HeadlineRemoteService headlineRemoteService) {
        return new HeadlineListPresenter(view, schedulerProvider, headlineRemoteService, headlineDataSource);

    }
}
