package com.example.myapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class BookmarksFragment extends Fragment implements BookmarkClickInterface{
    private View bookmark_fragment;
    private RecyclerView bRecyclerView;
    private RecyclerView.Adapter bAdapter;
    private RecyclerView.LayoutManager bLayoutManager;
    private ImageButton bookmarkRemove;
    public static final String EXTRA_ID = "article_id";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_IMAGEURL = "image_Url";
    public static final String EXTRA_SECTION = "section";
    public static final String EXTRA_DATE = "date";
    public static final String EXTRA_SHAREURL = "share_Url";
    public static final String EXTRA_BODY = "body";
    public GridLayout gridLayout;
    public RecyclerView bkRecyclerView;
    private ArrayList<CardItem> b_itemsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bookmark_fragment = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        gridLayout = bookmark_fragment.findViewById(R.id.myGridLayout);
        bkRecyclerView = bookmark_fragment.findViewById(R.id.bkRecyclerView);
        bkRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        bkRecyclerView.addItemDecoration(new DividerItemDecoration(bkRecyclerView.getContext(),DividerItemDecoration.VERTICAL));
        createBList();
//        buildRecyclerView();

        return bookmark_fragment;
    }

//
//    private void removeBookmarkedItem(int position)
//    {
//        String curr_title = b_itemsList.get(position).getB_title();
//        b_itemsList.remove(position);
//        Toast.makeText(getContext(),curr_title+" was removed from Bookmarks", Toast.LENGTH_SHORT).show();
//        bAdapter.notifyItemRemoved(position);
//    }
//
//    private void insertBookmarkedItem(){
//
//    }

    @SuppressLint("SetTextI18n")
    private void createBList(){
//        b_itemsList = new ArrayList<>();
//        b_itemsList.add(new BookmarkItem("Title2", "4 Apr", "null", "Tech", "jndfgjknfkfnbg", "wkewmk"));
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Bookmarked", null);
        Type type = new TypeToken<ArrayList<CardItem>>() {}.getType();
        b_itemsList = gson.fromJson(json, type);
        if(b_itemsList==null || b_itemsList.size()==0)
        {
            TextView nobk = bookmark_fragment.findViewById(R.id.no_bookmark_text);
            nobk.setVisibility(View.VISIBLE);


        }
        else{
            TextView nobk = bookmark_fragment.findViewById(R.id.no_bookmark_text);
            nobk.setVisibility(View.GONE);
        }
        buildRecyclerView();
    }

    private void buildRecyclerView(){

        bRecyclerView = bookmark_fragment.findViewById(R.id.bkRecyclerView);
//        bRecyclerView.setHasFixedSize(true);
//        bLayoutManager = new LinearLayoutManager(getContext());
        bAdapter = new BookmarkAdapterNew(getContext(), b_itemsList, this);
//        bRecyclerView.setLayoutManager(bLayoutManager);
        bRecyclerView.setAdapter(bAdapter);

    }


    @Override
    public void onBkClick(int position) {
        Intent detailedIntent = new Intent(getContext(), DetailActivity.class);
        CardItem clickedItem = b_itemsList.get(position);
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
    public void onBkLongClick(int position) {
        Log.d("Long CLick: ", "LONG CLICKED ");
        final Dialog dialog = new Dialog(getContext());
        final CardItem clickedItem = b_itemsList.get(position);
        dialog.setContentView(R.layout.dialog);

        final String curr_url = clickedItem.getMshareUrl();
        TextView title_d = dialog.findViewById(R.id.dialog_title);
        ImageView image_d = dialog.findViewById(R.id.dialog_image);
        ImageButton twitter_dialog = dialog.findViewById(R.id.dialog_twitter);
        ImageButton bookmark_dialog = dialog.findViewById(R.id.dialog_bookmark);

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
        dialog.show();
    }

    @Override
    public void removeBookmark(int position) {
        String curr_title = b_itemsList.get(position).getMtitle();
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        Gson gson = new Gson();
        String fetched = sharedPreferences.getString("Bookmarked", null);
        Type type = new TypeToken<ArrayList<CardItem>>() {}.getType();
        b_itemsList = gson.fromJson(fetched, type);
        if(b_itemsList==null)
        {
            b_itemsList = new ArrayList<>();
        }
        else{
            b_itemsList.remove(position);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(b_itemsList);
        Log.d("SHARED PREFERENCE :", json);
        editor.putString("Bookmarked", json);
        editor.apply();
        createBList();
        if(b_itemsList.size()==0)
        {
            TextView nobk = bookmark_fragment.findViewById(R.id.no_bookmark_text);
            nobk.setVisibility(View.VISIBLE);

        }
        bAdapter.notifyItemRemoved(position);
        String text = curr_title + " was removed from Bookmarks";
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
