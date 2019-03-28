/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.example.uidatabinding.databinding.core;

import android.databinding.BaseObservable;

public abstract class AbsModel<S extends AbsStore> extends BaseObservable {

    public S store;

    public void setAbsPresenter(S store) {
        this.store = store;
    }

}
