/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.cnx.openstaxcnxmusic.helpers

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.webkit.WebView
import android.widget.Toast

import org.cnx.openstaxcnxmusic.R
import org.cnx.openstaxcnxmusic.activity.AboutActivity
import org.cnx.openstaxcnxmusic.activity.BookmarkActivity
import org.cnx.openstaxcnxmusic.beans.Book
import org.cnx.openstaxcnxmusic.providers.Bookmarks

/**
 * Handles menu selections
 * @author Ed Woodward
 */
class MenuHelper
{
    fun handleContextMenu(item: Int, context: Context, book: Book?): Boolean
    {
        when(item)
        {
            R.id.add_to_bm ->
            {
                val cv = ContentValues()

                //Log.d("MenuHandler","title - " + currentContent.getTitle())  ;
                cv.put(Bookmarks.TITLE, book!!.title)
                cv.put(Bookmarks.OTHER, book.bookTitle)
                //Log.d("MnHndlr.handleCont...()","URL: " + currentContent.getUrl().toString());
                val url = book.url
                cv.put(Bookmarks.URL, url!!.replace("@\\d+(\\.\\d+)?".toRegex(), "") + "?bookmark=1")
                context.contentResolver.insert(Bookmarks.CONTENT_URI, cv)
                Toast.makeText(context, "Bookmark added for " + book.title!!, Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.go_to_bm ->
            {
                val intent = Intent(context, BookmarkActivity::class.java)
                context.startActivity(intent)
                return true
            }
            R.id.viewAbout ->
            {
                val about = Intent(context, AboutActivity::class.java)
                context.startActivity(about)
                return true
            }
            R.id.viewLicense ->
            {
                displayLicensesAlert(context)
                return true
            }
            R.id.share ->
            {
                val shareintent = Intent(Intent.ACTION_SEND)
                shareintent.type = context.getString(R.string.mimetype_text)

                if(book != null)
                {
                    shareintent.putExtra(Intent.EXTRA_SUBJECT, book.bookTitle + " : " + book.title)
                    shareintent.putExtra(Intent.EXTRA_TEXT, book.url + "\n\n " + context.getString(R.string.shared_via))

                    val chooser = Intent.createChooser(shareintent, context.getString(R.string.tell_friend) + " " + book.bookTitle)
                    context.startActivity(chooser)
                }
                else
                {
                    Toast.makeText(context, context.getString(R.string.no_data_msg), Toast.LENGTH_LONG).show()
                }
                return true
            }
            else -> return false
        }

    }

    private fun displayLicensesAlert(context: Context)
    {
        val view = LayoutInflater.from(context).inflate(R.layout.license_dialog, null) as WebView
        view.loadUrl("file:///android_asset/licenses.html")
        val alertDialog = AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog)
                .setTitle(context.getString(R.string.license_title))
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .show()
    }
}
