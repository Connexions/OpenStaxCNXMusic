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
import org.cnx.openstaxcnxmusic.adapters.AboutRecyclerViewAdapter
import org.cnx.openstaxcnxmusic.beans.About
import org.cnx.openstaxcnxmusic.beans.AboutList
import org.cnx.openstaxcnxmusic.helpers.JsonHelper

import java.util.ArrayList

/**
 * Fragment for display of About Us information
 * @author Ed Woodward
 */
class AboutFragment : Fragment()
{
    internal var recyclerView: RecyclerView? = null
    internal var activity: Activity? = null
    internal var adapter: AboutRecyclerViewAdapter? = null

    private val content: ArrayList<About>
        get()
        {
            val helper = JsonHelper()
            val aboutList = helper.getAboutData(activity!!, AboutList::class.java, "aboutList.json")
            return aboutList?.aboutList ?: ArrayList()
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
        adapter = AboutRecyclerViewAdapter(content, R.layout.about_row)
        recyclerView!!.adapter = adapter
    }

}
