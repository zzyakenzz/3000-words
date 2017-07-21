package qlearn.com.quang.english.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import qlearn.com.quang.english.model.WordModel;

/**
 * Created by quang.nguyen on 17/12/2015.
 */
public class Database {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_VOCABULARY,
            MySQLiteHelper.COLUMN_WORDFORM, MySQLiteHelper.COLUMN_PRONOUNCE, MySQLiteHelper.COLUMN_TRANSLATE, MySQLiteHelper.COLUMN_DESCRIPTION};

    public Database(Context context) {
        dbHelper = MySQLiteHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();

    }

    public void close() {
        dbHelper.close();
    }

    public void addWord(WordModel wordModel) {
        //Log.d(getClass().getSimpleName().toString(), "addWord: " + wordModel.toString());
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_VOCABULARY, wordModel.getVocabulary());
        values.put(MySQLiteHelper.COLUMN_WORDFORM, wordModel.getWordForm());
        values.put(MySQLiteHelper.COLUMN_PRONOUNCE, wordModel.getPronounce());
        values.put(MySQLiteHelper.COLUMN_TRANSLATE, wordModel.getTranslate());
        values.put(MySQLiteHelper.COLUMN_DESCRIPTION, wordModel.getDescription());
        database.insert(MySQLiteHelper.TABLE_3000W, null, values);
    }


    public WordModel getWordCorrect(int id) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_3000W, allColumns, MySQLiteHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        WordModel wordModel = new WordModel();
        wordModel.setId(Integer.parseInt(cursor.getString(0)));
        wordModel.setVocabulary(cursor.getString(1));
        wordModel.setWordForm(cursor.getString(2));
        wordModel.setTranslate(cursor.getString(3));
        wordModel.setTranslate(cursor.getString(4));
        Log.d(getClass().getSimpleName().toString(), "getWord(" + id + "):" + wordModel.toString());
        return wordModel;
    }
    public ArrayList<WordModel> getWord(String word) {
        ArrayList<WordModel> list = new ArrayList<WordModel>();
        String query = "SELECT * FROM " + MySQLiteHelper.TABLE_3000W + " WHERE "+ MySQLiteHelper.COLUMN_VOCABULARY + " LIKE '"+word+"%'";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                WordModel wordModel = new WordModel();
                wordModel.setId(Integer.parseInt(cursor.getString(0)));
                wordModel.setVocabulary(cursor.getString(1));
                wordModel.setWordForm(cursor.getString(2));
                wordModel.setPronounce(cursor.getString(3));
                wordModel.setTranslate(cursor.getString(4));
                wordModel.setDescription(cursor.getString(5));
                list.add(wordModel);
            } while (cursor.moveToNext());
        }
//        database.close();
        return list;
    }


    public ArrayList<WordModel> getAllWord() {
        ArrayList<WordModel> list = new ArrayList<>();
        String query = "SELECT * FROM " + MySQLiteHelper.TABLE_3000W;
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                WordModel wordModel = new WordModel();
                wordModel.setId(Integer.parseInt(cursor.getString(0)));
                wordModel.setVocabulary(cursor.getString(1));
                wordModel.setWordForm(cursor.getString(2));
                wordModel.setTranslate(cursor.getString(3));
                wordModel.setTranslate(cursor.getString(4));
                list.add(wordModel);
            } while (cursor.moveToNext());
        }
//        database.close();
        return list;
    }

    public int updateWord(WordModel wordModel) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_VOCABULARY, wordModel.getVocabulary());
        values.put(MySQLiteHelper.COLUMN_WORDFORM, wordModel.getWordForm());
        values.put(MySQLiteHelper.COLUMN_PRONOUNCE, wordModel.getPronounce());
        values.put(MySQLiteHelper.COLUMN_TRANSLATE, wordModel.getTranslate());
        values.put(MySQLiteHelper.COLUMN_DESCRIPTION, wordModel.getDescription());
        int i = database.update(MySQLiteHelper.TABLE_3000W, values, MySQLiteHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(wordModel.getId())});
        Log.d(getClass().getSimpleName().toString(), "update(" + wordModel.getId() + "):" + wordModel.toString());
        return i;
    }

    public void deleteWord(WordModel wordModel) {
        database.delete(MySQLiteHelper.TABLE_3000W, MySQLiteHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(wordModel.getId())});
        Log.d(getClass().getSimpleName().toString(), "delete(" + wordModel.getId() + "):" + wordModel.toString());
    }
}
