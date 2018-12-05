package com.bonobocorp.joker.litrocent.Controler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bonobocorp.joker.litrocent.Model.DatabaseHandler;

public abstract class DAOBase {

    protected final static int VERSION = 3;
    protected final static String NOM = "litrocentDB.db";

    protected SQLiteDatabase mDb = null;
    protected DatabaseHandler mHandler = null;

    protected DAOBase(Context pContext) {
        this.mHandler = new DatabaseHandler(pContext, NOM, null, VERSION);
    }

    protected SQLiteDatabase open() {
        mDb = mHandler.getWritableDatabase();
        return mDb;
    }

    protected void close() {
        mDb.close();
    }

    protected SQLiteDatabase getDb() {
        return mDb;
    }
}

