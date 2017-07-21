package qlearn.com.quang.english.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import qlearn.com.quang.english.util.GeneralUtils;


/**
 * Created by quang.nguyen on 16/12/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASENAME = "vocabulary";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_3000W = "TB3000W";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_VOCABULARY = "_vocabulary";
    public static final String COLUMN_WORDFORM = "_wordform";
    public static final String COLUMN_PRONOUNCE = "_pronounce";
    public static final String COLUMN_TRANSLATE = "_translate";
    public static final String COLUMN_DESCRIPTION = "_description";
    private static final String DATABASE_CREATE = "create table " +
            TABLE_3000W + "(" +
            COLUMN_ID + " integer," +
            COLUMN_VOCABULARY + " text," +
            COLUMN_WORDFORM + " text," +
            COLUMN_PRONOUNCE + " text," +
            COLUMN_TRANSLATE + " text," +
            COLUMN_DESCRIPTION + " text"
            + ");";
    private static MySQLiteHelper mInstance = null;
    private Context mContext;

    public MySQLiteHelper(Context context) {
        super(context, DATABASENAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public static MySQLiteHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySQLiteHelper(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        GeneralUtils.saveVersionDatabase(mContext);
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_3000W);
        onCreate(db);
    }
}
