package qlearn.com.quang.english.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;

import qlearn.com.quang.english.R;
import qlearn.com.quang.english.database.MySQLiteHelper;

/**
 * Created by quang.nguyen on 08/01/2016.
 */
public class InitDataBaseActivity extends Activity {
    Thread thread;
    ProgressBarCircularIndeterminate mCircleLoadingView;
    Context mContext;
    Handler mHandler;
    int count = 1;
    Timer T;
    Handler handler;
    Runnable runnablel;
    private MySQLiteHelper dbHelper;
    boolean check = true;
    int totalLine = 0;
    int i=0;
    int index=0;
    SQLiteDatabase db;
    public void readTXTFromRaw(Context context) {
        try {
            final InputStream is = context.getResources().openRawResource(R.raw.new_vocabulary);
            final BufferedReader buff = new BufferedReader(new InputStreamReader(is));
            dbHelper = MySQLiteHelper.getInstance(context);
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
                String sql = "insert into "+MySQLiteHelper.TABLE_3000W+"("
                    + MySQLiteHelper.COLUMN_ID + ","
                    + MySQLiteHelper.COLUMN_VOCABULARY + ","
                    + MySQLiteHelper.COLUMN_WORDFORM + ","
                    + MySQLiteHelper.COLUMN_PRONOUNCE + ","
                    + MySQLiteHelper.COLUMN_TRANSLATE + ","
                    + MySQLiteHelper.COLUMN_DESCRIPTION + ") values (?,?,?,?,?,?)";
                String line = buff.readLine();
                SQLiteStatement insert = db.compileStatement(sql);

                    while (line != null) {
                        try {

                            line = buff.readLine();
                            if (line!=null && line.contains("/")) {
                                String[] split;
                                if(line.contains("~~")){
                                    String[] splitRoot = line.split("~~");
                                    String description = "";
                                    for(int i = 1;i<splitRoot.length;i++){

//                                        for(int i = 3; i < split.length;i++){
//                                            description += split[i];
//                                        }
                                        description += "~~" + splitRoot[i];
                                        insert.bindString(6, description);
                                    }
                                     split = splitRoot[0].split("/");
                                }else {
                                    split = line.split("/");
                                }
                                if (split.length >= 3) {


                                    String[] splitVoca = split[0].split(" ");
                                    String voca = "";
                                    String form = "";
                                    for(int i = 0; i < splitVoca.length ; i++){
                                        if(!splitVoca[i].contains(".")){
                                            voca += splitVoca[i] + " ";
                                        }else{
                                            form += splitVoca[i] + " ";
                                        }
                                    }
                                    insert.bindLong(1, index);
                                    insert.bindString(2, voca.trim());
                                    insert.bindString(3, form.trim());
                                    insert.bindString(4, split[1].trim().length()>20?"":split[1].trim()); // sure
                                    insert.bindString(5, split[2].trim());

                                    try {
                                        insert.executeInsert();
                                    }catch (Exception e){
                                        Log.e("Quang","insert");
                                    }
                                    insert.clearBindings();
                                    Log.e("Quang :","---"+index);
                                    index++;
                                }

                            }
                            i++;

                            if(i>=(totalLine/100)*count){

                                count++;


                            }
                        } catch (Exception e) {
                            Log.e("Quang :", e.toString());
                        }
                    }
                    db.setTransactionSuccessful();
                    db.endTransaction();
                    db.close();


        } catch (Exception e) {
            Log.e("Quang :", e.toString());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initdatabase);
        mCircleLoadingView = (ProgressBarCircularIndeterminate) findViewById(R.id.circle_loading_view);

        totalLine = countLineFile();
        mContext = getApplicationContext();
        getApplicationContext().deleteDatabase(MySQLiteHelper.DATABASENAME);
        new insertBackGround().execute("");

    }
    private int countLineFile(){
        int i = 0;
        InputStream is = getResources().openRawResource(R.raw.new_vocabulary);
        BufferedReader buff = new BufferedReader(new InputStreamReader(is));
        try{
            String line = buff.readLine();
            while (line != null) {
                i++;
                line = buff.readLine();
            }
        }catch (Exception e){

        }
        return i;
    }
    private class insertBackGround extends AsyncTask<String, Integer, Boolean>{
        @Override
        protected Boolean doInBackground(String... params) {
            readTXTFromRaw(mContext);
           // publishProgress(1);
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean done) {
          if(done){
            //  mCircleLoadingView.stopOk();
              handler = new Handler();
              runnablel = new Runnable() {
                  public void run() {
                      Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                      startActivity(intent);
                      finish();
                  }
              };
              handler.postDelayed(runnablel
                      , 3000);
          }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);




        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        T.cancel();
        if (thread != null)
            thread.interrupt();
        if (handler != null)
            handler.removeCallbacks(runnablel);
    }
}
