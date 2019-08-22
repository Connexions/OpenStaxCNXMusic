/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.cnx.openstaxcnxmusic.adapters

import android.support.v4.text.HtmlCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import org.cnx.openstaxcnxmusic.R
import org.cnx.openstaxcnxmusic.beans.About

import java.util.ArrayList

/**
 * Adapter for displaying cards for About Us information
 * @author Ed Woodward
 */
class AboutRecyclerViewAdapter(private val contentList: ArrayList<About>?, private val rowLayout: Int) : RecyclerView.Adapter<AboutRecyclerViewAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder
    {
        val v = LayoutInflater.from(viewGroup.context).inflate(rowLayout, viewGroup, false)
        return ViewHolder(v, contentList!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int)
    {
        val about = contentList!![i]
        //Log.d("rv", "title: " + about.title + " details: " + about.blurb)
        viewHolder.title.text = about.title
        viewHolder.blurb.text = HtmlCompat.fromHtml(about.blurb!!, HtmlCompat.FROM_HTML_MODE_LEGACY)


    }

    override fun getItemCount(): Int
    {
        return contentList?.size ?: 0
    }


    class ViewHolder(itemView: View, internal var contentList: ArrayList<About>) : RecyclerView.ViewHolder(itemView)
    {
        val title: TextView
        val blurb: TextView

        init
        {

            title = itemView.findViewById(R.id.title)
            blurb = itemView.findViewById(R.id.blurb)

        }

    }
}
