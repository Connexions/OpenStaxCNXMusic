/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
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
import org.cnx.openstaxcnxmusic.adapters.AboutRecyclerViewAdapter;
import org.cnx.openstaxcnxmusic.beans.About;
import org.cnx.openstaxcnxmusic.beans.AboutList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Fragment for display of About Us information
 * @author Ed Woodward
 */
public class AboutFragment extends Fragment
{
    RecyclerView recyclerView;
    Activity activity;
    AboutRecyclerViewAdapter adapter;

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
        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new AboutRecyclerViewAdapter(getContent(), R.layout.about_row, activity);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<About> getContent()
    {
        AboutList aboutList = readJson();
        return aboutList.getAboutList();
    }

    private AboutList readJson()
    {
        AssetManager assets = getActivity().getAssets();
        AboutList aboutList = new AboutList();

        Gson gson = new Gson();

        try
        {
            InputStream is = assets.open("aboutList.json");
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
            aboutList = gson.fromJson(bf,AboutList.class);
        }
        catch(IOException ioe)
        {
            Log.d("json", "Some problem: " + ioe.toString());
        }

        return aboutList;
    }

}
