package com.example.lifecycledemo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.lifecycledemo.databinding.ActivityMainBinding;

import databind.Car;
import databind.User;
import lifecycle.MainPresenter;
import pagestack.FrameActivityTest;

public class DataBindingActivity extends AppCompatActivity {

    private MainPresenter minPresenter;
    private ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        minPresenter = new MainPresenter(this);
        getLifecycle().addObserver(minPresenter);
        user = new User();
        user.setAge("18");
        user.setName("long");
        user.sex = ("男");
        binding.setUser(user);
        binding.textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.sex = ("女");
                user.notifyPropertyChanged(BR.sex);
                user.setName("sunhailong");
                Intent intent = new Intent();
                intent.setClass(DataBindingActivity.this, FrameActivityTest.class);
                startActivity(intent);
            }
        });
        final Car car = new Car();
        car.setName("大众汽车");
        binding.setCars(car);
        binding.textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                car.setName("奥迪");
            }
        });
    }
}
