package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DatabaseName = "mydb.db";
    private static final int version = 1;

    private static final String TableName = "tbl_person";
    private static final String dId = "id";
    private static final String dName = "name";
    private static final String dFamily = "family";

    public DatabaseManager(Context context) {
        super(context, DatabaseName, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String cQuery = "CREATE TABLE " + TableName + "(" + dId + " INTEGER  PRIMARY KEY," + dName + " TEXT , " + dFamily + " TEXT );";

        db.execSQL(cQuery);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String cQuery = "create table tbl_person (id INTEGER PRIMARY KEY,name TEXT,family TEXT);";

    }

    public void isertperson(Person iprs) {

        SQLiteDatabase idb = this.getWritableDatabase();
        ContentValues icv = new ContentValues();

        icv.put(dId, iprs.pId);
        icv.put(dName, iprs.pName);
        icv.put(dFamily, iprs.pFamily);


        idb.insert(TableName, null, icv);
        idb.close();
    }

    public Person getperson(String gID) {

        Person gprs = new Person();
        SQLiteDatabase gbd = this.getReadableDatabase();

        String gQuery = "SELECT * FROM " + TableName + " WHERE " + dId + " = " + gID;
        Cursor gcur = gbd.rawQuery(gQuery, null);

        if (gcur.moveToFirst()) {

            gprs.pName = gcur.getString(1);
            gprs.pFamily = gcur.getString(2);
        }

        return gprs;

    }


    public void updateperson(Person uprs) {

        SQLiteDatabase udb = this.getWritableDatabase();
        ContentValues uvc = new ContentValues();
        uvc.put(dName, uprs.pName);
        uvc.put(dFamily, uprs.pFamily);
        udb.update(TableName, uvc, dId + " = ?", new String[]{String.valueOf(uprs.pId)});

    }


    public boolean deletperson(Person dper) {

        SQLiteDatabase ddb = this.getWritableDatabase();
        int dresult = ddb.delete(TableName, dId + " = ?", new String[]{String.valueOf(dper.pId)});
        if (dresult == 0)
            return false;
        else
            return true;
    }


    public int personCount() {
        String gQuery = "SELECT * FROM " + TableName;
        SQLiteDatabase gdb = this.getReadableDatabase();
        Cursor gcur = gdb.rawQuery(gQuery, null);
        int cresult = gcur.getCount();
        return cresult;

    }
}
