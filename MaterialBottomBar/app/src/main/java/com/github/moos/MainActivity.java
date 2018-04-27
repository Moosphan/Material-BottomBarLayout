package com.github.moos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private BottomTabView tab_home, tab_look, tab_mine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomBarLayout bottomBarLayout = findViewById(R.id.bottom_bar);
        tab_home = new BottomTabView(this);
        tab_home.setTabIcon(R.drawable.icon1);
        tab_home.setTabTitle("首页");

        tab_look = new BottomTabView(this);
        tab_look.setTabIcon(R.drawable.icon2);
        tab_look.setTabTitle("发现");

        tab_mine = new BottomTabView(this);
        tab_mine.setTabIcon(R.drawable.icon3);
        tab_mine.setTabTitle("我的");
        //tab_mine.setUnreadCount(100);
        bottomBarLayout
                .addTab(tab_home)
                .addTab(tab_look)
                .addTab(tab_mine)
                .create(new BottomBarLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(int position) {
                        Log.e(TAG, "onTabSelected: 选中了");
                    }

                    @Override
                    public void onTabUnselected(int position) {

                    }

                    @Override
                    public void onTabReselected(int position) {

                    }
                });
        //todo:tabs无法显示，但可以点击

    }
}
