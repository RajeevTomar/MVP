package com.positivemind.newsapp.di;



import com.positivemind.newsapp.data.remote.HeadlineRemoteService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class RemoteServiceModule {

    @Provides
    public HeadlineRemoteService provideHeadlineRemoteService(Retrofit retrofit)
    {
        return retrofit.create(HeadlineRemoteService.class);
    }

}
