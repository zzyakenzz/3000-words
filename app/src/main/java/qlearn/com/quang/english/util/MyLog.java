package qlearn.com.quang.english.util;

import android.content.Context;
import android.util.Log;

/**
 * Created by quang.nguyen on 24/12/2015.
 */
public class MyLog {
    public static void LogV(Context context, String string) {
        Log.v(context.getClass().getName().toString(), string);
    }

    public static void LogD(Context context, String string) {
        Log.d(context.getClass().getName().toString(), string);
    }

    public static void LogE(Context context, String string) {
        Log.e(context.getClass().getName().toString(), string);
    }
}
