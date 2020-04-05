package com.positivemind.newsapp.headline.list;

import com.positivemind.newsapp.base.IBasePresenter;
import com.positivemind.newsapp.base.IBaseView;
import com.positivemind.newsapp.headline.HeadlineModel;
import com.positivemind.newsapp.utils.exception.AppException;

import java.util.List;

/**
 * Created by Rajeev Tomar on 02,April,2020
 */
public interface HeadlineListContract {

    interface View extends IBaseView{
        // Remote
        void displayHeadlinesFromRemote(List<HeadlineModel> headlineModelList);
        void displayHeadlinesFromDB(List<HeadlineModel> headlineModelList);
        void onErrorFetchTopHeadlines(AppException appException);
        void showNoConnectionError();
    }

    interface Presenter extends IBasePresenter {

       void fetchTopHeadlinesFromRemote();
       void fetchTopHeadlinesFromDB();
        void saveNewsInDB(List<HeadlineModel> headlineModelList);
    }
}
