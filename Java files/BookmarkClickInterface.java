package com.example.myapp;

public interface BookmarkClickInterface {
    void onBkClick(int position);
    void onBkLongClick(int position);
    void removeBookmark(int position);
}
