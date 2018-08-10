/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.cnx.openstaxcnxmusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.cnx.openstaxcnxmusic.R;
import org.cnx.openstaxcnxmusic.activity.WebviewActivity;
import org.cnx.openstaxcnxmusic.beans.Book;

import java.util.ArrayList;

/**
 * Adapter for displaying list of books
 * @author Ed Woodward
 */
public class LandingListRecyclerViewAdapter extends RecyclerView.Adapter<LandingListRecyclerViewAdapter.ViewHolder>
{

    private int rowLayout;
    private ArrayList<Book> contentList;

    public LandingListRecyclerViewAdapter(ArrayList<Book> content, int rowLayout)
    {
        contentList = content;
        this.rowLayout = rowLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(v,contentList);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        Book book = contentList.get(position);
        //Log.d("Adapter", "book desc: " + book.getDesc());
        holder.bookTitle.setText(book.getBookTitle());
        holder.desc.setText(Html.fromHtml(book.getDesc()));
    }

    @Override
    public int getItemCount()
    {
        return contentList == null ? 0 : contentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView bookTitle;
        private TextView desc;
        ArrayList<Book> contentList;

        public ViewHolder(View itemView, ArrayList<Book> contentList)
        {
            super(itemView);
            this.contentList = contentList;
            bookTitle = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.details);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int position = getAdapterPosition();
            Book book = contentList.get(position);
            Context context = v.getContext();
            Intent wv = new Intent(v.getContext(), WebviewActivity.class);
            wv.putExtra(context.getString(R.string.webcontent), book);

            context.startActivity(wv);
        }
    }
}
