package com.example.uimvvm.manage;

import android.view.View;
import android.view.ViewGroup;

import java.util.HashSet;

/**
 * 维护component view正确挂载
 */
class ViewManager {

    private HashSet<UIComponentDelegate> pendingList = new HashSet<>();

    void mountComponent(final UIComponentDelegate delegate) throws IllegalTransformError {
        if (delegate.getView() == null) {
            // view还没有构造，等待onCreateView
            pendingList.add(delegate);
            return;
        }

        ViewGroup container = delegate.getContainer();
        if (container == null) {
            throw new IllegalTransformError("getContainer is null");
        }

        for (int i = 0; i < container.getChildCount(); i++) {
            if (container.getChildAt(i) == delegate.getView()) {
                return;
            }
        }

        container.addView(delegate.getView());
    }

    void unmountComponent(final UIComponentDelegate delegate) throws IllegalTransformError {
        if (pendingList.contains(delegate)) {
            // view还没有add进容器，直接返回
            return;
        }

        if (delegate.getContainer() == null || delegate.getView() == null) {
            throw new IllegalTransformError("getContainer or getView is null");
        }

        delegate.getContainer().removeView(delegate.getView());
    }

    /**
     * 如果之前存在，先移除后添加。如果当前和上次为同一个 VIEW 直接复用
     */
    void updateComponent(final UIComponentDelegate delegate, final View currentView) throws IllegalTransformError {
        if (delegate.getContainer() == null || currentView == null) {
            throw new IllegalTransformError("getContainer or getView is null");
        }

        View lastView = delegate.getView();
        if (lastView == null) {
            delegate.setView(currentView);
            mountComponent(delegate);
        } else if (lastView != currentView) {
            unmountComponent(delegate);
            delegate.setView(currentView);
            mountComponent(delegate);
        }
        pendingList.remove(delegate);
    }
}
