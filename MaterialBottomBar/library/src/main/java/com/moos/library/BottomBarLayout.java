package com.moos.library;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.view.ViewPager.SCROLL_STATE_DRAGGING;
import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;
import static android.support.v4.view.ViewPager.SCROLL_STATE_SETTLING;

/**
 * Created by moos on 2018/4/27.
 * <p>the layout to contain the tabs, it support the mixed views</p>
 */

public class BottomBarLayout extends LinearLayout {
    public static final String TAG = "BottomBarLayout";

    /**
     * the tabs added in bottomBarLayout
     */
    private List<BottomTabView> mTabs = new ArrayList<>();
    /**
     * the container of the added tabs
     */
    private LinearLayout mBottomBarLayout;
    /**
     * the current position of selected tab
     */
    private int mCurrentPosition = 0;
    /**
     * the arrange way of tabs in container
     */
    private int mArrangeType = 1;
    /**
     * the background of tabs container
     */
    private int mTabsBackground;
    /**
     * the params of each tab
     */
    private LayoutParams mTabParams;
    /**
     * the listener of tab when select
     */
    private OnTabSelectedListener mListener;
    /**
     * arrangement way of tabs
     */
    private ArrangeType arrangeType;
    /**
     * the viewPager to bind
     */
    private ViewPager mViewPager;

    private BottomBarLayoutOnPageChangeListener mPageChangeListener;

    private ViewPagerOnTabSelectedListener mCurrentVpSelectedListener;

    public BottomBarLayout(Context context) {
        super(context);
        init(context, null);
    }

