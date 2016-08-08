/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.cnx.openstaxcnxmusic.beans;

import java.util.ArrayList;

/**
 * Holds list of Book objects
 * @author Ed Woodward
 */
public class BookList
{
    ArrayList<Book> bookList;

    public ArrayList<Book> getBookList()
    {
        return bookList;
    }

    public void setBookList(ArrayList<Book> bookList)
    {
        this.bookList = bookList;
    }
}
