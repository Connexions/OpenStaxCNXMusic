/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.cnx.openstaxcnxmusic.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.cnx.openstaxcnxmusic.R;
import org.cnx.openstaxcnxmusic.adapters.LandingListRecyclerViewAdapter;
import org.cnx.openstaxcnxmusic.beans.Book;
import org.cnx.openstaxcnxmusic.beans.BookList;
import org.cnx.openstaxcnxmusic.helpers.JsonHelperKT;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Fragment for Display of list of books
 * @author Ed Woodward
 */
public class LandingListFragment extends Fragment
{
    RecyclerView recyclerView;

    Activity activity;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LandingListFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        activity = getActivity();

        return inflater.inflate(R.layout.fragment_book_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        LandingListRecyclerViewAdapter adapter = new LandingListRecyclerViewAdapter(getContent(), R.layout.fragment_book);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Book> getContent()
    {
        JsonHelperKT helper = new JsonHelperKT();
        BookList bookList = helper.getBookData(activity, BookList.class, "bookList.json");
        if(bookList != null)
        {
            Collections.sort(bookList.getBookList());
            return bookList.getBookList();
        }
        else
        {
            return new ArrayList<>();
        }
    }

}
