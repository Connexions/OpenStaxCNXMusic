/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.cnx.openstaxcnxmusic.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.cnx.openstaxcnxmusic.R;
import org.cnx.openstaxcnxmusic.adapters.BookmarkRecyclerViewAdapter;
import org.cnx.openstaxcnxmusic.beans.Book;
import org.cnx.openstaxcnxmusic.providers.Bookmarks;
import org.cnx.openstaxcnxmusic.providers.DBUtils;

import java.util.ArrayList;
import java.util.Collections;

import co.paulburke.android.itemtouchhelperdemo.helper.OnStartDragListener;
import co.paulburke.android.itemtouchhelperdemo.helper.SimpleItemTouchHelperCallback;

/**
 * Fragment for display of Bookmarks
 * @author Ed Woodward
 */
public class BookmarkFragment extends Fragment implements OnStartDragListener
{
    BookmarkRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Book> content;

    private Handler handler = new Handler();

    private ItemTouchHelper itemTouchHelper;

    Activity activity;

    private Runnable finishedLoadingListTask = new Runnable()
    {
        public void run()
        {
            finishedLoadingList();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        activity = getActivity();
        View v = inflater.inflate(R.layout.card_view, container, false);


        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView)getView().findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);


        //get already retrieved feed and reuse if it is there
        content = (ArrayList<Book>)activity.getLastNonConfigurationInstance();
        if(content == null)
        {
            //no previous data, so database must be read
            readDB();
        }
        else
        {
            //reuse existing feed data
            adapter = new BookmarkRecyclerViewAdapter(content, R.layout.card_row, activity);
            recyclerView.setAdapter(adapter);
            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
            itemTouchHelper = new ItemTouchHelper(callback);
            itemTouchHelper.attachToRecyclerView(recyclerView);

        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder)
    {
        itemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //if database state has changed, reload the display
        if(content != null)
        {
            int dbCount = getDBCount();

            if(dbCount >  content.size())
            {
                readDB();
            }
        }
    }

    protected void finishedLoadingList()
    {
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /** reads feed in a separate thread.  Starts progress dialog*/
    private void readDB()
    {
        Thread loadFavsThread = new Thread()
        {
            public void run()
            {

                String order = "bm_title ASC";

                content = DBUtils.readCursorIntoList(activity.getContentResolver().query(Bookmarks.CONTENT_URI, null, null, null, order));

                Collections.sort(content);

                fillData(content);
                handler.post(finishedLoadingListTask);
            }
        };
        loadFavsThread.start();

    }
    /**
     * Loads feed data into adapter on initial reading of feed
     * @param contentList ArrayList<Content>
     */
    private void fillData(ArrayList<Book> contentList)
    {
        //Log.d("LensViewer", "fillData() called");
        adapter = new BookmarkRecyclerViewAdapter(contentList, R.layout.card_row,activity);
    }

    /**
     * Queries the database to get the number of favorites stored
     * @return int - the number of favorites items in the database
     */
    private int getDBCount()
    {
        Cursor c = activity.getContentResolver().query(Bookmarks.CONTENT_URI, null, null, null, null);
        int count = c.getCount();
        c.close();
        return count;

    }


}
