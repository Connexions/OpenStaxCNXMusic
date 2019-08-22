/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.cnx.openstaxcnxmusic.fragments

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.cnx.openstaxcnxmusic.R
import org.cnx.openstaxcnxmusic.adapters.BookmarkRecyclerViewAdapter
import org.cnx.openstaxcnxmusic.beans.Book
import org.cnx.openstaxcnxmusic.providers.Bookmarks
import org.cnx.openstaxcnxmusic.providers.DBUtils

import java.util.ArrayList
import java.util.Collections

import co.paulburke.android.itemtouchhelperdemo.helper.OnStartDragListener
import co.paulburke.android.itemtouchhelperdemo.helper.SimpleItemTouchHelperCallback

/**
 * Fragment for display of Bookmarks
 * @author Ed Woodward
 */
class BookmarkFragment : Fragment(), OnStartDragListener
{
    internal var adapter: BookmarkRecyclerViewAdapter? = null
    internal var recyclerView: RecyclerView? = null
    internal var content: ArrayList<Book>? = null

    private val handler = Handler()

    private var itemTouchHelper: ItemTouchHelper? = null

    internal var activity: Activity? = null

    private val finishedLoadingListTask = Runnable { finishedLoadingList() }

    /**
     * Queries the database to get the number of favorites stored
     * @return int - the number of favorites items in the database
     */
    private val dbCount: Int
        get()
        {
            val c = activity!!.contentResolver.query(Bookmarks.CONTENT_URI, null, null, null, null)
            val count = c!!.count
            c.close()
            return count

        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        activity = getActivity()


        return inflater.inflate(R.layout.card_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        recyclerView = view!!.findViewById(R.id.recycler_view)

        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.isNestedScrollingEnabled = false


        //get already retrieved feed and reuse if it is there
        content = activity!!.lastNonConfigurationInstance as ArrayList<Book>?
        if(content == null)
        {
            //no previous data, so database must be read
            //Log.d("Bookmark", "content is null");
            readDB()
        }
        else
        {
            //reuse existing feed data
            adapter = BookmarkRecyclerViewAdapter(content, R.layout.card_row, activity!!)
            recyclerView!!.adapter = adapter
            val callback = SimpleItemTouchHelperCallback(adapter)
            itemTouchHelper = ItemTouchHelper(callback)
            itemTouchHelper!!.attachToRecyclerView(recyclerView)

        }
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    {
        itemTouchHelper!!.startDrag(viewHolder)
    }

    override fun onResume()
    {
        super.onResume()
        //if database state has changed, reload the display
        if(content != null)
        {
            val dbCount = dbCount

            if(dbCount > content!!.size)
            {
                readDB()
            }
        }
    }

    protected fun finishedLoadingList()
    {
        //Log.d("Bookmark", "finishLoadingList() called");
        recyclerView!!.adapter = adapter
        val callback = SimpleItemTouchHelperCallback(adapter)
        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper!!.attachToRecyclerView(recyclerView)
    }

    /** reads feed in a separate thread.  Starts progress dialog */
    private fun readDB()
    {
        //Log.d("Bookmark", "readDB() called");
        val loadFavsThread = object : Thread()
        {
            override fun run()
            {

                val order = "bm_title ASC"

                content = DBUtils.readCursorIntoList(activity!!.contentResolver.query(Bookmarks.CONTENT_URI, null, null, null, order)!!)

                Collections.sort(content)

                fillData(content)
                handler.post(finishedLoadingListTask)
            }
        }
        loadFavsThread.start()

    }

    /**
     * Loads feed data into adapter on initial reading of feed
     * @param contentList ArrayList<Content>
    </Content> */
    private fun fillData(contentList: ArrayList<Book>?)
    {
        //Log.d("Bookmark", "fillData() called");
        adapter = BookmarkRecyclerViewAdapter(contentList, R.layout.card_row, activity!!)
    }


}
