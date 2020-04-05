package com.positivemind.newsapp.di;

import com.positivemind.newsapp.headline.list.HeadlineListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Rajeev Tomar on 03,April,2020
 */
@Module
public abstract class BuildersModule {

        @ContributesAndroidInjector(modules = {ViewModule.class,PresenterModule.class})
        abstract HeadlineListFragment bindHeadlineListFragment();

}
