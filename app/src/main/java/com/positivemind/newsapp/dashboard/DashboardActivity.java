package com.positivemind.newsapp.dashboard;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.WindowManager;

import com.positivemind.newsapp.R;
import com.positivemind.newsapp.base.BaseActivity;
import com.positivemind.newsapp.headline.HeadlineModel;
import com.positivemind.newsapp.headline.detail.HeadlineDetailFragment;
import com.positivemind.newsapp.headline.list.HeadlineListFragment;
import com.positivemind.newsapp.utils.ActivityUtils;

public class DashboardActivity extends BaseActivity implements
        HeadlineListFragment.OnFragmentInteractionListener,
        HeadlineDetailFragment.OnFragmentInteractionListener {

    private static final String HEADLINE_DETAIL_TAG = "headline_detail_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // FullScreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        addHeadlineFragment();
    }


    //----------------------------------------------------------------------------------------------
    // Private methods
    //----------------------------------------------------------------------------------------------
    private void addHeadlineFragment() {
        Fragment popularFragment = obtainHeadlineFragment();
        ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), popularFragment,
                R.id.container_dashboard);
    }

    private Fragment obtainHeadlineFragment() {
        Fragment headlineFragment = getSupportFragmentManager().
                findFragmentById(R.id.container_dashboard);
        if (headlineFragment == null)
            headlineFragment = HeadlineListFragment.newInstance();
        return headlineFragment;
    }

    //----------------------------------------------------------------------------------------------
    // Callback methods from HeadlineList fragment
    //----------------------------------------------------------------------------------------------
    @Override
    public void onTapHeadline(HeadlineModel headlineModel) {
        if(headlineModel == null)
            return;
        // add detail fragment
        HeadlineDetailFragment headlineDetailFragment = HeadlineDetailFragment.getInstance(headlineModel);
        ActivityUtils.addFragmentInActivity(getSupportFragmentManager(), headlineDetailFragment,
                R.id.container_dashboard,HEADLINE_DETAIL_TAG);
    }


    //----------------------------------------------------------------------------------------------
    // Callback methods from HeadlineDetail fragment
    //----------------------------------------------------------------------------------------------
    @Override
    public void onTabBackButton() {
        // pop the Headline Detail Fragment
        getSupportFragmentManager().popBackStack();
    }
}

