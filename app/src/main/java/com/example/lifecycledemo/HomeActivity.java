package com.example.lifecycledemo;

import android.os.Bundle;

import databind.TabStore;

public class HomeActivity extends BaseActivity {
    TabStore tabStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabStore = new TabStore(this);
        tabStore.tabPresent.initData();
        setContentView(tabStore.binding.getRoot());

    }

    @Override
    public boolean supportFullScreen() {
        return true;
    }
}
