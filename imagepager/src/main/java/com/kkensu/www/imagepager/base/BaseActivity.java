package com.kkensu.www.imagepager.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    public BaseActivity activity;

    private int color = android.R.color.black;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setStatusBarColor(color);
    }

    public void setStatusBarColor(int color) {
        this.color = color;

        /* 상태바 색상변경 */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(color));
        }
    }

//    protected abstract void initStatusBarColor();
}
