package com.kkensu.www.library.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Logger, 레벨에 따른 필터링
 */
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class LogUtil {
    private static boolean showDebug;
    private static boolean showInfo;
    private static boolean showWarning;
    private static boolean showError;
    private static int MAX_LINE = 100;
    private static boolean showVerbose;


    private static Executor executor = Executors.newFixedThreadPool(1);

    public enum Level {
        VERBOSE,
        DEBUG,
        INFO,
        WARNING,
        ERROR,
        NONE
    }

    private static boolean isInitialized;

    public static void init(Level level) {
        Log.d("SJS_DEBUG","LogLevel"+level);
        Log.d("SJS_DEBUG","LogLevelordinal"+level.ordinal());
        showVerbose = level.ordinal() <= Level.VERBOSE.ordinal();
        showDebug = level.ordinal() <= Level.DEBUG.ordinal();
        showInfo = level.ordinal() <= Level.INFO.ordinal();
        showWarning = level.ordinal() <= Level.WARNING.ordinal();
        showError = level.ordinal() <= Level.ERROR.ordinal();
        isInitialized = true;
    }

    public static String getLogTAG(Object object) {
        Class<?> clazz = object.getClass();
        return getLogTAG(clazz);
    }

    public static String getLogTAG(Class<?> clazz) {
        String tag = clazz.getSimpleName();
        if (tag.length() > 23)
            tag = tag.substring(0, 23);
        return tag;
    }

    public static void d(final String tag, final String... messages) {
        StringBuilder builder = new StringBuilder();
        for (String message : messages) {
            builder.append(message);
            builder.append(" ");
        }
        final String message = builder.toString();
        if (showDebug) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    List<String> messages = split(message);
                    for (String shorten : messages) {
                        Log.d(tag, shorten);
                    }
                }
            });
        }
    }

    public static void d(final String tag, final Throwable throwable) {
        if (showDebug) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d(tag, throwable.getMessage(), throwable);
                }
            });
        }
    }

    public static void v(final String tag, final String message) {
        if (showVerbose) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    List<String> messages = split(message);
                    for (String shorten : messages) {
                        Log.v(tag, shorten);
                    }
                }
            });
        }
    }

    public static void e(final String tag, final Throwable throwable) {
        if (showError)
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    LogUtil.e(tag, throwable.getMessage() + throwable);
                }
            });
    }

    public static void e(final String tag, final String message) {
        if (showError) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    List<String> messages = split(message);
                    for (String shorten : messages) {
                        LogUtil.e(tag, shorten);
                    }
                }
            });
        }
    }

    public static void i(final String tag, final String message) {
        if (showInfo) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    List<String> messages = split(message);
                    for (String shorten : messages) {

                        Log.i(tag, shorten);
                    }
                }
            });
        }
    }

    public static void w(final String tag, final String message) {
        if (showWarning) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    List<String> messages = split(message);
                    for (String shorten : messages) {
                        Log.w(tag, shorten);
                    }
                }
            });
        }
    }

    static List<String> split(String veryLongString) {
        List<String> result = new ArrayList<>();
        int maxLogSize = 1000;

        String[] realLines = veryLongString.split("\\n");
        for (String realLine : realLines) {
            String[] lines = realLine.split("\\\\n(\\\\t)?");

            for (String line : lines) {
                for (int i = 0; i <= line.length() / maxLogSize; i++) {
                    int start = i * maxLogSize;
                    int end = (i + 1) * maxLogSize;
                    end = end > line.length() ? line.length() : end;
                    result.add(line.substring(start, end));
                    if (result.size() == MAX_LINE)
                        break;
                }
            }
        }
        return result;
    }
}
