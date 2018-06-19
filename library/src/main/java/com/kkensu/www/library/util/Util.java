package com.kkensu.www.library.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * 잡다한 도움이 될 함수들을 모아둔 클래스
 */
@SuppressWarnings("unused")
public class Util {

    public static final String TAG = "Util";

    public static boolean isServiceRunning(Context context, String serviceClassName) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(serviceClassName)) {
                return true;
            }
        }
        return false;
    }


    public static boolean hasArg(Activity activity, String key) {
        Intent intent = activity.getIntent();
        return intent != null && intent.hasExtra(key);
    }

    public static boolean hasArg(Fragment fragment, String key) {
        Bundle arg = fragment.getArguments();
        return arg != null && arg.keySet().contains(key);
    }

    public static String getStringArg(Activity activity, String key) {
        Intent intent = activity.getIntent();
        if (!hasArg(activity, key))
            throw new RuntimeException("no such argument for key -> " + key);
        return intent.getStringExtra(key);
    }

    public static int getIntegerArg(Activity activity, String key) {
        Intent intent = activity.getIntent();
        if (!hasArg(activity, key))
            throw new RuntimeException("no such argument for key -> " + key);
        return intent.getIntExtra(key, 0);
    }

    public static double getDoubleArg(Activity activity, String key) {
        Intent intent = activity.getIntent();
        if (!hasArg(activity, key))
            throw new RuntimeException("no such argument for key -> " + key);
        return intent.getDoubleExtra(key, 0);
    }

    public static long getLongArg(Activity activity, String key) {
        Intent intent = activity.getIntent();
        if (!hasArg(activity, key))
            throw new RuntimeException("no such argument for key -> " + key);
        return intent.getLongExtra(key, 0);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T getSerializableArg(Activity activity, String key) {
        return getSerializableArg(activity, key, true);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T getSerializableArg(Activity activity, String key, boolean notNull) {
        Intent intent = activity.getIntent();
        if (notNull && !hasArg(activity, key))
            throw new RuntimeException("no such argument for key -> " + key);
        return (T) intent.getSerializableExtra(key);
    }

    public static <T extends Activity> void finishAllActivityInTaskAndStartActivity(Context context, Class<T> clazz) {
        Intent intent = new Intent(context, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static <T extends Fragment> FragmentBuilder<T> newFragment(T fragment) {
        return new FragmentBuilder<>(fragment);
    }

    public static <T extends Activity> ActivityBuilder<T> newActivity(Context context, Class<T> clazz) {
        return new ActivityBuilder<>(context, clazz);
    }

    public static <T extends Service> ServiceBuilder<T> service(Context context, Class<T> clazz) {
        return new ServiceBuilder<>(context, clazz);
    }

    public static boolean getBooleanArg(Activity activity, String key) {
        Intent intent = activity.getIntent();
        if (!hasArg(activity, key))
            throw new RuntimeException("no such argument for key -> " + key);
        return intent.getBooleanExtra(key, false);
    }

    public static <T extends Service> void stopService(Context context, Class<T> clazz) {
        Intent service = new Intent(context, clazz);
        context.stopService(service);

    }

    public static String getStringArg(Fragment fragment, String key) {
        Bundle arg = fragment.getArguments();
        if (!hasArg(fragment, key))
            throw new RuntimeException("no such argument for key -> " + key);
        return arg.getString(key);
    }

    public static boolean getBooleanArg(Fragment fragment, String key) {
        Bundle arg = fragment.getArguments();
        if (!hasArg(fragment, key))
            throw new RuntimeException("no such argument for key -> " + key);
        return arg.getBoolean(key);
    }

    public static int getIntegerArg(Fragment fragment, String key) {
        Bundle arg = fragment.getArguments();
        if (!hasArg(fragment, key))
            throw new RuntimeException("no such argument for key -> " + key);
        return arg.getInt(key);
    }


    public static String formatCostDistance(double distance) {
        int distanceInt = (int) distance;
        StringBuilder builder = new StringBuilder();
        if (distanceInt > 1000) {
            builder.append(distanceInt / 1000);
            builder.append(".");
            builder.append((distanceInt % 1000) / 100);
            builder.append("km");
        } else {
            builder.append(((distanceInt / 100) + (distanceInt % 100) > 50 ? 1 : 0) * 100);
            builder.append("m");
        }
        return builder.toString();
    }

    public static String formatTime(double time) {
        int timeInt = (int) time;
        int minute = timeInt / 60;
        int hour = minute / 60;
        minute = minute % 60;
        if (timeInt % 60 > 30)
            minute += 1;

        if (hour == 0 && minute == 0)
            return "잠시 후 도착";
        StringBuilder builder = new StringBuilder();
        if (hour > 0) {
            builder.append(hour);
            builder.append("시간 ");
        }
        if (minute > 0) {
            builder.append(minute);
            builder.append("분");
        }
        return builder.toString();
    }

    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e(TAG, e);
            throw new RuntimeException("Unexpected State");
        }
    }

    public static <T extends View> T findView(Dialog dialog, @IdRes int resId) {
        return findView(dialog, resId, true);
    }

    public static <T extends View> T findView(Fragment fragment, @IdRes int resId) {
        return findView(fragment, resId, true);
    }

    public static <T extends View> T findView(View view, @IdRes int resId) {
        return findView(view, resId, true);
    }

    public static <T extends View> T findView(Activity activity, @IdRes int resId) {
        return findView(activity, resId, true);
    }

    public static <T extends View> List<T> findView(Activity activity, @IdRes int[] resId) {
        ArrayList<T> list = new ArrayList<>();
        for (int id : resId) {
            T view = findView(activity, id);
            list.add(view);
        }
        return list;
    }

    public static <T extends View> T findView(Dialog dialog, @IdRes int resId, boolean notNull) {
        View viewById = dialog.findViewById(resId);
        if (viewById == null && notNull)
            throw new RuntimeException("view is not found");
        //noinspection unchecked
        return (T) viewById;
    }

    public static <T extends View> T findView(Fragment fragment, @IdRes int resId, boolean notNull) {
        //noinspection unchecked,ConstantConditions
        View viewById = fragment.getView().findViewById(resId);
        if (viewById == null && notNull)
            throw new RuntimeException("view is not found");
        //noinspection unchecked
        return (T) viewById;
    }

    public static <T extends View> T findView(View view, @IdRes int resId, boolean notNull) {
        View viewById = view.findViewById(resId);
        if (viewById == null && notNull)
            throw new RuntimeException("view is not found");
        //noinspection unchecked
        return (T) viewById;
    }

    public static <T extends View> T findView(Activity activity, @IdRes int resId, boolean notNull) {
        View viewById = activity.findViewById(resId);
        if (viewById == null && notNull)
            throw new RuntimeException("view is not found");
        //noinspection unchecked
        return (T) viewById;
    }

    public static void onClick(Activity activity, @IdRes int resId, boolean notNull, View.OnClickListener onClickListener) {
        View view = Util.findView(activity, resId, notNull);
        if (view != null) {
            view.setOnClickListener(onClickListener);
        } else if (notNull) {
            throw new RuntimeException("view is not found");
        }
    }

    public static void onClick(Activity activity, @IdRes int resId, View.OnClickListener onClickListener) {
        onClick(activity, resId, true, onClickListener);
    }

    public static void onClick(Activity activity, @IdRes int[] resId, View.OnClickListener onClickListener) {
        onClick(activity, resId, true, onClickListener);
    }

    public static void onClick(Activity activity, @IdRes int[] resId, boolean notNull, View.OnClickListener onClickListener) {
        for (int id : resId) {
            onClick(activity, id, notNull, onClickListener);
        }
    }

    public static boolean isPackageInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void onClick(View view, @IdRes int[] resId, View.OnClickListener onClickListener) {
        onClick(view, resId, true, onClickListener);
    }

    public static void onClick(View view, @IdRes int[] resId, boolean notNull, View.OnClickListener onClickListener) {
        for (int id : resId) {
            onClick(view, id, notNull, onClickListener);
        }
    }

    public static void onClick(View view, @IdRes int resId, View.OnClickListener onClickListener) {
        onClick(view, resId, true, onClickListener);
    }

    public static void onClick(View view, @IdRes int resId, boolean notNull, View.OnClickListener onClickListener) {
        View findView = Util.findView(view, resId);

        if (findView != null) {
            findView.setOnClickListener(onClickListener);
        } else if (notNull) {
            throw new RuntimeException("view is not found");
        }

    }

    public static void onClick(Dialog dialog, @IdRes int[] resId, View.OnClickListener onClickListener) {
        onClick(dialog, resId, true, onClickListener);
    }

    public static void onClick(Dialog dialog, @IdRes int[] resId, boolean notNull, View.OnClickListener onClickListener) {
        for (int id : resId) {
            onClick(dialog, id, notNull, onClickListener);
        }
    }

    public static void onClick(Dialog dialog, @IdRes int resId, View.OnClickListener onClickListener) {
        onClick(dialog, resId, true, onClickListener);
    }

    public static void onClick(Dialog dialog, @IdRes int resId, boolean notNull, View.OnClickListener onClickListener) {
        View findView = Util.findView(dialog, resId);

        if (findView != null) {
            findView.setOnClickListener(onClickListener);
        } else if (notNull) {
            throw new RuntimeException("view is not found");
        }

    }

    public static void scrollBottomToTop(final ScrollView scrollView, final int animationDuration) {
        new CountDownTimer(animationDuration, 20) {
            public void onTick(long millisUntilFinished) {
//                LogUtil.d(TAG, "millisUntilFinished: " + millisUntilFinished);
//                LogUtil.d(TAG, "animationDuration: " + scrollView.getMaxScrollAmount());
                long remain = (scrollView.getMaxScrollAmount() * (animationDuration - millisUntilFinished)) / animationDuration;
//                LogUtil.d(TAG, "remain: " + remain);
                scrollView.scrollTo(0, scrollView.getMaxScrollAmount() - (int) remain);
            }

            public void onFinish() {
                scrollView.scrollTo(0, 0);
            }
        }.start();
    }

    public static int[] getColor(Resources res, int... colorResourceId) {
        int[] colorResult = new int[colorResourceId.length];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < colorResourceId.length; i++) {
                colorResult[i] = res.getColor(colorResourceId[i], null);
            }
        } else {
            for (int i = 0; i < colorResourceId.length; i++) {
                colorResult[i] = res.getColor(colorResourceId[i]);
            }
        }
        return colorResult;
    }

    public static String removeAsterisk(String s) {
        if (s.startsWith("※")) {
            s = s.substring(1, s.length());
        }
        if (s.startsWith(" ")) {
            s = s.substring(1, s.length());
        }
        return s;
    }

    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null || appProcesses.size() == 0) {
            return false;
        }
        final String packageName = context.getPackageName();
        ActivityManager.RunningAppProcessInfo appProcess = appProcesses.get(0);
        return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                && appProcess.processName.equals(packageName);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView, int maxCount) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        int count = maxCount <= 0 ? listAdapter.getCount() : maxCount;

        for (int i = 0; i < count; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static String getEncMD5(String txt) throws Exception {
        StringBuilder sbuf = new StringBuilder();
        MessageDigest mDigest = MessageDigest.getInstance("MD5");
        mDigest.update(txt.getBytes());

        byte[] msgStr = mDigest.digest();

        for (byte aMsgStr : msgStr) {
            String tmpEncTxt = Integer.toHexString((int) aMsgStr & 0x00ff);
            sbuf.append(tmpEncTxt);
        }
        return sbuf.toString();
    }

    public static boolean isForeground(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> tasks = manager.getRunningAppProcesses();
        if (tasks.isEmpty())
            return false;

        for (ActivityManager.RunningAppProcessInfo task : tasks) {
            if (context.getPackageName().equalsIgnoreCase(task.processName)) {
                return task.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
            }
        }
        return false;
    }
}
