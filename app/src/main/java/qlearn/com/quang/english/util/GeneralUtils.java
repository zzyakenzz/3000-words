package qlearn.com.quang.english.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.io.File;

import qlearn.com.quang.english.database.MySQLiteHelper;

/**
 * Created by quang.nguyen on 23/12/2015.
 */
public class GeneralUtils {
    public static String KEY_SHARE = "KEY_SHARE";
    public static String KEY_DATABASE_VERSION = "KEY_DATABASE_VERSION";

    public static boolean isDatabase(Context context) {
        File database = context.getDatabasePath(MySQLiteHelper.DATABASENAME);
        return database.exists();
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    public static void saveVersionDatabase(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_DATABASE_VERSION, MySQLiteHelper.DATABASE_VERSION);
        editor.commit();
    }

    public static Integer getVersionDatabase(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_SHARE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_DATABASE_VERSION, 0);
    }
}
