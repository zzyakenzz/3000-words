package qlearn.com.quang.english.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

import qlearn.com.quang.english.database.MySQLiteHelper;
import qlearn.com.quang.english.model.WordModel;

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

    public static int getRelativeLeft(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getLeft();
        else
            return myView.getLeft() + getRelativeLeft((View) myView.getParent());
    }

    public static int getRelativeTop(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getTop();
        else
            return myView.getTop() + getRelativeTop((View) myView.getParent());
    }
    public static int getSizeScreenW(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }
    public static int getSizeScreenH(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;

    }
    public static int dpToPixel(Context context,int dp){
        Resources r = context.getResources();
        return  (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
    }
    public static String getRefWord(String word,ArrayList<WordModel> listWords){
    int count = 0;
        String result = "";
        word.toLowerCase();

        for(int i = 0 ; i < listWords.size(); i++){
            String w = listWords.get(i).getVocabulary();
            if(!word.isEmpty()
                    && word.contains(w.trim())
                    && w.length()>=3
                    && !word.equals(w)){
                result += w.toString()+"; ";
                count++;
            }
            if (count>5){
                break;
            }
        }
        return result;
    }
}
