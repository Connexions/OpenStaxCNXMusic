/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.cnx.openstaxcnxmusic.adapters

import android.animation.AnimatorInflater
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import org.cnx.openstaxcnxmusic.R
import org.cnx.openstaxcnxmusic.activity.WebviewActivity
import org.cnx.openstaxcnxmusic.beans.Book
import org.cnx.openstaxcnxmusic.providers.Bookmarks

import java.util.ArrayList

import co.paulburke.android.itemtouchhelperdemo.helper.ItemTouchHelperAdapter

/**
 * Adapter for displaying Bookmark cards
 * @author Ed Woodward
 */
class BookmarkRecyclerViewAdapter(private val contentList: ArrayList<Book>?, private val rowLayout: Int, private val context: Context) : RecyclerView.Adapter<BookmarkRecyclerViewAdapter.ViewHolder>(), ItemTouchHelperAdapter
{

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder
    {
        val v = LayoutInflater.from(viewGroup.context).inflate(rowLayout, viewGroup, false)
        return ViewHolder(v, contentList!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int)
    {
        val content = contentList!![i]
        viewHolder.title.text = content.title
        viewHolder.desc.text = content.desc


    }

    override fun getItemCount(): Int
    {
        return contentList?.size ?: 0
    }

    override fun onItemDismiss(position: Int)
    {
        val currentContent = contentList!![position]
        context.contentResolver.delete(Bookmarks.CONTENT_URI, "_id=" + currentContent.id, null)
        contentList.removeAt(position)
        notifyItemRemoved(position)
        Toast.makeText(context, "Bookmark deleted for " + currentContent.title, Toast.LENGTH_SHORT).show()
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean
    {
        return true
    }

    inner class ViewHolder(var view: View, internal var contentList: ArrayList<Book>) : RecyclerView.ViewHolder(view), View.OnClickListener
    {
        var logo: ImageView
        var title: TextView
        var desc: TextView

        init
        {

            logo = view.findViewById(R.id.logoView)
            title = view.findViewById(R.id.bookName)
            desc = view.findViewById(R.id.desc)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                val stateListAnimator = AnimatorInflater.loadStateListAnimator(context, R.anim.lift_on_touch)
                view.stateListAnimator = stateListAnimator
            }
            view.setOnClickListener(this)
        }


        override fun onClick(v: View)
        {
            val content = contentList[adapterPosition]
            val context = v.context
            val wv = Intent(v.context, WebviewActivity::class.java)
            wv.putExtra(v.context.getString(R.string.webcontent), content)

            context.startActivity(wv)
        }


    }

}
