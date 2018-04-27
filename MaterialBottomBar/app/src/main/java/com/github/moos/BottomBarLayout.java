package com.github.moos;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

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
                Log.e(TAG, "tab>>>>>>>onClicked>>>>"+position);
                if(position == mCurrentPosition){
                    mListener.onTabReselected(position);
                }else {
                    Log.e(TAG, "onClick: 被点击选中了" );
                    mListener.onTabSelected(position);
                    tab.setSelected(true);
                    /**
                     * use the animation
                     */
                    startTabJumpAnimation(tab, 300);
                    stopTabJumpAnimation(mTabs.get(mCurrentPosition), 300);
                    mListener.onTabUnselected(mCurrentPosition);
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

    public BottomBarLayout create(OnTabSelectedListener onTabSelectedListener) {
        mListener = onTabSelectedListener;
        return this;
    }

    private void startTabJumpAnimation(BottomTabView tab, int duration){
        tab.animate().scaleX(1.2f).scaleY(1.2f)
                .setDuration(duration)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    private void stopTabJumpAnimation(BottomTabView tab, int duration){
        tab.animate().scaleX(1f).scaleY(1f)
                .setDuration(duration)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    public void setArrangeType(){

    }




    public interface OnTabSelectedListener {
        void onTabSelected(int position);

        void onTabUnselected(int position);

        void onTabReselected(int position);
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




}
