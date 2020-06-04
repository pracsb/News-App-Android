package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

//    int mNoOfTabs;

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    public PagerAdapter(FragmentManager fm)
    {
        super(fm);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        switch(position)
//        {
//            case 0:
//                WorldTab worldTab = new WorldTab();
//                return worldTab;
//            case 1:
//                BusinessTab businessTab = new BusinessTab();
//                return businessTab;
//            case 2:
//                PoliticsTab politicsTab = new PoliticsTab();
//                return politicsTab;
//            case 3:
//                SportsTab sportsTab = new SportsTab();
//                return sportsTab;
//            case 4:
//                TechnologyTab technologyTab = new TechnologyTab();
//                return technologyTab;
//            case 5:
//                ScienceTab scienceTab = new ScienceTab();
//                return scienceTab;
//
//        }
//        return null;
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public String getFragmentTitle(int position)
    {
        return titleList.get(position);
    }

    public void addFragment(Fragment fragment, String title)
    {
        fragmentList.add(fragment);
        titleList.add(title);
    }
}
