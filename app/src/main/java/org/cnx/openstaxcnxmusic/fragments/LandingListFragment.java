package org.cnx.openstaxcnxmusic.fragments;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.cnx.openstaxcnxmusic.R;
import org.cnx.openstaxcnxmusic.adapters.LandingListRecyclerViewAdapter;
import org.cnx.openstaxcnxmusic.beans.Book;
import org.cnx.openstaxcnxmusic.beans.BookList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;


/**
 *
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
        recyclerView = (RecyclerView)getView().findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        LandingListRecyclerViewAdapter adapter = new LandingListRecyclerViewAdapter(getContent(), R.layout.fragment_book, activity);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Book> getContent()
    {
        BookList bookList = readJson();
        return bookList.getBookList();
    }

    private BookList readJson()
    {
        AssetManager assets = getActivity().getAssets();
        BookList aboutList = new BookList();

        Gson gson = new Gson();

        try
        {
            InputStream is = assets.open("bookList.json");
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
            aboutList = gson.fromJson(bf,BookList.class);
        }
        catch(IOException ioe)
        {
            Log.d("json", "Some problem: " + ioe.toString());
        }

        Collections.sort(aboutList.getBookList());

        return aboutList;
    }



}
