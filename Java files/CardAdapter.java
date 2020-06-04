package com.example.myapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private Context mContext;
    private ArrayList<CardItem> mCardList;
    private OnItemClickListener mListener;
    private OnLongClickListener mLongListener;
    private OnBkClickListener mbkmrk_listener;
    private CardItem currentItem;



    public  interface OnBkClickListener{
        void onBkItemClick(int position);
    }

    public void setOnBkClickListener(OnBkClickListener bkClickListener){
        mbkmrk_listener = bkClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public interface OnLongClickListener{
        void onLongItemClick(int position);
    }
    public void setOnLongClickListener(OnLongClickListener longClickListener)
    {
        mLongListener = longClickListener;
    }



    @NonNull
    @Override
    public CardAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(v, mListener, mLongListener, mbkmrk_listener);
    }



    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
         currentItem = mCardList.get(position);
        String imageUrl = currentItem.getMimageUrl();
        String title = currentItem.getMtitle();
        String time = currentItem.getTime();
        String section = currentItem.getMsection();
        String article_id = currentItem.getMarticle_id();

        holder.mTextViewTitle.setText(title);
        holder.mTextViewSection.setText(section);
        holder.mTextViewTime.setText(time);
        Picasso.get().load(imageUrl).fit().centerCrop().into(holder.mImageView);




    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }

    public CardAdapter(Context context, ArrayList<CardItem> cardList)
    {
        mContext = context;
        mCardList = cardList;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextViewTitle;
        public TextView mTextViewTime;
        public TextView mTextViewSection;
        public ImageButton mBookmarkbtn;
        public CardView mCardView;
//        public TextView mTextViewShareUrl;

        public CardViewHolder(@NonNull final View itemView, final OnItemClickListener listener, final OnLongClickListener longListener, final OnBkClickListener bkmrk_listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.card_image);
            mTextViewTitle = itemView.findViewById(R.id.card_title);
            mTextViewSection = itemView.findViewById(R.id.card_section);
            mTextViewTime = itemView.findViewById(R.id.card_time);
            mBookmarkbtn = itemView.findViewById(R.id.bookmarkBtn);
            mCardView = itemView.findViewById(R.id.Card_item_xml);

            mBookmarkbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bkmrk_listener!=null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            bkmrk_listener.onBkItemClick(position);
                            Log.d("TAG", "onClick: BOOKMARK TAG : "+mBookmarkbtn.getTag());
                            if(Integer.parseInt(mBookmarkbtn.getTag().toString())==1)
                            {
                                mBookmarkbtn.setBackgroundResource(R.drawable.ic_bookmark_border_black_24dp);
                                mBookmarkbtn.setTag(0);
                                Toast.makeText(mContext,"\""+currentItem.getMtitle()+"\""+ "was removed from Bookmarks", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                mBookmarkbtn.setBackgroundResource(R.drawable.ic_bookmark_black_24dp);
                                mBookmarkbtn.setTag(1);
                                Toast.makeText(mContext, "\""+currentItem.getMtitle()+"\""+" was added to bookmarks", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(longListener!=null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            longListener.onLongItemClick(position);
                        }
                    }
                    return false;
                }
            });

        }
    }

}
