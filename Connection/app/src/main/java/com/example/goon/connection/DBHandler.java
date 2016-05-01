package com.example.goon.connection;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * Created by Daniela on 29/04/2016.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="goonInfos";
    private static final String TABLE_USER="userTable";
    private static final String TABLE_CHANEL="chanelTable";
    private static final String TABLE_ACTIVITY="avtivityTable";
    //userTable column
    private static final String USER_ID="userID";
    private static final String USERNAME="username";
    private static final String PASSWORD="password";
    private static final String EMAIL="email";
    private static final String AGE="age";
    private static final String CITIZEN="citizen";
    private static final String TAGS="tags";

    //chanelTable columns
    private static final String CHANEL_ID="userID";
    private static final String CHANEL_NAME="chanelName";
    private static final String CHANEL_DESCRIPTION="chanelDescription";
    private static final String CHANEL_TAGS="chanelTags";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + USER_ID + " INTEGER PRIMARY KEY,"+USERNAME+ " TEXT"
                + PASSWORD + " TEXT" + EMAIL+" TEXT"+AGE+" TEXT"+CITIZEN+" TEXT"+
                TAGS+" TEXT"+")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        // Creating tables again
        onCreate(db);
    }

    // Adding new user
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, user.getUserId());
        values.put(USERNAME,user.getUsername());
        values.put(PASSWORD, user.getPassword());
        values.put(EMAIL,user.getEmail());
        values.put(AGE,user.getAge());
        values.put(CITIZEN, user.getCitizen());
// Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
    }

    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[] {USER_ID,
                        USERNAME,PASSWORD,EMAIL,AGE,CITIZEN}, USER_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        User contact = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), Integer.parseInt(cursor.getString(4)), cursor.getString(5));
// return user
        return contact;
    }

    // Updating an user
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME,user.getUsername());
        values.put(PASSWORD, user.getPassword());
        values.put(EMAIL,user.getEmail());
        values.put(AGE,user.getAge());
        values.put(CITIZEN, user.getCitizen());

// updating row
        return db.update(TABLE_USER, values, USER_ID + " = ?",
                new String[]{String.valueOf(user.getUserId())});
    }

    // Updating an user
    public int updateTags(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        User user = getUser(id);
        values.put(TAGS, String.valueOf(user.getTags()));
// updating row
        return db.update(TABLE_USER, values, USER_ID + " = ?",
                new String[]{String.valueOf(user.getUserId())});
    }

    // Deleting an user
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, USER_ID + " = ?",
                new String[]{String.valueOf(user.getUserId())});
        db.close();
    }


}
