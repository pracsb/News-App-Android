package com.example.myapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TechnologyTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TechnologyTab extends Fragment implements CardAdapter.OnItemClickListener, CardAdapter.OnLongClickListener, CardAdapter.OnBkClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View return_fragment_tech;
    private RecyclerView mRecyclerView;
    private CardAdapter mCardAdapter;
    private ArrayList<CardItem> mCardList;
    private ProgressBar progressBar;
    TextView progressText;
    RequestQueue mRequestQueue;
    SwipeRefreshLayout swipeRefreshLayout;
    public static final String EXTRA_ID = "article_id";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_IMAGEURL = "image_Url";
    public static final String EXTRA_SECTION = "section";
    public static final String EXTRA_DATE = "date";
    public static final String EXTRA_SHAREURL = "share_Url";
    public static final String EXTRA_BODY = "body";

    public TechnologyTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TechnologyTab.
     */
    // TODO: Rename and change types and number of parameters
    public static TechnologyTab newInstance(String param1, String param2) {
        TechnologyTab fragment = new TechnologyTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return_fragment_tech = inflater.inflate(R.layout.fragment_technology_tab, container, false);
        mRecyclerView = return_fragment_tech.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity().getApplicationContext()));
        progressBar = return_fragment_tech.findViewById(R.id.progressBar1);
        progressText = return_fragment_tech.findViewById(R.id.progress_text);
        swipeRefreshLayout = return_fragment_tech.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCardList = new ArrayList<>();
                mRequestQueue = Volley.newRequestQueue(requireActivity().getApplicationContext());
                parseJSON();
                mCardAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        mCardList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(requireActivity().getApplicationContext());
        parseJSON();
        return return_fragment_tech;
    }
    private void parseJSON(){
        String url = "https://hw8webtech.wl.r.appspot.com/guardian_tech";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        progressText.setText("");
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject results_array = jsonArray.getJSONObject(i);
                                if(!results_array.has("webTitle") || !results_array.has("blocks"))
                                {continue;}
                                String title = results_array.getString("webTitle");
//                               For image url from JSON resposne
                                String imageUrl;
                                JSONObject blocks = results_array.getJSONObject("blocks");
                                if(!blocks.has("main"))
                                {continue;}
                                JSONObject main = blocks.getJSONObject("main");
                                if(main.has("elements"))
                                {
                                    JSONArray elements = main.getJSONArray("elements");
                                    if(elements.length()==0)
                                    {
                                        imageUrl = "https://assets.guim.co.uk/images/eada8aa27c12fe2d5afa3a89d3fbae0d/fallback-logo.png";
                                    }
                                    else
                                    {
                                        JSONObject inside_elements = elements.getJSONObject(0);
                                        JSONArray assets = inside_elements.getJSONArray("assets");
                                        if (assets.length() == 0) {
                                            imageUrl = "https://assets.guim.co.uk/images/eada8aa27c12fe2d5afa3a89d3fbae0d/fallback-logo.png";
                                        } else {
                                            JSONObject inside_assets = assets.getJSONObject(0);
                                            imageUrl = inside_assets.getString("file");
                                        }
                                    }
                                }
                                else{
                                    imageUrl = "https://assets.guim.co.uk/images/eada8aa27c12fe2d5afa3a89d3fbae0d/fallback-logo.png";
                                }



//                               For section from JSON response

                                String section = results_array.getString("sectionName");
                                String article_id = results_array.getString("id");
                                String time = results_array.getString("webPublicationDate");
                                LocalDateTime localDateTime_curr = LocalDateTime.now();
                                ZoneId zoneId = ZoneId.of("America/Los_Angeles");
                                Instant instant = Instant.parse(time);
                                ZonedDateTime zonedDateTime = instant.atZone(zoneId);
                                ZonedDateTime zonedDateTime_curr = localDateTime_curr.atZone(zoneId);
                                Duration duration = Duration.between(zonedDateTime, zonedDateTime_curr);
                                Long total_seconds = duration.getSeconds();
                                int day_Secs = 3600*24;
                                int hr_secs = 3600;
                                int min_secs = 60;

                                String final_time = "";
                                if(total_seconds>0 && total_seconds>day_Secs)
                                {
                                    Long total_days = total_seconds/day_Secs;
                                    final_time = Long.toString(total_days)+"d ago";
                                }
                                else if(total_seconds>0 && total_seconds<day_Secs && total_seconds>hr_secs)
                                {
                                    Long total_hrs = total_seconds/hr_secs;
                                    final_time = Long.toString(total_hrs)+"h ago";
                                }
                                else if(total_seconds>0 && total_seconds<hr_secs && total_seconds> min_secs)
                                {
                                    Long total_mins = total_seconds/min_secs;
                                    final_time = Long.toString(total_mins)+"m ago";
                                }
                                else if(total_seconds>0 && total_seconds< min_secs)
                                {
                                    Long total_sec = total_seconds;
                                    final_time = Long.toString(total_sec)+"s ago";
                                }
                                else if(total_seconds<0)
                                {
                                    Long sec = total_seconds * -1;
                                    final_time = Long.toString(sec)+"s ago";

                                }
//                                String duration_str = duration.toString().substring(2);
//                                duration_str = duration_str.substring(0, duration_str.length()-1);
//                                String[] arr = duration_str.split("M");
//
//                                if(arr.length==1)
//                                {
//                                    int sec = (int)Float.parseFloat(arr[0]);
//                                    final_time = Integer.toString(sec)+"s ago";
//                                }
//                                else {
//                                    int first = Integer.parseInt(arr[0]);
//                                    float second = Float.parseFloat(arr[1]);
//                                    Log.d("DATE", "onResponse: Time: " + duration);
//                                    Log.d("Stripped", "onResponse:  " + first);
//                                    Log.d("SECOND", "seconds :" + second);
//                                    String initial = "";
//                                    int first_time = 1;
//                                    if (second < 1.0) {
//                                        initial = "s ago";
//                                        first_time = 0;
//                                    } else if (first < 0 && second > 1.0) {
//                                        initial = "s ago";
//                                        first_time = (int) second;
//                                    } else if (first > 0 && first < 59) {
//                                        initial = "m ago";
//                                        first_time = first;
//                                    } else if (first > 59) {
//                                        initial = "h ago";
//                                        first_time = 1;
//                                    }
//

//                                    final_time = Integer.toString(first_time) + initial;


                                int day_val = zonedDateTime.getDayOfMonth();
                                Log.d("DAY ", "onResponse: Day "+day_val);
                                int month_val = zonedDateTime.getMonthValue();
                                int year_val = zonedDateTime.getYear();
                                Log.d("MONTH ", "onResponse: Month "+month_val);
                                Log.d("YEAR ", "onResponse: year "+year_val);
                                String day = "";
                                String month = "";
                                String year = Integer.toString(year_val);
                                if(day_val<10)
                                {
                                    day = "0"+Integer.toString(day_val);
                                }
                                switch (month_val){
                                    case 1:month = "Jan";break;
                                    case 2:month = "Feb";break;
                                    case 3:month = "Mar";break;
                                    case 4:month = "Apr";break;
                                    case 5:month = "May";break;
                                    case 6:month = "Jun";break;
                                    case 7:month = "Jul";break;
                                    case 8:month = "Aug";break;
                                    case 9:month = "Sep";break;
                                    case 10:month = "Oct";break;
                                    case 11:month = "Nov";break;
                                    case 12:month = "Dec";break;

                                }
//                                String temp_month = Integer.toString(month_val).toLowerCase();
//                                month = temp_month.substring(0,1).toUpperCase() + temp_month.substring(1);
                                String final_date = day + " " + month+ " " + year;

                                String share_Url = results_array.getString("webUrl");
                                StringBuilder sb = new StringBuilder();
                                JSONArray full_body = blocks.getJSONArray("body");
                                for(int j=0;j<full_body.length();j++)
                                {
                                    JSONObject inside_body = full_body.getJSONObject(j);
                                    String body_html = inside_body.getString("bodyHtml");
                                    sb.append(body_html);
                                }
                                String body = sb.toString();
                                mCardList.add(new CardItem(imageUrl, title, article_id, share_Url, final_time, final_date, section, body));
                            }
                            Activity activity = getActivity();
                            if(activity != null){
                            mCardAdapter = new CardAdapter(getContext(),mCardList);
                            mRecyclerView.setAdapter(mCardAdapter);
                            mCardAdapter.setOnItemClickListener(TechnologyTab.this);
                            mCardAdapter.setOnLongClickListener(TechnologyTab.this);
                            mCardAdapter.setOnBkClickListener(TechnologyTab.this);}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        );
        mRequestQueue.add(request);
    }
    @Override
    public void onItemClick(int position) {
        Intent detailedIntent = new Intent(getContext(), DetailActivity.class);
        CardItem clickedItem = mCardList.get(position);
        detailedIntent.putExtra(EXTRA_ID, clickedItem.getMarticle_id());
        detailedIntent.putExtra(EXTRA_IMAGEURL, clickedItem.getMimageUrl());
        detailedIntent.putExtra(EXTRA_SECTION, clickedItem.getMsection());
        detailedIntent.putExtra(EXTRA_TITLE, clickedItem.getMtitle());
        detailedIntent.putExtra(EXTRA_DATE, clickedItem.getDate());
        detailedIntent.putExtra(EXTRA_SHAREURL, clickedItem.getMshareUrl());
        detailedIntent.putExtra(EXTRA_BODY, clickedItem.getMbody());

        startActivity(detailedIntent);
    }

    @Override
    public void onLongItemClick(final int position) {
        Log.d("Long CLick: ", "LONG CLICKED ");
        final Dialog dialog = new Dialog(getContext());
        final CardItem clickedItem = mCardList.get(position);
        dialog.setContentView(R.layout.dialog);

        final String curr_url = clickedItem.getMshareUrl();
        TextView title_d = dialog.findViewById(R.id.dialog_title);
        ImageView image_d = dialog.findViewById(R.id.dialog_image);
        ImageButton twitter_dialog = dialog.findViewById(R.id.dialog_twitter);
        final ImageButton bookmark_dialog = dialog.findViewById(R.id.dialog_bookmark);

        title_d.setText(clickedItem.getMtitle());
        Picasso.get().load(clickedItem.getMimageUrl()).fit().centerCrop().into(image_d);
        twitter_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetUrl = "https://twitter.com/intent/tweet?text=Check out this link: \n"+curr_url+"&hashtags=CSCI571NewsSearch";
                Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));
                startActivity(twitterIntent);
                dialog.dismiss();
            }
        });

        bookmark_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBkItemClick(position);
                if(Integer.parseInt(bookmark_dialog.getTag().toString())==1)
                {
                    bookmark_dialog.setBackgroundResource(R.drawable.ic_bookmark_border_black_24dp);
                    bookmark_dialog.setTag(0);
                    Toast.makeText(getContext(),"\""+clickedItem.getMtitle()+"\""+ "was removed from Bookmarks", Toast.LENGTH_SHORT).show();
                }
                else{
                    bookmark_dialog.setBackgroundResource(R.drawable.ic_bookmark_black_24dp);
                    bookmark_dialog.setTag(1);
                    Toast.makeText(getContext(), "\""+clickedItem.getMtitle()+"\""+" was added to bookmarks", Toast.LENGTH_SHORT).show();

                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onBkItemClick(int position) {
        final CardItem clickedItem = mCardList.get(position);
        String curr_title = clickedItem.getMtitle();
        String curr_id = clickedItem.getMarticle_id();
        Log.d("BK CLICKED", "YES BOOKMK IS CLICKED");
//        Toast.makeText(getContext(), "\""+curr_title +"\""+" was added to bookmarks", Toast.LENGTH_SHORT).show();
        ArrayList<CardItem> bookmark_items;
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        Gson gson = new Gson();
        String fetched = sharedPreferences.getString("Bookmarked", null);
        Type type = new TypeToken<ArrayList<CardItem>>() {}.getType();
        bookmark_items = gson.fromJson(fetched, type);
        if(bookmark_items==null)
        {
            bookmark_items = new ArrayList<>();
        }
        else{
            bookmark_items.add(clickedItem);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(bookmark_items);
        Log.d("SHARED PREFERENCE :", json);
        editor.putString("Bookmarked", json);
        editor.apply();
    }
}
