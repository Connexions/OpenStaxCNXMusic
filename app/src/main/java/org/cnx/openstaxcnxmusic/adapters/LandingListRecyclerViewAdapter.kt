/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.cnx.openstaxcnxmusic.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import org.cnx.openstaxcnxmusic.R
import org.cnx.openstaxcnxmusic.activity.WebviewActivity
import org.cnx.openstaxcnxmusic.beans.Book

import java.util.ArrayList

/**
 * Adapter for displaying list of books
 * @author Ed Woodward
 */
class LandingListRecyclerViewAdapter(private val contentList: ArrayList<Book>?, private val rowLayout: Int) : RecyclerView.Adapter<LandingListRecyclerViewAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
        return ViewHolder(v, contentList!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val book = contentList!![position]
        //Log.d("Adapter", "book desc: " + book.getDesc());
        holder.bookTitle.text = book.bookTitle
        holder.desc.text = Html.fromHtml(book.desc)
    }

    override fun getItemCount(): Int
    {
        return contentList?.size ?: 0
    }

    inner class ViewHolder(itemView: View, internal var contentList: ArrayList<Book>) : RecyclerView.ViewHolder(itemView), View.OnClickListener
    {
        val bookTitle: TextView
        val desc: TextView

        init
        {
            bookTitle = itemView.findViewById(R.id.title)
            desc = itemView.findViewById(R.id.details)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View)
        {
            val position = adapterPosition
            val book = contentList[position]
            val context = v.context
            val wv = Intent(v.context, WebviewActivity::class.java)
            wv.putExtra(context.getString(R.string.webcontent), book)

            context.startActivity(wv)
        }
    }
}
