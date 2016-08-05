package org.cnx.openstaxcnxmusic.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.cnx.openstaxcnxmusic.helpers.MenuHelper;
import org.cnx.openstaxcnxmusic.R;
import org.cnx.openstaxcnxmusic.fragments.LandingListFragment;

public class LandingActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(Html.fromHtml(getString(R.string.title_activity_landing)));

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            LandingListFragment fragment = new LandingListFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
        final Context context = this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent = new Intent(context, BookmarkActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        MenuHelper mh = new MenuHelper();
        return mh.handleContextMenu(item.getItemId(), this, null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;

    }
}
