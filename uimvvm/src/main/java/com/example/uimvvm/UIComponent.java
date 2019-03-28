package com.example.uimvvm;

import android.content.Context;
import android.view.View;

public interface UIComponent {

    /**
     * 输入上下文
     */
    void onContext(Context context);

    /**
     * 输出视图
     */
    View getView();

    /**
     * 输入生命周期
     */
    void onLifecycleEvent(LifecycleEvent event);
}
