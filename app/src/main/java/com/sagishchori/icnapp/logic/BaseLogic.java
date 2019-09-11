package com.sagishchori.icnapp.logic;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

public abstract class BaseLogic<T extends ViewModel> {

    protected Context context;
    protected T viewModel;
    protected LifecycleOwner owner;

    public void init(Context context, T viewModel, LifecycleOwner owner) {
        this.context = context;
        this.viewModel = viewModel;
        this.owner = owner;
    }

    protected abstract T getViewModel();

    public abstract boolean isLogicInstantiated();
}
