package com.github.moos.fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.moos.adapter.CardViewPagerAdapter;
import com.github.moos.R;
import com.github.moos.bean.CardBean;
import com.moos.navigation.BottomBarLayout;
import com.moos.navigation.BottomTabView;

import java.util.ArrayList;
import java.util.List;

/**
 * A sample of Horizontal style navigation bottom bar.
 */
public class HorizontalSampleFragment extends Fragment{

    private List<CardBean> data = new ArrayList<>();
    public static final String TAG = "HorizontalStyle";

    public HorizontalSampleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fm_sample_horizontal, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        BottomBarLayout bottomBarLayout = view.findViewById(R.id.bottom_bar_horizontal);
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        bottomBarLayout.animationEnable(false);

        BottomTabView tab_home = new BottomTabView(getContext());
        tab_home.setTabIcon(R.drawable.work_edit_sticker_unselected_state);
        tab_home.setSelectedTabIcon(R.drawable.work_edit_sticker_selected_state);
        tab_home.setTabTitle("Home");
        //tab_home.setUnselectedColor(Color.parseColor("#f65656"));

        BottomTabView tab_look = new BottomTabView(getContext());
        tab_look.setTabIcon(R.drawable.work_edit_words_unselected_state);
        tab_look.setSelectedTabIcon(R.drawable.work_edit_words_selected_state);
        tab_look.setTabTitle("Discover");
        //tab_look.setUnselectedColor(Color.parseColor("#f65656"));


        BottomTabView tab_mine = new BottomTabView(getContext());
        tab_mine.setTabIcon(R.drawable.work_edit_music_unselected_state);
        tab_mine.setSelectedTabIcon(R.drawable.work_edit_music_selected_state);
        //tab_mine.setTabIconSize(29); // efficient
        //tab_mine.setTabPadding(12);
        //tab_mine.setTabTitleSize(16);
        //tab_home.setSelectedColor(Color.parseColor("#f3566a"));
        //tab_mine.setBubbleBackground(R.drawable.msg_bg);
        //tab_mine.setBubbleSize(28);
        tab_mine.setUnreadTextSize(12);
        //tab_mine.setUnreadTextColor(Color.parseColor("#f345f5"));
        //tab_mine.setTabIconOnly(true);
        //tab_home.setTabIconOnly(true);
        //tab_look.setTabIconOnly(true);
        //tab_mine.setTabTitleOnly(true);
        //tab_home.setTabTitleOnly(true);
        //tab_look.setTabTitleOnly(true);
        tab_mine.setTabTitle("Mine");
        tab_mine.setUnreadCount(100);
        //tab_mine.setSelectedTabIcon(R.drawable.user_selected);


        bottomBarLayout
                .addTab(tab_home)
                .addTab(tab_look)
                .addTab(tab_mine)
                .create(new BottomBarLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(BottomTabView tab) {
                        Log.e(TAG, "onTabSelected: =="+tab.getTabPosition() );
                    }

                    @Override
                    public void onTabUnselected(BottomTabView tab) {

                    }

                    @Override
                    public void onTabReselected(BottomTabView tab) {

                    }
                });
        data.add(new CardBean(R.drawable.image1, "A beautiful design for rooms."));
        data.add(new CardBean(R.drawable.image2, "This will make you comfortable."));
        data.add(new CardBean(R.drawable.image3, "Let's see the tartan design."));
        viewPager.setAdapter(new CardViewPagerAdapter(getContext(), data));
        bottomBarLayout.bindViewPager(viewPager);
    }




}
