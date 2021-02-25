package com.example.atakansoztekin.team_project;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    //-- Database Version
    private static final int DATABASE_VERSION = 1;

    //-- Database Name
    private static final String DATABASE_NAME = "UserDB";

    //-- Contacts table name
    private static final String TABLE_CONTACTS = "tblUsers";

    //-- Contacts Table Columns names
    private static final String COL_NAME = "name";
    private static final String COL_PASSWORD = "password";

    /* Constructor */
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* Create table on OnCreate()*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL Statement: CREATE TABLE tblContacts (id INTEGER PRIMARY KEY, name TEXT, phoneNumber TEXT))

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + COL_NAME + " TEXT,"
                + COL_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //-- drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        //-- create tables again
        onCreate(db);
    }

    /* SQLiteDatabase.update : Convenience method for updating rows in the database. */
    public int updateContact(Users users) {
        // SQL Statement: UPDATE tblContacts SET name=a, phoneNumber=1 WHERE id = ?
        SQLiteDatabase db = this.getWritableDatabase();

        // Create ContentValues to pass update method
        ContentValues values = new ContentValues();
        values.put(COL_NAME, users.getName());
        values.put(COL_PASSWORD, users.getPassword());

        int result = db.update(
                TABLE_CONTACTS, 		// the table to update in
                values, 				// a map from column names to new column values. null is a valid value that will be translated to NULL.
                COL_NAME+ " = ?",	// the optional WHERE clause to apply when updating. Passing null will update all rows.
                new String[] { String.valueOf(users.getName()) });  // You may include ?s in the where clause, which will be replaced by the values from whereArgs. The values will be bound as Strings.

        //-- close resources
        db.close();

        return result;
    }

    /* SQLiteDatabase.insert : Convenience method for inserting a row into the database. */
    public long addContact(Users users) {
        // SQL Statement: INSERT tblContacts (name,phoneNumber) VALUES ('a','1')
        SQLiteDatabase db = this.getWritableDatabase();

        // Create ContentValues to pass insert method
        ContentValues values = new ContentValues();
        values.put(COL_NAME, users.getName());
        values.put(COL_PASSWORD, users.getPassword());

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
    public Users getContact(String id) {
        // SELECT id,name,phoneNumber FROM tblContacts
        String path = "/data/user/0/com.example.atakansoztekin.a2014502207_hw3/databases/UserDB";
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, 0);
        Users users = new Users();
        if(getCount(id)!=0) {

            Cursor cursor = db.query(
                    TABLE_CONTACTS,    //  The table name to compile the query against.
                    new String[]{COL_NAME, COL_PASSWORD},   //  A list of which columns to return. Passing null will return all columns, which is discouraged to prevent reading data from storage that isn't going to be used.
                    COL_NAME + "=?",        // A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given table.
                    new String[]{String.valueOf(id)}, // You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings.
                    null,       // A filter declaring how to group rows, formatted as an SQL GROUP BY clause (excluding the GROUP BY itself). Passing null will cause the rows to not be grouped.
                    null,       // A filter declare which row groups to include in the cursor, if row grouping is being used, formatted as an SQL HAVING clause (excluding the HAVING itself). Passing null will cause all row groups to be included, and is required when row grouping is not being used.
                    null);      // How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort order, which may be unordered.

            //-- Check cursor object for null
            if (cursor != null) {

                cursor.moveToFirst();
                users.setName(cursor.getString(0));           //-- read name (0th col) from cursor
                users.setPassword(cursor.getString(1));
            }

            //-- close cursor
            cursor.close();
            db.close();
        }
        else {
            users.setName("");
            users.setName("");
        }
        //-- close database connection

        return users;
    }
    public int getCount(String name) {
        Cursor c = null;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String query = "select count(*) from tblUsers where name = ?";
            c = db.rawQuery(query, new String[] {name});
            if (c.moveToFirst()) {
                return c.getInt(0);
            }
            return 0;
        }
        finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

}