/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.example.uimvvm.mvvm;

import android.arch.lifecycle.LifecycleObserver;

public abstract class MVVMPresenter<C extends MVVMComponent> implements LifecycleObserver {

    public C component;

    public void setComponent(C component) {
        this.component = component;
    }

}
