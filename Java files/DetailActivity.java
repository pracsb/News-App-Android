package com.example.myapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.MediaRouteButton;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.myapp.HomeFragment.EXTRA_BODY;
import static com.example.myapp.HomeFragment.EXTRA_DATE;
import static com.example.myapp.HomeFragment.EXTRA_ID;
import static com.example.myapp.HomeFragment.EXTRA_IMAGEURL;
import static com.example.myapp.HomeFragment.EXTRA_SECTION;
import static com.example.myapp.HomeFragment.EXTRA_SHAREURL;
import static com.example.myapp.HomeFragment.EXTRA_TITLE;

public class DetailActivity extends AppCompatActivity{
    private RequestQueue detailRequestQueue;
    private String article_id;
    private String title;
    private String imageUrl;
    private String section;
    private String date;
    private String shareUrl;
    private String full_body;
    public static ImageView imageView;
    public static TextView detail_title;
    public static TextView date_detail;
    public static TextView section_detail;
    public static TextView body_detail;
    public static TextView fullArticle_button;
    public static Toolbar detail_toolbar;
    public static ImageButton twitterShare;
    public static ImageButton bookmark_detail;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent =getIntent();
        article_id = intent.getStringExtra(EXTRA_ID);
        imageUrl = intent.getStringExtra(EXTRA_IMAGEURL);
        shareUrl = intent.getStringExtra(EXTRA_SHAREURL);
        title = intent.getStringExtra(EXTRA_TITLE);
        section = intent.getStringExtra(EXTRA_SECTION);
        date = intent.getStringExtra(EXTRA_DATE);
//        Instant instant = Instant.parse(date);
//        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("America/Los_Angeles"));
//        int day_val = zonedDateTime.getDayOfMonth();
//        int month_val = zonedDateTime.getDayOfMonth();
//        int year_val = zonedDateTime.getYear();
//        String day = "";
//        String month = "";
//        String year = "";
//        if(day_val<10)
//        {
//            day = "0"+Integer.toString(day_val);
//        }
//        String temp_month = Integer.toString(month_val).toLowerCase();
//        month = temp_month.substring(0,1).toUpperCase() + temp_month.substring(1);
//        String final_date = day + " " + month+ " " + year;
        full_body = intent.getStringExtra(EXTRA_BODY);

        imageView = findViewById(R.id.image_detail);
        detail_title = findViewById(R.id.title_detail);
        date_detail = findViewById(R.id.date_detail);
        section_detail = findViewById(R.id.section_detail);
        body_detail = findViewById(R.id.body_detail);
        fullArticle_button = findViewById(R.id.full_article_click);
        detail_toolbar = findViewById(R.id.Detail_toolbar);
        twitterShare = findViewById(R.id.Twitter_share);
        bookmark_detail = findViewById(R.id.bookmarkBtn);
        fullArticle_button.setPaintFlags(fullArticle_button.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        setSupportActionBar(detail_toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);



        Picasso.get().load(imageUrl).fit().centerCrop().into(imageView);
        detail_title.setText(title);
        date_detail.setText(date);
        section_detail.setText(section);
        body_detail.setText(Html.fromHtml(full_body));
        detail_toolbar.setTitle(title);


        twitterShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetUrl = "https://twitter.com/intent/tweet?text=Check out this link: \n"+shareUrl+"&hashtags=CSCI571NewsSearch";
                Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));
                startActivity(twitterIntent);
            }
        });


        fullArticle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the webURL and open the site for article
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse(shareUrl));
                startActivity(viewIntent);
            }
        });


        bookmark_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardItem clickedItem = new CardItem(imageUrl, title, article_id, shareUrl, date, date, section, full_body);
                String curr_title = clickedItem.getMtitle();
                String curr_id = clickedItem.getMarticle_id();
                Log.d("BK CLICKED", "YES BOOKMK IS CLICKED");
//                Toast.makeText(DetailActivity.this, "\""+curr_title+"\""+" was added to bookmarks", Toast.LENGTH_SHORT).show();
                ArrayList<CardItem> bookmark_items;
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                Gson gson = new Gson();
                String fetched = sharedPreferences.getString("Bookmarked", null);
                Type type = new TypeToken<ArrayList<CardItem>>() {}.getType();
                bookmark_items = gson.fromJson(fetched, type);
                if(bookmark_items==null)
                {
                    bookmark_items = new ArrayList<>();
                }
                else{
                    if(Integer.parseInt(bookmark_detail.getTag().toString())==1)
                    {
                        bookmark_detail.setBackgroundResource(R.drawable.ic_bookmark_border_black_24dp);
                        bookmark_detail.setTag(0);
                        Toast.makeText(DetailActivity.this,"\""+curr_title+"\""+ "was removed from Bookmarks", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        bookmark_detail.setBackgroundResource(R.drawable.ic_bookmark_black_24dp);
                        bookmark_detail.setTag(1);
                        Toast.makeText(DetailActivity.this, "\""+curr_title+"\""+" was added to bookmarks", Toast.LENGTH_SHORT).show();
                        bookmark_items.add(clickedItem);

                    }

//                    bookmark_items.add(clickedItem);
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                String json = gson.toJson(bookmark_items);
                Log.d("SHARED PREFERENCE :", json);
                editor.putString("Bookmarked", json);
                editor.apply();
            }
        });

    }


}

