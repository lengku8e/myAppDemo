package com.example.uimvvm.manage;

import android.view.View;
import android.view.ViewGroup;

import com.example.uimvvm.UIComponent;

/**
 * 保存 UIComponent 的中间状态
 */
class UIComponentDelegate {

    private final UIComponent host;
    private final ViewGroup container;
    private LifecycleState lifecycle;
    private View view;
    private boolean enable;

    UIComponentDelegate(UIComponent host, ViewGroup container) {
        this.host = host;
        this.container = container;
        this.lifecycle = LifecycleState.NONE;
        this.enable = true;
    }

    UIComponent getHost() {
        return host;
    }

    ViewGroup getContainer() {
        return container;
    }

    LifecycleState getLifecycle() {
        return lifecycle;
    }

    void setLifecycle(LifecycleState lifecycle) {
        this.lifecycle = lifecycle;
    }

    View getView() {
        return view;
    }

    void setView(View view) {
        this.view = view;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
