package com.github.moos.fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.moos.R;
import com.moos.navigation.BottomBarLayout;
import com.moos.navigation.BottomTabView;

/**
 * A sample of Vertical style navigation bar.
 * todo:
 * 1. add the message bubble
 * 2. add the bg changing
 */
public class VerticalSampleFragment extends Fragment{

    public static final String TAG = "VerticalStyle";
    private BottomTabView tab_book, tab_music, tab_film;



    public VerticalSampleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fm_sample_vertical, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        BottomBarLayout bottomBarLayout = view.findViewById(R.id.navigation_bar_vertical);
        tab_book = new BottomTabView(getContext());
        tab_film = new BottomTabView(getContext());
        tab_music = new BottomTabView(getContext());

        tab_book.setTabIcon(R.drawable.icon_book);
        tab_book.setTabIconOnly(true);
        tab_book.setTabIconSize(28);
        tab_music.setTabIcon(R.drawable.icon_music);
        tab_music.setTabIconOnly(true);
        tab_music.setTabIconSize(28);
        tab_film.setTabIcon(R.drawable.icon_film);
        tab_film.setTabIconOnly(true);
        tab_film.setTabIconSize(28);

        replaceFragment(new ContentFragment("Book"));

        bottomBarLayout
                .addTab(tab_book)
                .addTab(tab_music)
                .addTab(tab_film)
                .create(new BottomBarLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(BottomTabView tab) {
                        Log.e(TAG, "onTabSelected: =="+tab.getTabPosition() );
                        switch (tab.getTabPosition()){
                            case 0:
                                replaceFragment(new ContentFragment("Book"));
                                break;

                            case 1:
                                replaceFragment(new ContentFragment("Music"));
                                break;

                            case 2:
                                replaceFragment(new ContentFragment("Movie"));
                                break;
                        }
                    }

                    @Override
                    public void onTabUnselected(BottomTabView tab) {

                    }

                    @Override
                    public void onTabReselected(BottomTabView tab) {

                    }
                });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fm_content_container, fragment);
        transaction.commit();
    }
}
