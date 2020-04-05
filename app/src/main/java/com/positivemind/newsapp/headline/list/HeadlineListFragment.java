package com.positivemind.newsapp.headline.list;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.positivemind.newsapp.NewsApplication;
import com.positivemind.newsapp.R;
import com.positivemind.newsapp.base.BaseFragment;
import com.positivemind.newsapp.data.remote.HeadlineRemoteService;
import com.positivemind.newsapp.databinding.FragmentHeadlineListBinding;
import com.positivemind.newsapp.db.headline.HeadlineDataSource;
import com.positivemind.newsapp.headline.HeadlineListItemListener;
import com.positivemind.newsapp.headline.HeadlineModel;
import com.positivemind.newsapp.utils.Utils;
import com.positivemind.newsapp.utils.exception.AppException;
import com.positivemind.newsapp.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.AndroidSupportInjectionModule;


public class HeadlineListFragment extends BaseFragment<FragmentHeadlineListBinding>
        implements HeadlineListContract.View,
        HeadlineListItemListener {

    //----------------------------------------------------------------------------------------------
    // Class Variables
    //----------------------------------------------------------------------------------------------
    private OnFragmentInteractionListener mListener;
    private HeadlineRecyclerViewAdapter mHeadLineRecyclerViewAdapter;

    @Inject
    HeadlineListPresenter headlineListPresenter;

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // set toolbar title
        getViewDataBinding().toolbarHeadline.setTitle(getResources().getString(R.string.title_headlines));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAdapter();
        initListener();

    }

    @Override
    public void onResume() {
        super.onResume();
        // fetch headlines
        fetchHeadlines();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        headlineListPresenter.onStop();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //----------------------------------------------------------------------------------------------
    // Base Implemented methods
    //----------------------------------------------------------------------------------------------

    @Override
    public int getLayoutId() {
        return R.layout.fragment_headline_list;
    }


    public static HeadlineListFragment newInstance() {
        HeadlineListFragment fragment = new HeadlineListFragment();
        return fragment;
    }


    //----------------------------------------------------------------------------------------------
    // Contract.View implemented methods
    //----------------------------------------------------------------------------------------------
    @Override
    public void displayHeadlinesFromRemote(List<HeadlineModel> headlineModelList) {
        displayHeadlines(headlineModelList);
        // save in DB
        headlineListPresenter.saveNewsInDB(headlineModelList);
    }

    @Override
    public void displayHeadlinesFromDB(List<HeadlineModel> headlineModelList) {
        if (headlineModelList != null && headlineModelList.size() > 0)
            displayHeadlines(headlineModelList);
        else {
            // No articles saved in DB
            // show no connection view to request connection
            getViewDataBinding().viewNoConnection.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onErrorFetchTopHeadlines(AppException appException) {
        showSnackBar(appException.getErrorMessage(),false);
    }

    @Override
    public void showNoConnectionError() {
        getViewDataBinding().viewNoConnection.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressBar() {
        getViewDataBinding().viewProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        getViewDataBinding().viewProgress.setVisibility(View.GONE);
    }



    //----------------------------------------------------------------------------------------------
    // Private methods
    //----------------------------------------------------------------------------------------------
    private void initAdapter() {
        RecyclerView recyclerView = getViewDataBinding().rvHeadline;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mHeadLineRecyclerViewAdapter = new HeadlineRecyclerViewAdapter(this);
        recyclerView.setAdapter(mHeadLineRecyclerViewAdapter);
    }

    private void initListener() {
        getViewDataBinding().viewNoConnection.setOnClickListener(v -> {
                    getViewDataBinding().viewNoConnection.setVisibility(View.GONE);
                    fetchHeadlines();
                }
        );
    }


    private void fetchHeadlines() {
        // show shimmer
        getViewDataBinding().shimmerHeadlineList.setVisibility(View.VISIBLE);
        getViewDataBinding().shimmerHeadlineList.startShimmer();
        // check connection
        boolean isConnected = Utils.isDeviceHasInternetConnection(getContext());
        if (isConnected)
            // load data
            headlineListPresenter.fetchTopHeadlinesFromRemote();
        else
            headlineListPresenter.fetchTopHeadlinesFromDB();
    }

    private void displayHeadlines(List<HeadlineModel> headlineModelList) {
        // stop shimmer animation
        getViewDataBinding().shimmerHeadlineList.stopShimmer();
        // hide shimmer View
        getViewDataBinding().shimmerHeadlineList.setVisibility(View.GONE);
        if (headlineModelList.size() > 0) {
            // set data on adapter
            mHeadLineRecyclerViewAdapter.updateData(headlineModelList);
        } else {
            String noDataMessage = getResources().getString(R.string.data_not_found);
            showSnackBar(noDataMessage, false);
        }
    }


    public interface OnFragmentInteractionListener {
        void onTapHeadline(HeadlineModel headlineModel);
    }


    //----------------------------------------------------------------------------------------------
    // HeadlineListItemListener implemented methods
    //----------------------------------------------------------------------------------------------
    @Override
    public void onClickHeadlineListItem(HeadlineModel headlineModel) {
        // open detail fragment
        if (mListener != null)
            mListener.onTapHeadline(headlineModel);
    }
}
