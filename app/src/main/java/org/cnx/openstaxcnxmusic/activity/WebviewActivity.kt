/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.cnx.openstaxcnxmusic.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

import org.cnx.openstaxcnxmusic.helpers.MenuHelper
import org.cnx.openstaxcnxmusic.R
import org.cnx.openstaxcnxmusic.beans.Book
import org.cnx.openstaxcnxmusic.logic.WebviewLogic

/**
 * Activity for viewing book content
 * @author Ed Woodward
 */
class WebviewActivity : AppCompatActivity()
{
    private var webView: WebView? = null
    private var book: Book? = null

    private val webViewClient = object : WebViewClient()
    {
        /** loads URL into view  */
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean
        {
            view.loadUrl(url)
            book!!.url = url

            return true
        }

        /* (non-Javadoc)
         * @see android.webkit.WebViewClient#onPageFinished(android.webkit.WebView, java.lang.String)
         * Sets title and URL correctly after the page is fully loaded
         */
        override fun onPageFinished(view: WebView, url: String)
        {
            //Log.d("WebViewClient.onPageFinished", "title: " + view.getTitle());
            //Log.d("WebViewClient.onPageFinished", "url: " + url);

            book!!.url = url
            Log.d("webview", "webview itle: " + webView!!.title)
            if(book!!.bookTitle == null)
            {
                val webviewLogic = WebviewLogic()
                book!!.bookTitle = webviewLogic.getBookTitle(webView!!.title)
            }
            //Log.d("WebViewClient.onPageFinished", "setSupportProgressBarIndeterminateVisibility(false) Called");

        }

    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        book = intent.getSerializableExtra("webcontent") as Book
        if(!book!!.url!!.contains("?bookmark=1"))
        {

            val sharedPref = getSharedPreferences(getString(R.string.oscm_package), Context.MODE_PRIVATE)
            val url = sharedPref.getString(book!!.bookTitle, "")

            if(url != "")
            {
                book!!.url = url
            }
        }
        else
        {
            //remove bookmark parameter
            val newURL = book!!.url!!.replace("?bookmark=1", "")
            book!!.url = newURL

        }
        //Log.d("onCreate()",book.getBookTitle());

        webView = findViewById<View>(R.id.web_view) as WebView
        setUpViews()
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean
    {
        super.onPrepareOptionsMenu(menu)
        //handle changing menu based on URL
        return onCreateOptionsMenu(menu)
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     * Handles use of back button on browser
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean
    {
        if(webView != null && keyCode == KeyEvent.KEYCODE_BACK && webView!!.canGoBack())
        {

            webView!!.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        val inflater = menuInflater
        if(book == null)
        {
            return false
        }

        menu.clear()
        inflater.inflate(R.menu.menu_webview, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if(item.itemId == android.R.id.home)
        {
            val mainIntent = Intent(applicationContext, LandingActivity::class.java)
            mainIntent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(mainIntent)
            return true

        }
        else
        {
            if(book!!.bookTitle == null || book!!.bookTitle == "")
            {
                val webviewLogic = WebviewLogic()
                book!!.bookTitle = webviewLogic.getBookTitle(webView!!.title)
            }
            book!!.title = webView!!.title.replace(" - " + book!!.bookTitle + " - OpenStax CNX", "")
            book!!.url = webView!!.url
            val mh = MenuHelper()
            return mh.handleContextMenu(item.itemId, this, book)
        }

    }

    override fun onResume()
    {
        super.onResume()
        val sharedPref = getSharedPreferences(getString(R.string.oscm_package), Context.MODE_PRIVATE)
        val url = sharedPref.getString(book!!.bookTitle, "")
        //Log.d("WebViewActivity.onResume()","URL retrieved: " + url);
        if(url != "")
        {
            book!!.url = url
        }

    }

    override fun onPause()
    {
        super.onPause()
        val sharedPref = getSharedPreferences(getString(R.string.oscm_package), Context.MODE_PRIVATE)
        val ed = sharedPref.edit()
        //Log.d("WVA.onPause()","URL saved: " + content.getUrl().toString());
        val url = webView!!.url.replace("?bookmark=1", "")
        ed.putString(book!!.bookTitle, url)
        ed.apply()
    }


    override fun onSaveInstanceState(outState: Bundle)
    {
        super.onSaveInstanceState(outState)
        //Log.d("ViewLenses.onSaveInstanceState()", "saving data");
        outState.putSerializable(getString(R.string.webcontent), book)
        val sharedPref = getSharedPreferences(getString(R.string.oscm_package), Context.MODE_PRIVATE)
        val ed = sharedPref.edit()
        val url = webView!!.url.replace("?bookmark=1", "")
        ed.putString(book!!.bookTitle, url)
        ed.apply()

    }

    private fun setUpViews()
    {

        //Log.d("WebViewView.setupViews()", "Called");
        webView = findViewById<View>(R.id.web_view) as WebView
        webView!!.settings.javaScriptEnabled = true
        webView!!.settings.setSupportZoom(true)
        webView!!.settings.builtInZoomControls = true
        webView!!.settings.loadWithOverviewMode = true
        webView!!.settings.useWideViewPort = true

        webView!!.webChromeClient = object : WebChromeClient()
        {


        }

        webView!!.webViewClient = webViewClient
        webView!!.loadUrl(book!!.url)
    }


}
