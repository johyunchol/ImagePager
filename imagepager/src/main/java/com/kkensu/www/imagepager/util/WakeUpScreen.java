package com.kkensu.www.imagepager.util;

import android.content.Context;
import android.os.PowerManager;

public class WakeUpScreen {

    private static PowerManager.WakeLock wakeLock;

    /**
     * timeout을 설정하면, 자동으로 릴리즈됨
     * @param context
     * @param timeout
     */
    public static void acquire(Context context, long timeout) {

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(
                PowerManager.ACQUIRE_CAUSES_WAKEUP  |
                PowerManager.FULL_WAKE_LOCK         |
                PowerManager.ON_AFTER_RELEASE
                , context.getClass().getName());

        if(timeout > 0)
            wakeLock.acquire(timeout);
        else
            wakeLock.acquire();

    }

    /**
     * 이 메소드를 사용하면, 반드시 release를 해줘야 함
     * @param context
     */
    public static void acquire(Context context) {
        acquire(context, 0);
    }

    public static void release() {
        if (wakeLock.isHeld())
            wakeLock.release();
    }
}
