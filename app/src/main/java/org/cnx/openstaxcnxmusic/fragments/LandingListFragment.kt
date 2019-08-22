/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.cnx.openstaxcnxmusic.fragments

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.cnx.openstaxcnxmusic.R
import org.cnx.openstaxcnxmusic.adapters.LandingListRecyclerViewAdapter
import org.cnx.openstaxcnxmusic.beans.Book
import org.cnx.openstaxcnxmusic.beans.BookList
import org.cnx.openstaxcnxmusic.helpers.JsonHelper

import java.util.ArrayList
import java.util.Collections


/**
 * Fragment for Display of list of books
 * @author Ed Woodward
 */
class LandingListFragment : Fragment()
{
    internal var recyclerView: RecyclerView? = null

    internal var activity: Activity? = null
    internal var layoutManager: LinearLayoutManager? = null

    private val content: ArrayList<Book>
        get()
        {
            val helper = JsonHelper()
            val bookList = helper.getBookData(activity!!, BookList::class.java, "bookList.json")
            if(bookList != null)
            {
                Collections.sort(bookList.bookList)
                return bookList.bookList!!
            }
            else
            {
                return ArrayList()
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        activity = getActivity()

        return inflater.inflate(R.layout.fragment_book_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        super.onActivityCreated(savedInstanceState)
        recyclerView = getView()!!.findViewById(R.id.list)
        layoutManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.isNestedScrollingEnabled = false
        val adapter = LandingListRecyclerViewAdapter(content, R.layout.fragment_book)
        recyclerView!!.adapter = adapter

        if(index != -1)
        {
            layoutManager!!.scrollToPositionWithOffset(index, top)
        }
    }

    override fun onPause()
    {
        super.onPause()
        index = layoutManager!!.findFirstVisibleItemPosition()
        val v = recyclerView!!.getChildAt(0)
        if(v == null)
        {
            top = 0
        }
        else
        {
            top = v.top - recyclerView!!.paddingTop
        }
    }

    override fun onStop()
    {
        super.onStop()
        index = layoutManager!!.findFirstVisibleItemPosition()
        val v = recyclerView!!.getChildAt(0)
        if(v == null)
        {
            top = 0
        }
        else
        {
            top = v.top - recyclerView!!.paddingTop
        }
    }

    override fun onDetach()
    {
        super.onDetach()
        index = layoutManager!!.findFirstVisibleItemPosition()
        val v = recyclerView!!.getChildAt(0)
        if(v == null)
        {
            top = 0
        }
        else
        {
            top = v.top - recyclerView!!.paddingTop
        }
    }

    override fun onResume()
    {
        super.onResume()
        if(index != -1)
        {
            layoutManager!!.scrollToPositionWithOffset(index, top)
        }
    }

    override fun onStart()
    {
        super.onStart()
        if(index != -1)
        {
            layoutManager!!.scrollToPositionWithOffset(index, top)
        }
    }

    companion object
    {

        var index = -1
        var top = -1
    }


}
