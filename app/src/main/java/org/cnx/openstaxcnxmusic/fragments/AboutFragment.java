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
import org.cnx.openstaxcnxmusic.adapters.AboutRecyclerViewAdapter;
import org.cnx.openstaxcnxmusic.beans.About;
import org.cnx.openstaxcnxmusic.beans.AboutList;
import org.cnx.openstaxcnxmusic.helpers.JsonHelperKT;

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
        recyclerView = getView().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new AboutRecyclerViewAdapter(getContent(), R.layout.about_row);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<About> getContent()
    {
        JsonHelperKT helper = new JsonHelperKT();
        AboutList aboutList = helper.getAboutData(activity, AboutList.class, "aboutList.json");
        if(aboutList != null)
        {
            return aboutList.getAboutList();
        }
        else
        {
            return new ArrayList<>();
        }
    }

}
