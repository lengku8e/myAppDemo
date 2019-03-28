package com.example.uimvvm.manage;

import android.content.Context;
import android.view.ViewGroup;

import com.example.uimvvm.LifecycleEvent;
import com.example.uimvvm.UIComponent;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 管理 UIComponent lifecycle 和 view 构造
 */
public class UIComponentManager {

    private final Context context;
    private final ViewManager viewManager = new ViewManager();
    private final LifecycleManager lifecycleManager = new LifecycleManager(viewManager);
    private final ExceptionManger exceptionManager = new ExceptionManger(this);

    private final LinkedHashMap<UIComponent, UIComponentDelegate> componentMap = new LinkedHashMap<>();
    private LifecycleState currentState = LifecycleState.NONE;

    public UIComponentManager(Context context) {
        this.context = context;
    }

    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        exceptionManager.setHandler(exceptionHandler);
    }

    public void addUIComponent(ViewGroup containerView, UIComponent component) {
        UIComponentDelegate delegate = componentMap.get(component);
        if (delegate != null) {
            exceptionManager.handleWarning("COMPONENT HAS BEEN ADDED");
            return;
        }

        try {
            component.onContext(context);
            delegate = new UIComponentDelegate(component, containerView);
            viewManager.mountComponent(delegate);
            lifecycleManager.transform(delegate, currentState);
            componentMap.put(component, delegate);
        } catch (Exception e) {
            exceptionManager.handleException(delegate, e);
        }
    }

    public void removeUIComponent(UIComponent component) {
        UIComponentDelegate delegate = componentMap.get(component);
        if (delegate == null) {
            exceptionManager.handleWarning("COMPONENT HAS BEEN REMOVED");
            return;
        }

        try {
            lifecycleManager.transform(delegate, LifecycleState.DESTROYED);
            viewManager.unmountComponent(delegate);
        } catch (Exception e) {
            // 移除页面时不做补救处理
            exceptionManager.handleException(delegate, e);
        } finally {
            componentMap.remove(component);
        }
    }

    public void enableComponent(UIComponent component) {
        UIComponentDelegate delegate = componentMap.get(component);
        if (delegate == null) {
            exceptionManager.handleWarning("COMPONENT HAS BEEN REMOVED");
            return;
        }

        if (delegate.isEnable()) {
            return;
        }

        try {
            delegate.setEnable(true);
            lifecycleManager.transform(delegate, currentState);
        } catch (Exception e) {
            exceptionManager.handleException(delegate, e);
        }
    }

    public void disableComponent(UIComponent component) {
        UIComponentDelegate delegate = componentMap.get(component);
        if (delegate == null) {
            exceptionManager.handleWarning("COMPONENT HAS BEEN REMOVED");
            return;
        }

        if (!delegate.isEnable()) {
            return;
        }

        try {
            delegate.setEnable(false);
            lifecycleManager.transform(delegate, LifecycleState.DESTROYED);
        } catch (Exception e) {
            exceptionManager.handleException(delegate, e);
        }
    }

    public void onLifecycleEvent(LifecycleEvent event) {
        LifecycleState lifecycleState = LifecycleState.fromEvent(event);
        for (Map.Entry<UIComponent, UIComponentDelegate> entry : componentMap.entrySet()) {
            UIComponentDelegate delegate = entry.getValue();
            if (delegate.isEnable()) {
                try {
                    lifecycleManager.transform(delegate, lifecycleState);
                } catch (Exception e) {
                    exceptionManager.handleException(delegate, e);
                }
            }
        }
        this.currentState = lifecycleState;
    }

    public interface ExceptionHandler {
        void onException(UIComponent uiComponent, Exception e);
    }
}
