package org.cnx.openstaxcnxmusic.providers;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ew2 on 7/20/16.
 */
public class Bookmarks implements BaseColumns
{
    /** Private constructor.  */
    private Bookmarks()
    {

    }

    /** URI of code allowed to access table, I think*/
    public static final Uri CONTENT_URI = Uri.parse("content://org.cnx.openstaxcnxmusic.providers.BookmarkProvider");
    /** title column name*/
    public static final String TITLE = "bm_title";
    /** url column name*/
    public static final String URL = "bm_url";
    /** id column name*/
    public static final String ID = "_id";
    /** other column name*/
    public static final String OTHER = "bm_other";
}
