package qlearn.com.quang.english.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import qlearn.com.quang.english.R;
import qlearn.com.quang.english.database.Database;
import qlearn.com.quang.english.database.MySQLiteHelper;
import qlearn.com.quang.english.model.WordModel;

/**
 * Created by quang.nguyen on 08/01/2016.
 */
public class InitDataBaseActivity extends Activity {
    static Thread thread;
    AnimatedCircleLoadingView mCircleLoadingView;
    Context mContext;
    Handler mHandler;
    int count = 0;
    Timer T;
    Handler handler;
    Runnable runnablel;
    boolean check = true;

    public static void readTXTFromRaw(Context context, final Handler handler) {
        try {
            final Database database = new Database(context);
            database.open();
            final InputStream is = context.getResources().openRawResource(R.raw.vocabulary);
            final BufferedReader buff = new BufferedReader(new InputStreamReader(is));

            thread = new Thread(new Runnable() {
                String line = buff.readLine();

                @Override
                public void run() {

                    while (line != null) {
                        try {

                            line = buff.readLine();
                            if (line.contains("/")) {
                                String[] split = line.split("/");
                                if (split.length > 2) {
                                    WordModel wordModel = new WordModel();
                                    String[] splitVoca = split[0].split(" ");
                                    wordModel.setVocabulary(splitVoca[0].trim());
                                    wordModel.setWordForm(split[0].substring(split[0].indexOf(" ")).trim());
                                    wordModel.setPronounce(split[1].trim());
                                    wordModel.setTranslate(split[2].trim());
                                    database.addWord(wordModel);


                                }

                            }
                        } catch (Exception e) {
                            Log.e("Quang :", e.toString());
                        }
                    }

                }
            });

            thread.start();
        } catch (Exception e) {
            Log.e("Quang :", e.toString());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initdatabase);
        mCircleLoadingView = (AnimatedCircleLoadingView) findViewById(R.id.circle_loading_view);
        mCircleLoadingView.startDeterminate();

        mContext = getApplicationContext();
        getApplicationContext().deleteDatabase(MySQLiteHelper.DATABASENAME);
        readTXTFromRaw(mContext, mHandler);

//        else{
//            getApplicationContext().deleteDatabase(MySQLiteHelper.DATABASENAME);
//        }
        T = new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (count > 100 && check) {
                            check = false;
                            mCircleLoadingView.stopOk();
                            try {
                                handler = new Handler();
                                runnablel = new Runnable() {
                                    public void run() {
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                };
                                handler.postDelayed(runnablel
                                        , 3500);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (check) {
                                mCircleLoadingView.setPercent(count);
                                count++;
                            }
                        }
                    }
                });
            }
        }, 25, 25);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        T.cancel();
        if (thread != null)
            thread.interrupt();
        if (handler != null)
            handler.removeCallbacks(runnablel);
    }
}
