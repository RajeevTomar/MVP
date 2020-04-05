
package com.positivemind.newsapp.base;


import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;


import com.positivemind.newsapp.utils.exception.AppException;
import com.positivemind.newsapp.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<V> extends BaseObservable {


    private final SchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;
    private final MutableLiveData<AppException> appExceptionEvent = new MutableLiveData<>();

    protected final V mView;


    public BasePresenter(SchedulerProvider schedulerProvider,  V view) {
        this.mView = view;
        this.mSchedulerProvider = schedulerProvider;
        mCompositeDisposable = new CompositeDisposable();
    }


    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public MutableLiveData<AppException> getAppExceptionEvent()
    {
        return appExceptionEvent;
    }

    protected void onCleared() {
        mCompositeDisposable.dispose();
    }

    protected void handleError(Throwable error)
    {
        if(error == null)
            return;
        appExceptionEvent.setValue(new AppException(error.getMessage()));
    }
}
