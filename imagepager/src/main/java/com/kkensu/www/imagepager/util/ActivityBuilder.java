package com.kkensu.www.imagepager.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

/**
 * Activity 시작을 편하게 하기 위한 Builder 패턴
 */
@SuppressWarnings("unused")
public class ActivityBuilder<T extends Activity> {
    private Context context;
    private final Intent intent;
    private Bundle option;

    public ActivityBuilder(Context context, Class<T> clazz) {
        this.context = context;
        intent = new Intent(context, clazz);
    }

    public ActivityBuilder<T> arg(Bundle bundle) {
        intent.putExtras(bundle);
        return this;
    }

    public ActivityBuilder<T> arg(BundleBuilder builder) {
        intent.putExtras(builder.build());
        return this;
    }

    public ActivityBuilder<T> flags(int flags){
        intent.setFlags(flags);
        return this;
    }
    public void start() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent, option);
        }else{
            context.startActivity(intent);
        }
    }

    public void startForResult(int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((Activity) context).startActivityForResult(intent, requestCode, option);
        }else{
            ((Activity) context).startActivityForResult(intent, requestCode);
        }
    }

    public Intent intent() {
        return intent;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public ActivityBuilder<T> withOptions(Bundle bundle) {
        option = bundle;
        return this;
    }
}
