package org.cnx.openstaxcnxmusic.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.HashMap;

/**
 * Created by ew2 on 7/20/16.
 */
public class BookmarkProvider  extends ContentProvider
{
    /** Favorites table name */
    public static final String BOOKMARK_TABLE = "bookmarks";

    /** Map of Fav table columns */
    private static HashMap<String, String> BMProjectionMap;

    /** static section to initialize bookmark table map */
    static
    {
        BMProjectionMap = new HashMap<>();
        BMProjectionMap.put(Bookmarks.ID, Bookmarks.ID);
        BMProjectionMap.put(Bookmarks.TITLE, Bookmarks.TITLE);
        BMProjectionMap.put(Bookmarks.URL, Bookmarks.URL);
        BMProjectionMap.put(Bookmarks.OTHER, Bookmarks.OTHER);
    }

    /** Variable for database helper */
    private DatabaseHelper dbHelper;

    /**  Called when class created. initializes database helper*/
    @Override
    public boolean onCreate()
    {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    /** used to delete content from bookmark table */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = db.delete(BOOKMARK_TABLE, selection, selectionArgs);
        db.close();
        return count;
    }

    /**  needed for interface.  Not sure why.*/
    @Override
    public String getType(Uri uri)
    {
        return null;
    }

    /** Used to insert a bookmarks in the bookmarks table */
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        //check if the URL is already in bookmarks
        boolean duplicate = checkForDuplicate(values.getAsString(Bookmarks.URL));
        //If not in bookmarks, save it
        if(! duplicate)
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long rowId = db.insert(BOOKMARK_TABLE, null, new ContentValues(values));
            db.close();
            if (rowId > 0)
            {
                Uri bmUri = ContentUris.withAppendedId(Bookmarks.CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(bmUri, null);
                return bmUri;
            }

            throw new SQLException("Failed to insert row into " + uri);
        }
        return null;

    }

    /**  Used to retrieve all items in the bookmarks table*/
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(BOOKMARK_TABLE);
        qb.setProjectionMap(BMProjectionMap);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
    }

    /** Could be used to update items already in the favs table
     * Not used now, but needed for interface. */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        return 0;
    }

    /**
     * Check if URL isd already in database
     * @param url String - URL to check
     * @return true if the URL is already in the database, otherwise false
     */
    private boolean checkForDuplicate(String url)
    {
        boolean dup = false;
        Cursor c = query(Bookmarks.CONTENT_URI,null,"bm_url='"+url+"'",null, null);
        int urlColumn = c.getColumnIndex(Bookmarks.URL);
        if(c.getCount()>0)
        {
            c.moveToNext();
            if(c.getString(urlColumn).equals(url))
            {
                dup = true;
            }
        }
        c.close();
        return dup;
    }


}
