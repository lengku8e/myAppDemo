/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.example.mylog;

import android.os.Build;

/**
 * User: lichenyu
 * Date: 1/9/14
 * Time: 12:32 PM
 */
public class MLog {

    public static  boolean DEBUG = BuildConfig.DEBUG;
    private static final String  TAG   = "BaiduMapLog";

    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.v(tag, "" + msg);
        }
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.d(tag, "" + msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.d(tag, "" + msg, tr);
        }
    }

    public static void d(String tag, String... msgs) {
        if (DEBUG) {
            android.util.Log.d(tag, formatMsgs(msgs, " "));
        }
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.i(tag, "" + msg);
        }
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.w(tag, "" + msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.w(tag, "" + msg, tr);
        }
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.e(tag, "" + msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.e(tag, "" + msg, tr);
        }
    }

    public static boolean isLoggable(int level) {
        return isLoggable(TAG, level);
    }

    public static boolean isLoggable(String tag, int level) {
        if (DEBUG) {
            return android.util.Log.isLoggable(tag, level);
        }

        return false;
    }

    public static void wtf(String msg) {
        wtf(TAG, msg);
    }

    public static void wtf(String tag, String msg) {
        if (DEBUG) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                android.util.Log.wtf(tag, "" + msg);
            }
        }
    }

    private static String formatMsgs(final String[] msgs, final String splitter) {
        if (msgs == null || splitter == null) {
            return "";
        }

        final StringBuilder sb = new StringBuilder();
        for (String msg : msgs) {
            sb.append(msg);
            sb.append(splitter);
        }
        return sb.toString();
    }

}