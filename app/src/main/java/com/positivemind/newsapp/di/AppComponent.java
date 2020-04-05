package com.positivemind.newsapp.di;



import com.positivemind.newsapp.NewsApplication;
import com.positivemind.newsapp.headline.detail.HeadlineDetailFragment;
import com.positivemind.newsapp.headline.list.HeadlineListFragment;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class,AppModule.class, RetrofitModule.class,
        RemoteServiceModule.class, DBServiceModule.class,
        BuildersModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(NewsApplication application);
        AppComponent build();
    }
    void inject(NewsApplication app);

}
