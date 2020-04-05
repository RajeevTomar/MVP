

package com.positivemind.newsapp.base;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;


import com.positivemind.newsapp.R;
import com.positivemind.newsapp.utils.exception.AppException;
import com.google.android.material.snackbar.Snackbar;


public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {

    private BaseActivity mActivity;
    private T mViewDataBinding;
    private View mRootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mRootView = mViewDataBinding.getRoot();
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(mViewDataBinding == null)
        {
            Log.d("BaseFragment","BaseFragment OnCreateView is not getting called");
            return;
        }
        mViewDataBinding.executePendingBindings();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        // clear the stuff
        super.onDestroy();
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }


    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }


    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }


    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();




    protected void showSnackBar(String message, boolean success) {
        Snackbar snackbar = getSnackBar(message, success);
        snackbar.show();
    }


    private Snackbar getSnackBar(String message, boolean success) {
        Snackbar snackbar = Snackbar.make(getViewDataBinding().getRoot(), message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        int color = getResources().getColor(R.color.colorPrimary);
        if (!success)
            color = getResources().getColor(R.color.colorAccent);
        snackBarView.setBackgroundColor(color);
        return snackbar;
    }

}
