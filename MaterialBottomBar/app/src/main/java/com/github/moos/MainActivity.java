package com.github.moos;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private BottomTabView tab_home, tab_look, tab_mine;
    private ViewPager viewPager;
    private List<CardBean> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomBarLayout bottomBarLayout = findViewById(R.id.bottom_bar);
        viewPager = findViewById(R.id.viewPager);
        tab_home = new BottomTabView(this);
        tab_home.setTabIcon(R.drawable.home);
        tab_home.setTabTitle("Home");

        tab_look = new BottomTabView(this);
        tab_look.setTabIcon(R.drawable.activity);
        tab_look.setTabTitle("Discover");

        tab_mine = new BottomTabView(this);
        tab_mine.setTabIcon(R.drawable.user);
        //tab_mine.setTabIconSize(29); // efficient
        //tab_mine.setTabPadding(12);
        //tab_mine.setTabTitleSize(16);
        //tab_home.setSelectedColor(Color.parseColor("#f3566a"));
        //tab_mine.setBubbleBackground(R.drawable.msg_bg);
        //tab_mine.setBubbleSize(28);
        //tab_mine.setUnreadTextSize(16);
        //tab_mine.setUnreadTextColor(Color.parseColor("#f345f5"));
        tab_mine.setTabTitle("Mine");
        tab_mine.setUnreadCount(100);
        bottomBarLayout
                .addTab(tab_home)
                .addTab(tab_look)
                .addTab(tab_mine)
                .create(new BottomBarLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(BottomTabView tab) {
                        Log.e(TAG, "onTabSelected: ====="+tab.getTabPosition() );
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
        viewPager.setAdapter(new CardViewPagerAdapter(data));
        bottomBarLayout.bindViewPager(viewPager);


    }

    public class CardViewPagerAdapter extends PagerAdapter {
        private List<CardBean> list;

        public CardViewPagerAdapter(List<CardBean> list) {
            this.list = list;
        }

        @Override
        public int getCount() {

            if (list != null && list.size() > 0) {
                return list.size();
            } else {
                return 0;
            }
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(MainActivity.this, R.layout.card_item_layout, null);
            ImageView imageView = view.findViewById(R.id.item_image);
            TextView textView = view.findViewById(R.id.item_title);
            Glide.with(MainActivity.this)
                  .load(list.get(position).getImageRes())
                    .animate(new AlphaAnimation(0.3f, 1f))
                    .into(imageView);
            textView.setText(list.get(position).getTitleRes());
            ((ViewPager)container).addView(view);
            return view;
        }


    }
}
