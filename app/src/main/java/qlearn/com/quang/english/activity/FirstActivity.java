package qlearn.com.quang.english.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import qlearn.com.quang.english.database.MySQLiteHelper;
import qlearn.com.quang.english.util.GeneralUtils;

/**
 * Created by quang.nguyen on 11/01/2016.
 */
public class FirstActivity extends Activity {
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        Intent intent;
        if (!GeneralUtils.isDatabase(getApplicationContext())) {
            intent = new Intent(getApplicationContext(), InitDataBaseActivity.class);
        } else {
            if (GeneralUtils.getVersionDatabase(mContext) != MySQLiteHelper.DATABASE_VERSION) {
                intent = new Intent(getApplicationContext(), InitDataBaseActivity.class);
            } else {
                intent = new Intent(getApplicationContext(), HomeActivity.class);
            }
        }
        startActivity(intent);
        finish();
    }

}
