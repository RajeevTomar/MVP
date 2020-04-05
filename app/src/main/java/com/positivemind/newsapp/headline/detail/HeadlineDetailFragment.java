package com.positivemind.newsapp.headline.detail;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.positivemind.newsapp.NewsApplication;
import com.positivemind.newsapp.R;
import com.positivemind.newsapp.base.BaseFragment;
import com.positivemind.newsapp.databinding.FragmentHeadlineDetailBinding;
import com.positivemind.newsapp.headline.HeadlineModel;

import javax.inject.Inject;

/**
 * Created by Rajeev Tomar on 22,December,2019
 */
public class HeadlineDetailFragment extends BaseFragment<FragmentHeadlineDetailBinding> {

    private static final String ARG_HEADLINE_MODEL = "arg_headline";

    //----------------------------------------------------------------------------------------------
    // Class variables
    //----------------------------------------------------------------------------------------------
    private HeadlineModel mHeadlineModel;
    private OnFragmentInteractionListener mListener;


    public static HeadlineDetailFragment getInstance(HeadlineModel headlineModel) {
        HeadlineDetailFragment headlineDetailFragment = new HeadlineDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_HEADLINE_MODEL, headlineModel);
        headlineDetailFragment.setArguments(bundle);
        return headlineDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //inject into DI
        //NewsApplication.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        // fetch bundle
        receivedBundle();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
        // display data
        displayData();
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
        return R.layout.fragment_headline_detail;
    }


    //----------------------------------------------------------------------------------------------
    // Private methods
    //----------------------------------------------------------------------------------------------
    private void initListener() {
        getViewDataBinding().ivBack.setOnClickListener(v ->
                {
                    if (mListener != null)
                        mListener.onTabBackButton();
                }
        );
    }

    private void receivedBundle() {
        if (getArguments() != null) {
            mHeadlineModel = getArguments().getParcelable(ARG_HEADLINE_MODEL);
        }
    }

    private void displayData() {
        if (mHeadlineModel == null)
            return;
        // image
        String thumbnailUrl = mHeadlineModel.getUrlToImage();
        if (!TextUtils.isEmpty(thumbnailUrl)) {
            Glide.with(getViewDataBinding().ivArticleDetail).
                    load(thumbnailUrl).
                    into(getViewDataBinding().ivArticleDetail);

        }
        // title
        String title = mHeadlineModel.getTitle();
        if (!TextUtils.isEmpty(title))
            getViewDataBinding().title.setText(title);
        //source
        String source = mHeadlineModel.getSource().getName();
        if (!TextUtils.isEmpty(source))
            getViewDataBinding().tvSource.setText(source);
        //publish date
        String date = mHeadlineModel.getPublishedDate();
        if (!TextUtils.isEmpty(date))
            getViewDataBinding().tvPublishedDate.setText(date);
        String description = mHeadlineModel.getDescription();
        if (!TextUtils.isEmpty(description))
            getViewDataBinding().description.setText(description);


    }



    //----------------------------------------------------------------------------------------------
    // Interaction  interface
    //----------------------------------------------------------------------------------------------
    public interface OnFragmentInteractionListener {
        void onTabBackButton();
    }


}


