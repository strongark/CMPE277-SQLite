package com.cmpe277.datastorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by tranpham on 3/28/17.
 */

public class DbController {

    public static final String DATABASE_NAME="datastorage.db";
    public static final int DATABASE_VERSION=1;
    public static final String TABLE_NAME="Product";

    DbHelper dbHelper;
    Context context;
    SQLiteDatabase db;

    public DbController(Context context)
    {
        this.context=context;
        dbHelper=new DbHelper(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public DbController open()
    {
        db=dbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        dbHelper.close();
    }

    public long insert(Product prodItem)
    {
        ContentValues content=new ContentValues();
        content.put("Name", prodItem.name);
        content.put("Description", prodItem.description);
        content.put("Price", prodItem.price);
        content.put("Review", prodItem.review);
        return db.insertOrThrow(TABLE_NAME, null, content);
    }

    public Cursor searchByName(String productName)
    {
        //// TODO: 3/28/17 update the where clause to ignore case
        return db.query(TABLE_NAME, new String[]{"Name","Description","Price","Review"}
                        , "Name=?",new String[]{productName}, null, null, "Name ASC");
    }

}
