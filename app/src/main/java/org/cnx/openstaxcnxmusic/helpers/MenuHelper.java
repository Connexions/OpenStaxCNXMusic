/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.cnx.openstaxcnxmusic.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.widget.Toast;

import org.cnx.openstaxcnxmusic.R;
import org.cnx.openstaxcnxmusic.activity.AboutActivity;
import org.cnx.openstaxcnxmusic.activity.BookmarkActivity;
import org.cnx.openstaxcnxmusic.beans.Book;
import org.cnx.openstaxcnxmusic.providers.Bookmarks;

/**
 * Handles menu selections
 * @author Ed Woodward
 */
public class MenuHelper
{
    public boolean handleContextMenu(int item, Context context, Book book)
    {
        switch (item)
        {
            case R.id.add_to_bm:
                ContentValues cv = new ContentValues();

                //Log.d("MenuHandler","title - " + currentContent.getTitle())  ;
                cv.put(Bookmarks.TITLE, book.getTitle());
                cv.put(Bookmarks.OTHER, book.getBookTitle());
                //Log.d("MnHndlr.handleCont...()","URL: " + currentContent.getUrl().toString());
                String url = book.getUrl();
                cv.put(Bookmarks.URL, url.replaceAll("@\\d+(\\.\\d+)?","")+ "?bookmark=1");
                context.getContentResolver().insert(Bookmarks.CONTENT_URI, cv);
                Toast.makeText(context, "Bookmark added for " + book.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.go_to_bm:
                Intent intent = new Intent(context, BookmarkActivity.class);
                context.startActivity(intent);
                return true;
            case R.id.viewAbout:
                Intent about = new Intent(context, AboutActivity.class);
                context.startActivity(about);
                return true;
            case R.id.viewLicense:
                displayLicensesAlert(context);
                return true;
            case R.id.share:
                Intent shareintent = new Intent(Intent.ACTION_SEND);
                shareintent.setType(context.getString(R.string.mimetype_text));

                if(book != null)
                {
                    shareintent.putExtra(Intent.EXTRA_SUBJECT, book.getBookTitle() + " : " + book.getTitle());
                    shareintent.putExtra(Intent.EXTRA_TEXT, book.getUrl() + "\n\n " + context.getString(R.string.shared_via));

                    Intent chooser = Intent.createChooser(shareintent, context.getString(R.string.tell_friend) + " "+ book.getBookTitle());
                    context.startActivity(chooser);
                }
                else
                {
                    Toast.makeText(context, context.getString(R.string.no_data_msg),  Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return false;
        }

    }

    private void displayLicensesAlert(Context context)
    {
        WebView view = (WebView) LayoutInflater.from(context).inflate(R.layout.license_dialog, null);
        view.loadUrl("file:///android_asset/licenses.html");
        AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog)
                .setTitle(context.getString(R.string.license_title))
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}
