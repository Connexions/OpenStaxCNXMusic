package org.cnx.openstaxcnxmusic.providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ew2 on 7/20/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "cnxmusic.db";
    /**  database version */
    private static final int DATABASE_VERSION = 1;
    /**  Constructor*/
    DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**  Creates table if it does not exist*/
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + BookmarkProvider.BOOKMARK_TABLE + " ("
                + Bookmarks.ID + " INTEGER PRIMARY KEY,"
                + Bookmarks.TITLE + " TEXT,"
                + Bookmarks.URL + " TEXT,"
                + Bookmarks.OTHER + " TEXT"
                + ");");
    }

    /** For upgrading database */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
//        db.execSQL("CREATE TABLE " + NotesProvider.NOTES_TABLE + " ("
//                + Notes._ID + " INTEGER PRIMARY KEY,"
//                + Notes.TITLE + " TEXT,"
//                + Notes.NOTE + " TEXT,"
//                + Notes.URL + " TEXT"
//                + ");");
    }

}
