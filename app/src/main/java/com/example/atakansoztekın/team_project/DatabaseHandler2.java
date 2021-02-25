package com.example.atakansoztekin.team_project;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler2 extends SQLiteOpenHelper {

    //-- Database Version
    private static final int DATABASE_VERSION = 1;

    //-- Database Name
    private static final String DATABASE_NAME = "ScoreDB";

    //-- Contacts table name
    private static final String TABLE_CONTACTS = "ScoreUsers";

    //-- Contacts Table Columns names
    private static final String COL_NAME = "name";
    private static final String COL_SCORE = "score";

    /* Constructor */
    public DatabaseHandler2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* Create table on OnCreate()*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL Statement: CREATE TABLE tblContacts (id INTEGER PRIMARY KEY, name TEXT, phoneNumber TEXT))

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + COL_NAME + " TEXT,"
                + COL_SCORE + " INT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //-- drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        //-- create tables again
        onCreate(db);
    }

    /* SQLiteDatabase.insert : Convenience method for inserting a row into the database. */
    public long addContact(Leaderboard leaderboard) {
        // SQL Statement: INSERT tblContacts (name,phoneNumber) VALUES ('a','1')
        SQLiteDatabase db = this.getWritableDatabase();

        // Create ContentValues to pass insert method
        ContentValues values = new ContentValues();
        values.put(COL_NAME, leaderboard.getName());
        values.put(COL_SCORE, leaderboard.getScore());

        //-- insert new contact row
        long result = db.insert(
                TABLE_CONTACTS, // the table to insert the row into
                null, 			// nullColumnHack, optional; may be null.
                values);		// this map contains the initial column values for the row. The keys should be the column names and the values the column values

        //-- close database connection
        db.close();

        return result;
    }

    /* SQLiteDatabase.query : Query the given table, returning a Cursor over the result set. */
    public List<Leaderboard> getAllContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Leaderboard> scorelist = new ArrayList<Leaderboard>();

        //-- select all query
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS + " ORDER BY score DESC";

        //-- run query on db
        Cursor cursor = db.rawQuery(
                selectQuery,
// the SQL query.
                null);// You may include ?s in where clause in the query, which will be replaced by the values from selectionArgs. The values will be bound as Strings.

        //-- loop through all rows and adding to list
        //-- Move the cursor to the first row
        if (cursor.moveToFirst()) {
            do {
                Leaderboard leaderboard = new Leaderboard();
                leaderboard.setScore(cursor.getInt(1));
                leaderboard.setName(cursor.getString(0));

                //-- add contact to list
                scorelist.add(leaderboard);
            } while (cursor.moveToNext()); // Move the cursor to the next row
        }

        //-- close resources
        cursor.close();
        db.close();

        //-- return contact list
        return scorelist;
    }

}