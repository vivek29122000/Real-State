package com.example.realestatear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;

import com.example.realestatear.fragment.CommonFragment;
import com.example.realestatear.fragment.IndoorFragment;
import com.example.realestatear.fragment.OutdoorFragment;
import com.google.android.material.tabs.TabLayout;

public class ProgressActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Indoor"));
        tabLayout.addTab(tabLayout.newTab().setText("Common"));
        tabLayout.addTab(tabLayout.newTab().setText("Outdoor"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount(), new IndoorFragment(), new OutdoorFragment(), new CommonFragment());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    class MyAdapter extends FragmentPagerAdapter {

        private Context myContext;
        int totalTabs;
        IndoorFragment indoorFragment;
        OutdoorFragment outdoorFragment;
        CommonFragment commonFragment;

        public MyAdapter(Context context, FragmentManager fm, int totalTabs, IndoorFragment indoorFragment, OutdoorFragment outdoorFragment, CommonFragment commonFragment) {
            super(fm);
            myContext = context;
            this.totalTabs = totalTabs;
            this.indoorFragment = indoorFragment;
            this.commonFragment = commonFragment;
            this.outdoorFragment = outdoorFragment;
        }

        // this is for fragment tabs
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return indoorFragment;
                case 1:
                    return commonFragment;
                case 2:
                    return outdoorFragment;
                default:
                    return null;
            }
        }
        // this counts total number of tabs
        @Override
        public int getCount() {
            return totalTabs;
        }
    }
}