/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.cnx.openstaxcnxmusic.providers;

import android.database.Cursor;

import org.cnx.openstaxcnxmusic.beans.Book;

import java.util.ArrayList;

/**
 * Utility class for database
 * @author Ed Woodward
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
