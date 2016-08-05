package org.cnx.openstaxcnxmusic.providers;

import android.database.Cursor;

import org.cnx.openstaxcnxmusic.beans.Book;

import java.util.ArrayList;

/**
 * Created by ew2 on 7/21/16.
 */
public class DBUtils
{
    public static ArrayList<Book> readCursorIntoList(Cursor c)
    {
        ArrayList<Book> contentList = new ArrayList<>();

        int titleColumn = c.getColumnIndex(Bookmarks.TITLE);
        int urlColumn = c.getColumnIndex(Bookmarks.URL);
        int idColumn = c.getColumnIndex(Bookmarks.ID);
        int otherColumn = c.getColumnIndex(Bookmarks.OTHER);
        if(c.getCount() > 0)
        {
            c.moveToNext();
            do
            {
                Book con = new Book();
                con.setTitle(c.getString(titleColumn));
                con.setUrl(c.getString(urlColumn));
                con.setId(c.getInt(idColumn));
                con.setDesc(c.getString(otherColumn));
                contentList.add(con);

            }while(c.moveToNext());
        }
        c.close();

        return contentList;

    }
}
