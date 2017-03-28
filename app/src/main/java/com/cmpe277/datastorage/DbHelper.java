package com.cmpe277.datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tranpham on 3/26/17.
 */


public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /*
    * Generate database table
    * PRODUCT
    * {
    *  Name varchar(250)
    *  Description text
    *  Price double
    *  Review text
    *  (Rating double)
    *  */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql= "CREATE TABLE IF NOT EXISTS Product(ProductId INTEGER PRIMARY KEY, " +
                "   Name TEXT NOT NULL, Description TEXT, Price REAL, Review TEXT)";
        //create sample data
        String sampleSql="INSERT INTO Product(Name, Description, Price, Revew)" +
                "VALUES('Samsung Galazy Note 7', 'A brandnew model with big screen',599.99, 'It provide great experiences')," +
                "('IPhone 7', 'Force touch and new iOS 10',799.99, 'Very elegant design')";
        try {
            db.execSQL(sql);
            db.execSQL(sampleSql);
        }
        catch (SQLiteException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="DROP TABLE IF EXISTS Product";
        try {
            db.execSQL(sql);
        }
        catch (SQLiteException ex){
            ex.printStackTrace();
        }
        onCreate(db);
    }
}
