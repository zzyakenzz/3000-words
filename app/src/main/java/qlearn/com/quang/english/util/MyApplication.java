package qlearn.com.quang.english.util;

import android.app.Application;

/**
 * Created by quang.nguyen on 04/03/2016.
 */
public class MyApplication extends Application {
        private boolean isInitView = true;

    public boolean isInitView() {
        return isInitView;
    }

    public void setIsInitView(boolean isInitView) {
        this.isInitView = isInitView;
    }
}
