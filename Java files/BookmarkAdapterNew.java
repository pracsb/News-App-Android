package com.example.myapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookmarkAdapterNew extends RecyclerView.Adapter<BookmarkAdapterNew.BookmarkHolder> {
    private Context bcontext;
    public ArrayList<CardItem> bExampleList;
    private BookmarkClickInterface bookmarkClickInterface;
    ViewParent view_parent;

    BookmarkAdapterNew(Context context, ArrayList<CardItem> exampleList, BookmarkClickInterface mbookmarkClickInterface){
        bcontext = context;
        bExampleList = exampleList;
        bookmarkClickInterface = mbookmarkClickInterface;
    }

    public class BookmarkHolder extends RecyclerView.ViewHolder{

        public TextView title_b;
        public ImageView image_b;
        public TextView date_b;
        public TextView section_b;
        public String body_b;
        public String share_b;
        public ImageButton bookmark_remove;
        public TextView noBookmark;

        BookmarkHolder(@NonNull View itemView) {
            super(itemView);
            title_b = itemView.findViewById(R.id.bookmark_title);
            image_b = itemView.findViewById(R.id.bookmark_image);
            date_b = itemView.findViewById(R.id.bookmark_date);
            section_b = itemView.findViewById(R.id.bookmark_section);
            bookmark_remove = itemView.findViewById(R.id.bookmark_btn_delete);

            noBookmark = itemView.findViewById(R.id.no_bookmark_text);
            Log.d("PARENT ", "BookmarkHolder: "+noBookmark);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bookmarkClickInterface.onBkClick(getAdapterPosition());

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    bookmarkClickInterface.onBkLongClick(getAdapterPosition());
                    return false;
                }
            });

            bookmark_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bookmarkClickInterface.removeBookmark(getAdapterPosition());
                    Log.d("LIST SIZE ", "onClick: List size = "+bExampleList.size());
//                    if(bExampleList.size()==1){
//                        noBookmark.setVisibility(View.VISIBLE);
//                        notify();
//                    }

                }
            });

        }
    }

//    private void removeItem(int position) {
//        bExampleList.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, bExampleList.size());
//    }


    @NonNull
    @Override
    public BookmarkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_card, parent, false);
        BookmarkHolder bvh = new BookmarkHolder(v);
        view_parent = v.getParent();
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkHolder holder, int position) {
        CardItem currentItem = bExampleList.get(position);
        holder.title_b.setText(currentItem.getMtitle());
        holder.body_b = currentItem.getMbody();
        holder.share_b = currentItem.getMshareUrl();
        holder.section_b.setText(currentItem.getMsection());
        String curr_date = currentItem.getDate();
        String dt = curr_date.substring(0,curr_date.length()-5);
        holder.date_b.setText(dt);
        Picasso.get().load(currentItem.getMimageUrl()).into(holder.image_b);
    }

    @Override
    public int getItemCount() {
        if(bExampleList==null)
            return 0;
        return bExampleList.size();
    }
}