    public BottomBarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BottomBarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BottomBarLayout);
        mArrangeType = typedArray.getInt(R.styleable.BottomBarLayout_tabs_arrange_way, 1);
        mTabsBackground = typedArray.getResourceId(R.styleable.BottomBarLayout_tabs_background, Color.parseColor("#ffffff"));
        typedArray.recycle();

        setOrientation(VERTICAL);
        mBottomBarLayout = new LinearLayout(context);
        mBottomBarLayout.setBackgroundColor(mTabsBackground);
        mBottomBarLayout.setOrientation(HORIZONTAL);
        addView(mBottomBarLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        initTab();
    }

    private void initTab(){
        /**
         * todo:如何在布局里设置tab生效并显示？
         */
        mTabParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        mTabParams.weight = 1;
    }

    /**
     * add the tab for container
     * @param tab {@link BottomTabView}each tab
     * @return current container
     */
    public BottomBarLayout addTab(final BottomTabView tab){
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mListener == null)
                    return;
                int position = tab.getTabPosition();
                if(position == mCurrentPosition){
                    mListener.onTabReselected(tab);
                }else {
                    Log.e(TAG, "onClick: 被点击选中了" );
                    mListener.onTabSelected(tab);
                    tab.setSelected(true);
                    /**
                     * use the animation
                     */
                    startTabJumpAnimation(tab.getTabContainer(), 300);
                    if(mViewPager!=null){
                        mViewPager.setCurrentItem(position);
                    }
                    stopTabJumpAnimation(mTabs.get(mCurrentPosition).getTabContainer(), 300);
                    mListener.onTabUnselected(mTabs.get(mCurrentPosition));
                    mTabs.get(mCurrentPosition).setSelected(false);
                    mCurrentPosition = position;
                }

            }
        });

        tab.setTabPosition(mBottomBarLayout.getChildCount());
        tab.setLayoutParams(mTabParams);
        mBottomBarLayout.addView(tab);
        mTabs.add(tab);

        return this;
    }

    /**
     * batch add the tabs into container
     * @param tabs {@link BottomTabView} many tabs
     */
    public void addTabs(BottomTabView... tabs){
        for(BottomTabView tab : tabs){
            addTab(tab);
        }

    }

    /***
     * set current tab in your self
     * @param position position you want
     */
    public void setCurrentTab(final int position) {
        mBottomBarLayout.post(new Runnable() {
            @Override
            public void run() {
                mBottomBarLayout.getChildAt(position).performClick();
            }
        });
    }

    /**
     * get current selected tab's position
     * @return position
     */
    public int getCurrentTabPosition() {
        return mCurrentPosition;
    }

    /**
     * get the count of tabs
     * @return
     */
    public int getTabCount(){
        return mTabs!=null ? mTabs.size() : 0;
    }

    /**
     * set call back of tab's selected operation
     * @param onTabSelectedListener call back
     * @return
     */
    public BottomBarLayout create(OnTabSelectedListener onTabSelectedListener) {
        mListener = onTabSelectedListener;
        return this;
    }

    private void startTabJumpAnimation(LinearLayout tab, int duration){
        tab.animate().scaleX(1.1f).scaleY(1.1f)
                .setDuration(duration)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    private void stopTabJumpAnimation(LinearLayout tab, int duration){
        tab.animate().scaleX(1f).scaleY(1f)
                .setDuration(duration)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }


    /**
     * bind the viewPager and scroll with it
     * @param viewPager mViewPager
     */
    public void bindViewPager(ViewPager viewPager){
        if (mViewPager != null) {
            // If we've already been setup with a ViewPager, remove us from it
            if (mPageChangeListener != null) {
                mViewPager.removeOnPageChangeListener(mPageChangeListener);
            }
        }

        if (mCurrentVpSelectedListener != null) {
            // If we already have a tab selected listener for the ViewPager, remove it
            mCurrentVpSelectedListener = null;
        }

        if (viewPager != null) {
            mViewPager = viewPager;

            // Add our custom OnPageChangeListener to the ViewPager
            if (mPageChangeListener == null) {
                mPageChangeListener = new BottomBarLayoutOnPageChangeListener(this);
            }
            mPageChangeListener.reset();
            viewPager.addOnPageChangeListener(mPageChangeListener);

            // Now we'll add a tab selected listener to set ViewPager's current item
            mCurrentVpSelectedListener = new BottomBarLayout.ViewPagerOnTabSelectedListener(viewPager);
            setScrollPosition(viewPager.getCurrentItem(), 0f, true);
        } else {
            // We've been given a null ViewPager so we need to clear out the internal state,
            // listeners and observers
            mViewPager = null;
        }
    }

    /**
     * the way of tabs arranges
     * @param arrangeType type
     */
    public void setArrangeType(ArrangeType arrangeType){
        this.arrangeType = arrangeType;

    }

    private void setScrollPosition(int position, float positionOffset, boolean updateSelectedText){
        final int roundedPosition = Math.round(position + positionOffset);
        if (roundedPosition < 0 ) {
            return;
        }
        // Update the 'selected state' view as we scroll, if enabled
        if (updateSelectedText) {
            setCurrentTab(roundedPosition);
        }
    }

    /**
     * the way tabs arranges
     */
    public enum ArrangeType{
        HORIZONTAL,
        VERTICAL
    }




    public interface OnTabSelectedListener {
        void onTabSelected(BottomTabView tab);

        void onTabUnselected(BottomTabView tab);

        void onTabReselected(BottomTabView tab);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new TabSavedState(superState, mCurrentPosition);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        TabSavedState ss = (TabSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        if (mCurrentPosition != ss.position) {
            mBottomBarLayout.getChildAt(mCurrentPosition).setSelected(false);
            mBottomBarLayout.getChildAt(ss.position).setSelected(true);
        }
        mCurrentPosition = ss.position;
    }

    /**
     * Save the tab position for viewGroup
     */
    static class TabSavedState extends BaseSavedState {
        private int position;

        public TabSavedState(Parcel source) {
            super(source);
            position = source.readInt();
        }

        public TabSavedState(Parcelable superState, int position) {
            super(superState);
            this.position = position;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(position);
        }

        public static final Creator<TabSavedState> CREATOR = new Creator<TabSavedState>() {
            public TabSavedState createFromParcel(Parcel in) {
                return new TabSavedState(in);
            }

            public TabSavedState[] newArray(int size) {
                return new TabSavedState[size];
            }
        };
    }

    public static class BottomBarLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private final WeakReference<BottomBarLayout> mTabLayoutRef;
        private int mPreviousScrollState;
        private int mScrollState;

        public BottomBarLayoutOnPageChangeListener(BottomBarLayout tabLayout) {
            mTabLayoutRef = new WeakReference<>(tabLayout);
        }

        @Override
        public void onPageScrollStateChanged(final int state) {
            mPreviousScrollState = mScrollState;
            mScrollState = state;
        }

        @Override
        public void onPageScrolled(final int position, final float positionOffset,
                                   final int positionOffsetPixels) {
            final BottomBarLayout bottomBarLayout = mTabLayoutRef.get();
            if (bottomBarLayout != null) {
                // Only update the text selection if we're not settling, or we are settling after
                // being dragged
                final boolean updateText = mScrollState != SCROLL_STATE_SETTLING ||
                        mPreviousScrollState == SCROLL_STATE_DRAGGING;

                bottomBarLayout.setScrollPosition(position, positionOffset, updateText);
            }
        }

        @Override
        public void onPageSelected(final int position) {
            final BottomBarLayout bottomBarLayout = mTabLayoutRef.get();
            if (bottomBarLayout != null && bottomBarLayout.getCurrentTabPosition() != position
                    && position < bottomBarLayout.getTabCount()) {
                // Select the tab, only updating the indicator if we're not being dragged/settled
                bottomBarLayout.setCurrentTab(position);
            }
        }

        void reset() {
            mPreviousScrollState = mScrollState = SCROLL_STATE_IDLE;
        }
    }

    /**
     * A {@link BottomBarLayout.OnTabSelectedListener} class which contains the necessary calls back
     * to the provided {@link ViewPager} so that the tab position is kept in sync.
     */
    public static class ViewPagerOnTabSelectedListener implements BottomBarLayout.OnTabSelectedListener {
        private final ViewPager mViewPager;

        public ViewPagerOnTabSelectedListener(ViewPager viewPager) {
            mViewPager = viewPager;
        }


        @Override
        public void onTabSelected(BottomTabView tab) {
            mViewPager.setCurrentItem(tab.getTabPosition());
        }

        @Override
        public void onTabUnselected(BottomTabView tab) {

        }

        @Override
        public void onTabReselected(BottomTabView tab) {

        }
    }




}
