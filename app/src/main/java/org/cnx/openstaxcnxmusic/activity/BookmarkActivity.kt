/**
 * Copyright (c) 2016 Rice University
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 2.1 (LGPL).  See LICENSE.txt for details.
 */
package org.cnx.openstaxcnxmusic.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View

import org.cnx.openstaxcnxmusic.helpers.MenuHelper
import org.cnx.openstaxcnxmusic.R
import org.cnx.openstaxcnxmusic.fragments.BookmarkFragment

/**
 * Activity for Bookmarks
 * @author Ed Woodward
 */
class BookmarkActivity : AppCompatActivity()
{
    public override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarks)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(getString(R.string.title_bookmarks))

        val transaction = supportFragmentManager.beginTransaction()
        val fragment = BookmarkFragment()
        transaction.replace(R.id.container, fragment)
        transaction.commit()

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
            val mh = MenuHelper()
            return mh.handleContextMenu(item.itemId, this, null)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_bookmarks, menu)
        return true

    }
}
