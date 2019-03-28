package com.example.uimvvm.mvvm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.uimvvm.LifecycleEvent;
import com.example.uimvvm.UIComponent;
import com.example.uimvvm.manage.UIComponentManager;

public abstract class MVVMComponent implements UIComponent, LifecycleOwner, LifecycleObserver {

    private static final String TAG = MVVMComponent.class.getName();

    private Context context;
    private View view;
    private UIComponentManager manager;
    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private LifecycleEvent lastEvent = null;

    {
        autoCreatePresenterAndModel();
        bindLifecycle(this);
    }

    public abstract ViewDataBinding getBinding();

    @Override
    public void onContext(Context context) {
        this.context = context;
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void onLifecycleEvent(LifecycleEvent event) {
        switch (event) {
            case ON_CREATE:
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
                break;
            case ON_CREATE_VIEW:
                createView();
                break;
            case ON_START:
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
                break;
            case ON_RESUME:
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
                break;
            case ON_PAUSE:
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
                break;
            case ON_STOP:
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
                break;
            case ON_DESTROY_VIEW:
                destroyView();
                break;
            case ON_DESTROY:
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
                break;
            default:
                Log.e(TAG, "exception: unknown LifecycleEvent");
                break;
        }
        if (manager != null) {
            manager.onLifecycleEvent(event);
        }
        lastEvent = event;
    }

    @NonNull
    public Context getContext() {
        return context;
    }

    /**
     * 以当前 Component 为根节点，获取 Manager
     */
    @NonNull
    public final UIComponentManager getUIComponentManager() {
        if (manager == null) {
            manager = new UIComponentManager(context);
            if (lastEvent != null) {
                manager.onLifecycleEvent(lastEvent);
            }
        }
        return manager;
    }

    public void bindLifecycle(LifecycleObserver lifecycleObserver) {
        getLifecycle().addObserver(lifecycleObserver);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }

    private void createView() {
        if (view == null) {
            autoCreateBinding();
            view = getBinding().getRoot();
        }

        notifyViewEvent(OnViewCreated.class);
    }

    private void destroyView() {
        notifyViewEvent(OnViewDestroyed.class);
    }

    private void notifyViewEvent(Class annotationClass) {
        Field[] fields = getClass().getFields();
        for (Field field : fields) {
            if (!MVVMPresenter.class.equals(field.getType().getSuperclass())
                    && !MVVMModel.class.equals(field.getType().getSuperclass())) {
                continue;
            }
            try {
                notifyViewEvent(annotationClass, field.getType().getMethods(), field.get(this));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        notifyViewEvent(annotationClass, this.getClass().getMethods(), this);
    }
    private void notifyViewEvent(Class annotationClass, Method[] methods, Object target) {
        for (Method method : methods) {
            if (method.getAnnotation(annotationClass) == null) {
                continue;
            }
            try {
                method.invoke(target);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void autoCreatePresenterAndModel() { // 自动创建、注入Component、自动bindLifecycle
        Field[] fields = getClass().getFields();
        for (Field field : fields) {
            try {
                if (field.get(this) != null) {
                    continue;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (MVVMPresenter.class.equals(field.getType().getSuperclass())) {
                injectPresenter(field);
            } else if (MVVMModel.class.equals(field.getType().getSuperclass())) {
                injectModel(field);
            }
        }
    }

    private void autoCreateBinding() { // 自动创建、注入Component、自动bindLifecycle
        Field[] fields = getClass().getFields();
        for (Field field : fields) {
            try {
                if (field.get(this) != null) {
                    continue;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (android.databinding.ViewDataBinding.class.equals(field.getType().getSuperclass())) {
                inflateBinding(field);
            }
        }
    }

    private void inflateBinding(Field field) {
        try {
            Method method = field.getType().getMethod("inflate", LayoutInflater.class);
            ViewDataBinding obj = (ViewDataBinding) method.invoke(null, LayoutInflater.from(context));
            obj.setLifecycleOwner(this);
            field.set(this, obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private void injectPresenter(Field field) {
        try {
            MVVMPresenter presenter = (MVVMPresenter) field.getType().getConstructor().newInstance();
            presenter.setComponent(this);
            bindLifecycle(presenter);
            field.set(this, presenter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private void injectModel(Field field) {
        try {
            MVVMModel model = (MVVMModel) field.getType().getConstructor().newInstance();
            model.setComponent(this);
            field.set(this, model);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}