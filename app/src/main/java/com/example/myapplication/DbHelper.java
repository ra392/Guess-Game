package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scores.db";
    private static final String TABLE_SCORES = "scores";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_QUESTION_NUMBER = "question_number";
    private static final String COLUMN_RESULT = "result";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCORES_TABLE = "CREATE TABLE " + TABLE_SCORES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_QUESTION_NUMBER + " INTEGER,"
                + COLUMN_RESULT + " INTEGER"
                + ")";
        db.execSQL(CREATE_SCORES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);
    }

    public void addScore(Score score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION_NUMBER, score.getQuestionNumber());
        values.put(COLUMN_RESULT, score.getResult());
        db.insert(TABLE_SCORES, null, values);
        db.close();
    }

    public List<Score> getAllScores() {
        List<Score> scores = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_SCORES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int questionNumberIndex = cursor.getColumnIndex(COLUMN_QUESTION_NUMBER);
                int resultIndex = cursor.getColumnIndex(COLUMN_RESULT);
                if (questionNumberIndex != -1 && resultIndex != -1) {
                    int questionNumber = cursor.getInt(questionNumberIndex);
                    int result = cursor.getInt(resultIndex);
                    Score score = new Score(questionNumber, result);
                    scores.add(score);
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return scores;
    }


    public void deleteAllScores() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCORES, null, null);
        db.close();
    }
}
