package com.kkensu.www.imagepager.util;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * 서비스 실행을 쉽게 하기 위한 Builder 패턴
 */
@SuppressWarnings("unused")
public class ServiceBuilder<T extends Service> {
    private final Context context;
    private final Intent intent;

    public ServiceBuilder(Context context, Class<T> clazz) {
        this.context = context;
        intent = new Intent(context, clazz);
    }

    public ServiceBuilder<T> arg(Bundle bundle) {
        intent.putExtras(bundle);
        return this;
    }

    public ServiceBuilder<T> arg(BundleBuilder builder) {
        intent.putExtras(builder.build());
        return this;
    }

    public void start() {
        context.startService(intent);
    }

}
