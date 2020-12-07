package com.example.utskalkulator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class HistoryDBHelper extends SQLiteOpenHelper {

    private Context context;
    private static  final String  DATABASE_NAME ="numhistory.db";
    private static  final  int DATABASE_VERSION = 1;

    private static  String TABLE_NAME = "historylist";
    private static  String COLUMN_ID = "_id";
    private static  String COLUMN_NUMBER1 = "number1";
    private static  String COLUMN_SYMBOL = "symbol";
    private static  String COLUMN_NUMBER2 = "number2";
    private static  String COLUMN_COUNT = "count";


    public HistoryDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
        this.context =context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NUMBER1 + " DOUBLE, " +
                        COLUMN_SYMBOL + " TEXT, " +
                        COLUMN_NUMBER2 + " DOUBLE, " +
                        COLUMN_COUNT + " DOUBLE);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
    void addHistory(double number1,String simbol, double number2,double count)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NUMBER1,number1);
        cv.put(COLUMN_SYMBOL,simbol);
        cv.put(COLUMN_NUMBER2,number2);
        cv.put(COLUMN_COUNT,count);


        long result =db.insert(TABLE_NAME,null,cv);

        if(result == -1 )
        {
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(context,"sucess",Toast.LENGTH_SHORT).show();
    }

    Cursor readalldata()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db =this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null)
        {
            cursor = db.rawQuery(query,null);
        }
        return  cursor;
    }
    void deletealldata()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}
