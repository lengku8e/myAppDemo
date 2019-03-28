/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.example.uidatabinding.databinding.widget;

import android.databinding.BaseObservable;

/**
 * Created by wangtianya on 2018/4/20.
 */

public abstract class BindingAdapterItemModel extends BaseObservable {
    public int layoutId;
    public int variableId;

    public void setResId(int layoutId, int variableId) {
        this.layoutId = layoutId;
        this.variableId = variableId;
    }
}
