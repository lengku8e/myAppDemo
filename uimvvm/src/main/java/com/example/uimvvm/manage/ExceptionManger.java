package com.example.uimvvm.manage;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.uimvvm.BuildConfig;
import com.example.uimvvm.LifecycleEvent;
import com.example.uimvvm.UIComponent;


/**
 * 处理 UIComponentManager 异常逻辑
 * 接口由 UIComponentManage 内部使用，并保证线程一致性
 */
class ExceptionManger {

    private static final String TAG = ExceptionManger.class.getName();

    private final UIComponentManager uiComponentManager;
    private UIComponentManager.ExceptionHandler handler;

    ExceptionManger(UIComponentManager uiComponentManager) {
        this.uiComponentManager = uiComponentManager;
    }

    void setHandler(UIComponentManager.ExceptionHandler handler) {
        this.handler = handler;
    }

    void handleException(UIComponentDelegate delegate, Exception e) {
        UIComponent component;
        if (delegate != null) {
            component = delegate.getHost();
        } else {
            component = NO_UI_COMPONENT;
        }

        if (BuildConfig.DEBUG) {
            throw new RuntimeException(e);
        } else if (handler != null) {
            handler.onException(component, e);
        } else {
            DEFAULT_HANDLER.onException(component, e);
        }
    }

    void handleWarning(String message) {
        if (BuildConfig.DEBUG) {
            throw new RuntimeException(message);
        } else {
            Log.e(TAG, "runtimeWarning: " + message);
        }
    }

    private static final UIComponentManager.ExceptionHandler DEFAULT_HANDLER =
            new UIComponentManager.ExceptionHandler() {
                @Override
                public void onException(UIComponent uiComponent, Exception e) {
                    Log.e(TAG, "runtimeException: ", e);
                }
            };

    private static final UIComponent NO_UI_COMPONENT = new UIComponent() {
        @Override
        public void onContext(Context context) {
            // design to empty
        }

        @Override
        public View getView() {
            return null;
        }

        @Override
        public void onLifecycleEvent(LifecycleEvent event) {
            // design to empty
        }
    };
}
