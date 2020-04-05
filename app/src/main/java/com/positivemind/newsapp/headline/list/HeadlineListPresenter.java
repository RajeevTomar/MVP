package com.positivemind.newsapp.headline.list;

import com.positivemind.newsapp.R;
import com.positivemind.newsapp.base.BasePresenter;
import com.positivemind.newsapp.data.Constants;
import com.positivemind.newsapp.data.remote.HeadlineRemoteService;
import com.positivemind.newsapp.db.headline.HeadlineDataSource;
import com.positivemind.newsapp.headline.HeadlineModel;
import com.positivemind.newsapp.utils.exception.AppException;
import com.positivemind.newsapp.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Rajeev Tomar on 02,April,2020
 */
public class HeadlineListPresenter extends BasePresenter<HeadlineListContract.View>
        implements HeadlineListContract.Presenter {

    // ---------------------------------------------------------------------------------------------
    // Variables
    // ---------------------------------------------------------------------------------------------
    private HeadlineRemoteService headlineRemoteService;
    private HeadlineDataSource headlineDataSource;


    // ---------------------------------------------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------------------------------------------
    public HeadlineListPresenter(HeadlineListContract.View view,
                                 SchedulerProvider schedulerProvider,
                                 HeadlineRemoteService headlineRemoteService,
                                 HeadlineDataSource headlineDataSource) {
        super(schedulerProvider,view);
        this.headlineRemoteService = headlineRemoteService;
        this.headlineDataSource = headlineDataSource;
    }



    // ---------------------------------------------------------------------------------------------
    // HeadlineListContract.Presenter implemented methods
    // ---------------------------------------------------------------------------------------------
    @Override
    public void onStop() {
        onCleared();
    }

    @Override
    public void fetchTopHeadlinesFromRemote() {
        getCompositeDisposable().add(headlineRemoteService.getTopHeadlines(Constants.INDIA_COUNTRY_CODE).
                subscribeOn(getSchedulerProvider().io()).
                observeOn(getSchedulerProvider().ui()).
                subscribe(
                        headlineDataResponse -> {
                            if (headlineDataResponse != null) {
                                List<HeadlineModel> headlineModels = headlineDataResponse.getArticles();
                                mView.displayHeadlinesFromRemote(headlineModels);
                            }
                        }, throwable -> {
                            mView.onErrorFetchTopHeadlines(new AppException(throwable.getMessage()));
                        }
                ));
    }

    @Override
    public void fetchTopHeadlinesFromDB() {
        getCompositeDisposable().add(headlineDataSource.getAll().
                subscribeOn(getSchedulerProvider().io()).
                observeOn(getSchedulerProvider().ui()).
                subscribe(headlineModels -> {
                    mView.displayHeadlinesFromDB(headlineModels);
                }, throwable -> {
                    mView.onErrorFetchTopHeadlines(new AppException(throwable.getMessage()));
                }));
    }

    @Override
    public void saveNewsInDB(List<HeadlineModel> headlineModelList) {
        mView.showProgressBar();
        getCompositeDisposable().add(Observable.fromCallable(() -> {
            // delete all news
            headlineDataSource.deleteAll();
            // insert new news
            headlineDataSource.insertAll(headlineModelList);
            return true;
        }).
                subscribeOn(getSchedulerProvider().io()).
                observeOn(getSchedulerProvider().ui()).
                subscribe(result -> {
                    mView.hideProgressBar();
                }, throwable -> {
                    mView.hideProgressBar();
                    mView.onErrorFetchTopHeadlines(new AppException(throwable.getMessage()));
                }));
    }
}
