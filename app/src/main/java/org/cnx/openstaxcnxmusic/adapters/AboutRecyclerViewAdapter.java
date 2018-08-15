/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.cnx.openstaxcnxmusic.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.cnx.openstaxcnxmusic.R;
import org.cnx.openstaxcnxmusic.beans.About;

import java.util.ArrayList;

/**
 * Adapter for displaying cards for About Us information
 * @author Ed Woodward
 */
public class AboutRecyclerViewAdapter extends RecyclerView.Adapter<AboutRecyclerViewAdapter.ViewHolder>
{
    private int rowLayout;
    private ArrayList<About> contentList;

    public AboutRecyclerViewAdapter(ArrayList<About> content, int rowLayout)
    {
        contentList = content;
        this.rowLayout = rowLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v,contentList);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i)
    {
        About about = contentList.get(i);
        Log.d("rv","title: " + about.getTitle() + " details: "+ about.getBlurb());
        viewHolder.title.setText(about.getTitle());
        viewHolder.blurb.setText(Html.fromHtml(about.getBlurb()));


    }

    @Override
    public int getItemCount()
    {
        return contentList == null ? 0 : contentList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title;
        private TextView blurb;
        ArrayList<About> contentList;

        public ViewHolder(View itemView, ArrayList<About> contentList)
        {
            super(itemView);
            this.contentList = contentList;

            title = itemView.findViewById(R.id.title);
            blurb = itemView.findViewById(R.id.blurb);

        }

    }
}
