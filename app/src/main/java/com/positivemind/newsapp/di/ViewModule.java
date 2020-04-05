package com.positivemind.newsapp.di;

import androidx.fragment.app.Fragment;

import com.positivemind.newsapp.headline.list.HeadlineListContract;
import com.positivemind.newsapp.headline.list.HeadlineListFragment;

import dagger.Binds;
import dagger.Module;

/**
 * Created by Rajeev Tomar on 03,April,2020
 */
@Module
public abstract class ViewModule {

    @Binds
    abstract HeadlineListContract.View provideHeadlineListView(HeadlineListFragment
                                                                       headlineListFragment);

}
