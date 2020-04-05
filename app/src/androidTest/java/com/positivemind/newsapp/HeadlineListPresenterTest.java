package com.positivemind.newsapp;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.positivemind.newsapp.data.local.HeadlineLocalDataSource;
import com.positivemind.newsapp.data.remote.HeadlineRemoteService;
import com.positivemind.newsapp.headline.HeadlineModel;
import com.positivemind.newsapp.headline.list.HeadlineListContract;
import com.positivemind.newsapp.headline.list.HeadlineListPresenter;
import com.positivemind.newsapp.server.BaseResponse;
import com.positivemind.newsapp.utils.exception.AppException;
import com.positivemind.newsapp.utils.rx.AppSchedulerProvider;
import com.positivemind.newsapp.utils.rx.SchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by Rajeev Tomar on 22,December,2019
 */
@RunWith(JUnit4.class)
public class HeadlineListPresenterTest {


    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    HeadlineListContract.View headListView;

    SchedulerProvider schedulerProvider;

    @Mock
    HeadlineRemoteService headlineRemoteService;

    @Mock
    HeadlineLocalDataSource headlineLocalDataSource;

    private HeadlineListContract.Presenter headlineListPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        schedulerProvider = new AppSchedulerProvider();
        headlineListPresenter = new HeadlineListPresenter(headListView,schedulerProvider,
                headlineRemoteService, headlineLocalDataSource);
    }



    @Test
    public void testApiFetchDataSuccess() {
        // Mock API response
        BaseResponse<List<HeadlineModel>> baseResponse = new BaseResponse<>();
        List<HeadlineModel> headlineModels = new ArrayList<>();
        headlineModels.add(new HeadlineModel());
        baseResponse.setArticles(headlineModels);
        when(headlineRemoteService.getTopHeadlines("in")).thenReturn(Single.just(baseResponse));
        headlineListPresenter.fetchTopHeadlinesFromRemote();
        verify(headListView).displayHeadlinesFromRemote(headlineModels);
    }


    @Test
    public void testDBFetchDataSuccess() {
        // Mock API response
        List<HeadlineModel> headlineModels = new ArrayList<>();
        headlineModels.add(new HeadlineModel());
        when(headlineLocalDataSource.getAll()).thenReturn(Observable.just(headlineModels));
        headlineListPresenter.fetchTopHeadlinesFromDB();

        verify(headListView).displayHeadlinesFromDB(headlineModels);
    }


    @Test
    public void testApiFetchDataError() {
        Throwable throwable = new Throwable("Error");
        when(headlineRemoteService.getTopHeadlines("in")).
                thenReturn(Single.error(throwable));
        headlineListPresenter.fetchTopHeadlinesFromRemote();
        verify(headListView).onErrorFetchTopHeadlines(new AppException(throwable.getMessage()));
    }


    @After
    public void tearDown() throws Exception {
        headlineRemoteService = null;
        headlineLocalDataSource = null;
        headlineListPresenter = null;
    }

}
