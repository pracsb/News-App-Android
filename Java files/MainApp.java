package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainApp extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);


        BottomNavigationView bottomNav = findViewById(R.id.Home_bottom_Navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container, new HomeFragment()).commit();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        MenuItem searchitem = menu.findItem(R.id.search_toolbar);

        SearchView search = (SearchView) MenuItemCompat.getActionView(searchitem);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        assert searchManager != null;
        search.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, Search.class)));
        search.setQueryHint(getResources().getString(R.string.search_hint));

        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.bottom_home:
                            selectedFragment = new HomeFragment();
                            break;

                        case R.id.bottom_headlines:
                            selectedFragment = new HeadlinesFragment();
                            break;

                        case R.id.bottom_trending:
                            selectedFragment = new TrendingFragment();
                            break;

                        case R.id.bottom_bookmarks:
                            selectedFragment = new BookmarksFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

}

