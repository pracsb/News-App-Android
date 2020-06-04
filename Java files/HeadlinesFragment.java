package com.example.myapp;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapp.R;
import com.google.android.material.tabs.TabLayout;

public class HeadlinesFragment extends Fragment {
    View myFragment;
    ViewPager viewPager;
    TabLayout tabLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_headlines, container, false);
        viewPager = myFragment.findViewById(R.id.pager);

        tabLayout = myFragment.findViewById(R.id.headlinesTabLayout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        return myFragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("WORLD");
        tabLayout.getTabAt(1).setText("BUSINESS");
        tabLayout.getTabAt(2).setText("POLITICS");
        tabLayout.getTabAt(3).setText("SPORTS");
        tabLayout.getTabAt(4).setText("TECHNOLOGY");
        tabLayout.getTabAt(5).setText("SCIENCE");


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            int position;
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                switch (position){
                    case 0: tab.setText("World");break;
                    case 1: tab.setText("Business");break;
                    case 2: tab.setText("Politics");break;
                    case 3: tab.setText("Sports");break;
                    case 4: tab.setText("Technology");break;
                    case 5: tab.setText("Science");break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (position){
                    case 0: tab.setText("World");break;
                    case 1: tab.setText("Business");break;
                    case 2: tab.setText("Politics");break;
                    case 3: tab.setText("Sports");break;
                    case 4: tab.setText("Technology");break;
                    case 5: tab.setText("Science");break;

                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (position){
                    case 0: tab.setText("World");break;
                    case 1: tab.setText("Business");break;
                    case 2: tab.setText("Politics");break;
                    case 3: tab.setText("Sports");break;
                    case 4: tab.setText("Technology");break;
                    case 5: tab.setText("Science");break;

                }
            }
        });
    }
    private void setUpViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());

        adapter.addFragment(new WorldTab(), "World");
        adapter.addFragment(new BusinessTab(), "Business");
        adapter.addFragment(new PoliticsTab(), "Politics");
        adapter.addFragment(new SportsTab(), "Sports");
        adapter.addFragment(new TechnologyTab(), "Technology");
        adapter.addFragment(new ScienceTab(), "Science");

        viewPager.setAdapter(adapter);
    }
}
