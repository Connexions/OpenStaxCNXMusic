/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.cnx.openstaxcnxmusic.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.cnx.openstaxcnxmusic.helpers.MenuHelper;
import org.cnx.openstaxcnxmusic.R;
import org.cnx.openstaxcnxmusic.beans.Book;
import org.cnx.openstaxcnxmusic.logic.WebviewLogic;

/**
 * Activity for viewing book content
 * @author Ed Woodward
 */
public class WebviewActivity extends AppCompatActivity
{
    private boolean progressBarRunning;
    private WebView webView;
    private Book book;

    private WebViewClient webViewClient = new WebViewClient()
    {
        @Override
        public void onLoadResource(WebView view, String url)
        {
            super.onLoadResource(view, url);

            //Log.d("WebViewClient.onLoadResource()", "Called");
        }

        /** loads URL into view */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            //Log.d("WebViewClient.shouldOverrideUrlLo()", "Called");
            if(!progressBarRunning)
            {
                setProgressBarIndeterminateVisibility(true);
            }
            view.loadUrl(url);
            book.setUrl(url);

            return true;
        }

        /* (non-Javadoc)
         * @see android.webkit.WebViewClient#onPageFinished(android.webkit.WebView, java.lang.String)
         * Sets title and URL correctly after the page is fully loaded
         */
        @Override
        public void onPageFinished(WebView view, String url)
        {
            //Log.d("WebViewClient.onPageFinished", "title: " + view.getTitle());
            //Log.d("WebViewClient.onPageFinished", "url: " + url);

            setProgressBarIndeterminateVisibility(false);
            progressBarRunning = false;
            book.setUrl(url);
            Log.d("webview", "webview itle: " + webView.getTitle());
            if(book.getBookTitle() == null)
            {
                WebviewLogic webviewLogic = new WebviewLogic();
                book.setBookTitle(webviewLogic.getBookTitle(webView.getTitle()));
            }
            //Log.d("WebViewClient.onPageFinished", "setSupportProgressBarIndeterminateVisibility(false) Called");

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        book = (Book)intent.getSerializableExtra("webcontent");
        if(!book.getUrl().contains("?bookmark=1"))
        {

            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.oscm_package), MODE_PRIVATE);
            String url = sharedPref.getString(book.getBookTitle(), "");

            if(!url.equals(""))
            {
                book.setUrl(url);
            }
        }
        else
        {
            //remove bookmark parameter
            String newURL = book.getUrl().replace("?bookmark=1","");
            book.setUrl(newURL);

        }
        //Log.d("onCreate()",book.getBookTitle());

        webView = (WebView)findViewById(R.id.web_view);
        setUpViews();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        super.onPrepareOptionsMenu(menu);
        //handle changing menu based on URL
        return onCreateOptionsMenu(menu);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     * Handles use of back button on browser
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(webView != null && ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()))
        {

            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        if(book == null)
        {
            return false;
        }

        menu.clear();
        inflater.inflate(R.menu.menu_webview, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            Intent mainIntent = new Intent(getApplicationContext(), LandingActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);
            return true;

        }
        else
        {
            if(book.getBookTitle() == null || book.getBookTitle().equals(""))
            {
                WebviewLogic webviewLogic = new WebviewLogic();
                book.setBookTitle(webviewLogic.getBookTitle(webView.getTitle()));
            }
            book.setTitle(webView.getTitle().replace(" - " + book.getBookTitle() + " - OpenStax CNX",""));
            book.setUrl(webView.getUrl());
            MenuHelper mh = new MenuHelper();
            return mh.handleContextMenu(item.getItemId(), this, book);
        }

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.oscm_package),MODE_PRIVATE);
        String url = sharedPref.getString(book.getBookTitle(), "");
        //Log.d("WebViewActivity.onResume()","URL retrieved: " + url);
        if(!url.equals(""))
        {
            book.setUrl(url);
        }

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.oscm_package),MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPref.edit();
        //Log.d("WVA.onPause()","URL saved: " + content.getUrl().toString());
        String url = webView.getUrl().replace("?bookmark=1","");
        ed.putString(book.getBookTitle(), url);
        ed.apply();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        //Log.d("ViewLenses.onSaveInstanceState()", "saving data");
        outState.putSerializable(getString(R.string.webcontent),book);
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.oscm_package),MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPref.edit();
        String url = webView.getUrl().replace("?bookmark=1","");
        ed.putString(book.getBookTitle(), url);
        ed.apply();

    }

    private void setUpViews()
    {

        //Log.d("WebViewView.setupViews()", "Called");
        webView = (WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setDefaultFontSize(17);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.setWebChromeClient(new WebChromeClient()
        {


        });

        webView.setWebViewClient(webViewClient);
        webView.loadUrl(book.getUrl());
    }


}
