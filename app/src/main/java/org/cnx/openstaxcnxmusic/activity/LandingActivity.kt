/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.cnx.openstaxcnxmusic.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v4.text.HtmlCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import org.cnx.openstaxcnxmusic.fragments.BookmarkFragment
import org.cnx.openstaxcnxmusic.helpers.MenuHelper
import org.cnx.openstaxcnxmusic.R
import org.cnx.openstaxcnxmusic.fragments.LandingListFragment

/**
 * Activity for initial screen
 * @author Ed Woodward
 */
class LandingActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val toolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)
        toolbarLayout.title = HtmlCompat.fromHtml(getString(R.string.title_activity_landing), HtmlCompat.FROM_HTML_MODE_LEGACY)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when(item.itemId)
            {
                R.id.books_nav -> selectedFragment = LandingListFragment()
                R.id.bookmarks_nav ->
                {
                    //Log.d("Landing", "bookmark selected")
                    selectedFragment = BookmarkFragment()
                }
            }
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, selectedFragment!!)
            transaction.commit()
            true
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, LandingListFragment())
        transaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        val mh = MenuHelper()
        return mh.handleContextMenu(item.itemId, this, null)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_about, menu)
        return true

    }
}
