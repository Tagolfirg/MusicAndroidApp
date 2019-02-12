package com.diaze.musicapp;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements
        PlayListsFragment.OnFragmentInteractionListener,
        TrackListFragment.OnFragmentInteractionListener,
        AlbumListFragment.OnFragmentInteractionListener,
        ArtistListFragment.OnFragmentInteractionListener,
        GenreListFragment.OnFragmentInteractionListener{

    private DrawerLayout drawerLayout;
    private static final String TAG = "MainActivty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        drawerLayout = findViewById(R.id.layout_drawer);
        NavigationView nv = findViewById(R.id.nav_drawer);

        nv.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d(TAG,"drawer item selected! ");
                selectDrawerItem(item);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home: {
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectDrawerItem(MenuItem menuItem){
        Fragment fragment = null;
        Class fragmentClass = null;

        switch(menuItem.getItemId()) {
            case R.id.menu_item_playlists: {
                fragmentClass = PlayListsFragment.class;
                break;
            }
            case R.id.menu_item_tracks:{
                fragmentClass = TrackListFragment.class;
                break;
            }
            case R.id.menu_item_albums:{
                fragmentClass = AlbumListFragment.class;
                break;
            }
            case R.id.menu_item_artists:{
                fragmentClass = ArtistListFragment.class;
                break;
            }
            case R.id.menu_item_genres:{
                fragmentClass = GenreListFragment.class;
                break;
            }

        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentFrameLayout, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        drawerLayout.closeDrawers();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
