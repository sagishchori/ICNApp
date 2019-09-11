package com.sagishchori.icnapp.viewmodels;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

public abstract class AbstractViewModel<T> extends ViewModel {

    protected Context context;
    protected MutableLiveData<T> data;
    protected MutableLiveData<T> logic;
    protected LifecycleOwner owner;


    public abstract void init(Context context, LifecycleOwner owner);

    public abstract T getLogic();
}
