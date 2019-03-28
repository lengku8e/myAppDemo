package com.example.uidatabinding.databinding.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;

import com.example.mylog.MLog;

public abstract class AbsStore<P> {

    public Activity context;
    public P page;


    public AbsStore(P p) {
        this(p, true, true, true);
    }

    public AbsStore(P p, boolean autoInflateBinding, boolean autoPresenter, boolean autoModel) {
        page = p;

        if (p instanceof Fragment) {
            context = ((Fragment) p).getActivity();
        } else if (p instanceof Activity) {
            context = (Activity) p;
        } else if (p instanceof android.support.v4.app.Fragment) {
            context = ((android.support.v4.app.Fragment) p).getActivity();
        }

        for (Field field : getClass().getFields()) {
            if (autoInflateBinding && android.databinding.ViewDataBinding.class.equals(field.getType().getSuperclass())) {
                inflateBinding(field);
                MLog.e("inject binding:  "  + field.getName());
            } else if (autoPresenter && AbsPresenter.class.equals(field.getType().getSuperclass())) {
                injectPresenter(field);
                MLog.e("inject presenter:  "  + field.getName());
            } else if (autoModel && AbsModel.class.equals(field.getType().getSuperclass())) {
                injectModel(field);
                MLog.e("inject model:  "  + field.getName());
            }
        }
    }

    private void inflateBinding(Field field) {
        try {
            Method method = field.getType().getMethod("inflate", LayoutInflater.class);
            Object obj = method.invoke(null, LayoutInflater.from(context));
            field.set(this, obj);
        } catch (Exception e) {
            MLog.e(e.getMessage());
        }
    }

    private void injectPresenter(Field field) {
        try {
            AbsPresenter presenter = (AbsPresenter) field.getType().getConstructor().newInstance();
            presenter.setAbsPresenter(this);
            field.set(this, presenter);
        } catch (Exception e) {
            MLog.e(e.getMessage());
        }
    }


    private void injectModel(Field field) {
        try {
            AbsModel model = (AbsModel) field.getType().getConstructor().newInstance();
            model.setAbsPresenter(this);
            field.set(this, model);
        } catch (Exception e) {
            MLog.e(e.getMessage());
        }
    }

}
