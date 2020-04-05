package com.positivemind.newsapp;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.positivemind.newsapp.data.local.HeadlineLocalDataSource;
import com.positivemind.newsapp.data.remote.HeadlineRemoteService;
import com.positivemind.newsapp.headline.HeadlineModel;
import com.positivemind.newsapp.server.BaseResponse;
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
public class HeadlineListViewModelTest {


    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    SchedulerProvider schedulerProvider;
    @Mock
    HeadlineRemoteService headlineRemoteService;
    @Mock
    Observer<List<HeadlineModel>> remoteHeadlineObserver;

    @Mock
    Observer<List<HeadlineModel>> savedHeadlineObserver;

    @Mock
    Observer<Throwable> exceptionObserver;

    @Mock
    HeadlineLocalDataSource headlineLocalDataSource;

    private HeadlineListViewModel headlineListViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        schedulerProvider = new AppSchedulerProvider();
        headlineListViewModel = new HeadlineListViewModel(schedulerProvider,
                headlineRemoteService, headlineLocalDataSource);
        // set observer
        headlineListViewModel.getHeadlineListMutableLiveData().observeForever(remoteHeadlineObserver);
        headlineListViewModel.getThrowableMutableLiveData().observeForever(exceptionObserver);
        headlineListViewModel.getSavedHeadlinesMutableLiveData().observeForever(savedHeadlineObserver);
    }


    @Test
    public void testNull() {
        when(headlineRemoteService.getTopHeadlines("in")).thenReturn(null);
        assertNotNull(headlineListViewModel.getHeadlineListMutableLiveData());
        assertTrue(headlineListViewModel.getHeadlineListMutableLiveData().hasObservers());
    }


    @Test
    public void testApiFetchDataSuccess() {
        // Mock API response
        BaseResponse<List<HeadlineModel>> baseResponse = new BaseResponse<>();
        List<HeadlineModel> headlineModels = new ArrayList<>();
        headlineModels.add(new HeadlineModel());
        baseResponse.setArticles(headlineModels);
        when(headlineRemoteService.getTopHeadlines("in")).thenReturn(Single.just(baseResponse));
        headlineListViewModel.fetchTopHeadlinesFromRemote();
        verify(remoteHeadlineObserver).onChanged(headlineModels);
    }


    @Test
    public void testDBFetchDataSuccess() {
        // Mock API response
        List<HeadlineModel> headlineModels = new ArrayList<>();
        headlineModels.add(new HeadlineModel());
        when(headlineLocalDataSource.getAll()).thenReturn(Observable.just(headlineModels));
        headlineListViewModel.fetchHeadlinesFromDB();
        verify(savedHeadlineObserver).onChanged(headlineModels);
    }


    @Test
    public void testApiFetchDataError() {
        Throwable throwable = new Throwable("Error");
        when(headlineRemoteService.getTopHeadlines("in")).
                thenReturn(Single.error(throwable));
        headlineListViewModel.fetchTopHeadlinesFromRemote();
        verify(exceptionObserver).onChanged(throwable);
    }


    @After
    public void tearDown() throws Exception {
        headlineRemoteService = null;
        headlineLocalDataSource = null;
        headlineListViewModel = null;
    }

}
