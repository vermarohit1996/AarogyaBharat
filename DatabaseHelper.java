package com.example.arogyademo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.content.ContextCompat;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "health.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME_USER = "user";
    public static final String USER_COL_0 = "ID", USER_COL_1 = "PHONE", USER_COL_2 = "PASSWORD", USER_COL_3 = "NAME", USER_COL_4 = "GENDER", USER_COL_5 = "DOB",USER_COL_6 = "EMAIL",USER_COL_7 = "BLOOD_GROUP",USER_COL_8 = "STATE",USER_COL_9 = "CITY",USER_COL_10 = "IMAGE";
    public static final String QUERY_USER = "CREATE TABLE USER(ID INTEGER PRIMARY KEY AUTOINCREMENT,PHONE TEXT UNIQUE,PASSWORD TEXT,NAME TEXT,GENDER TEXT,DOB TEXT, EMAIL TEXT UNIQUE,BLOOD_GROUP TEXT,STATE TEXT,CITY TEXT,IMAGE TEXT);";

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public long signUp(String phone,String password)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(USER_COL_1,phone);
        contentValues.put(USER_COL_2,password);
        return db.insert(TABLE_NAME_USER,null,contentValues);
    }

    public long updateUser(User user,String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COL_3, user.name);
        contentValues.put(USER_COL_4, user.gender);
        contentValues.put(USER_COL_5, user.dob);
        contentValues.put(USER_COL_6, user.email);
        contentValues.put(USER_COL_7, user.blood_group);
        contentValues.put(USER_COL_8, user.state);
        contentValues.put(USER_COL_9, user.city);
        contentValues.put(USER_COL_10, user.image);
        return db.update(TABLE_NAME_USER,contentValues,"PHONE=?",new String[]{phone});
    }
    public long updatePassword(String password,String phone)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(USER_COL_2,password);
        return db.update(TABLE_NAME_USER,contentValues,"PHONE=?",new String[]{phone});
    }

    public Cursor searchUserByPhone(String phone)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.query(TABLE_NAME_USER,null,"PHONE=?",new String[] {phone},null,null,null);
    }

    public long deleteUser(String phone)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME_USER,"PHONE=?",new String[]{phone});
    }
    public Cursor getAllUser()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+TABLE_NAME_USER,null);
    }
    public void closeDb()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        if(db!=null&&db.isOpen())
        {
            db.close();
        }
    }
}
