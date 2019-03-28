package com.example.lifecycledemo;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import base.BaseActivityInter;
import util.DeviceHelper;


public class BaseActivity extends FragmentActivity implements BaseActivityInter {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.WHITE);
        }
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public boolean supportFullScreen() {
        return false;
    }

    @Override
    public int statusBarColor() {
        return getStatusBarColor();
    }

    @Override
    public PageStyle pageStyle() {
        return PageStyle.WHITE;
    }


    public void changeFullScreenStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.setStatusBarColor(statusBarColor());
            if (supportFullScreen()) {
                if (Build.VERSION.SDK_INT >= 23) {
                    window.getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                    DeviceHelper.setStatusBarLightMode(window, pageStyle() != BaseActivityInter.PageStyle.BLACK);
                } else {
                    window.getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                }
            } else {
                window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                DeviceHelper.setStatusBarLightMode(window, false);
            }
        }
    }


    private int getStatusBarColor() {
        if (supportFullScreen()) {
            if (Build.VERSION.SDK_INT >= 23) {
                return Color.TRANSPARENT;
            } else {
                return 0x2d000000;
            }
        } else {
            return Color.BLACK;
        }
    }

}
