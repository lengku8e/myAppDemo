/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.example.uimvvm.mvvm;

import android.databinding.BaseObservable;

public abstract class MVVMModel<C extends MVVMComponent> extends BaseObservable {

    public C component;

    public void setComponent(C component) {
        this.component = component;
    }
}
