package org.cnx.openstaxcnxmusic.adapters;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.cnx.openstaxcnxmusic.R;
import org.cnx.openstaxcnxmusic.activity.WebviewActivity;
import org.cnx.openstaxcnxmusic.beans.Book;
import org.cnx.openstaxcnxmusic.providers.Bookmarks;

import java.util.ArrayList;

import co.paulburke.android.itemtouchhelperdemo.helper.ItemTouchHelperAdapter;

/**
 * Created by ew2 on 7/21/16.
 */
public class BookmarkRecyclerViewAdapter extends RecyclerView.Adapter<BookmarkRecyclerViewAdapter.ViewHolder> implements ItemTouchHelperAdapter
{
    private ArrayList<Book> contentList;
    Book content;
    static Context CONTEXT;
    private int rowLayout;

    public BookmarkRecyclerViewAdapter(ArrayList<Book> content, int rowLayout, Context context)
    {
        contentList = content;
        this.rowLayout = rowLayout;
        CONTEXT = context;
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
        content = contentList.get(i);
        viewHolder.title.setText(content.getTitle());
        viewHolder.desc.setText(content.getDesc());


    }

    @Override
    public int getItemCount()
    {
        return contentList == null ? 0 : contentList.size();
    }

    @Override
    public void onItemDismiss(int position)
    {
        Book currentContent = contentList.get(position);
        CONTEXT.getContentResolver().delete(Bookmarks.CONTENT_URI, "_id="+ currentContent.getId(), null);
        contentList.remove(position);
        notifyItemRemoved(position);
        Toast.makeText(CONTEXT, "Bookmark deleted for " + currentContent.getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition)
    {
        return true;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public ImageView logo;
        public TextView title;
        public TextView desc;
        public View view;
        ArrayList<Book> contentList;

        public ViewHolder(View itemView, ArrayList<Book> contentList)
        {
            super(itemView);
            view = itemView;
            this.contentList = contentList;

            logo = (ImageView) itemView.findViewById(R.id.logoView);
            title = (TextView)itemView.findViewById(R.id.bookName);
            desc = (TextView)itemView.findViewById(R.id.desc);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                StateListAnimator stateListAnimator = AnimatorInflater.loadStateListAnimator(CONTEXT, R.anim.lift_on_touch);
                itemView.setStateListAnimator(stateListAnimator);
            }
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v)
        {
            Book content = contentList.get(getAdapterPosition());
            Context context = v.getContext();
            Intent wv = new Intent(v.getContext(), WebviewActivity.class);
            wv.putExtra(v.getContext().getString(R.string.webcontent), content);

            context.startActivity(wv);
        }


    }

}
