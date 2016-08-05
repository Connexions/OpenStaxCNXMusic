package org.cnx.openstaxcnxmusic.beans;

import java.io.Serializable;

/**
 * Created by ew2 on 7/1/16.
 */
public class Book implements Serializable, Comparable<Book>
{
    private String bookTitle;
    private String url;
    private String title;
    private String desc;
    private int id;

    public String getBookTitle()
    {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle)
    {
        this.bookTitle = bookTitle;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int compareTo(Book another)
    {
        int titleCompare = title.toUpperCase().trim().compareTo(another.getTitle().toUpperCase().trim());
        if(titleCompare != 0)
        {
            return titleCompare;
        }
        else
        {
            return url.compareTo(another.getUrl());
        }
    }
}
