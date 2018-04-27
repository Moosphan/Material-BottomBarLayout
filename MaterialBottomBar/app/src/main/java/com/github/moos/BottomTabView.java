package com.github.moos;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

/**
 * Created by moos on 2018/4/26.
 * <p>the tab in bottom bar layout</p>
 */

public class BottomTabView extends FrameLayout {
    public static final String TAG = "BottomTabView";

    /**
     * the icon view of tab
     */
    private ImageView mTabIcon;
    /**
     * the title view of tab
     */
    private TextView mTabTitle;
    /**
     * the context
     */
    private Context mContext;
    /**
     * the current tab's position
     */
    private int mTabPosition = -1;
    /**
     * the icon res of tab
     */
    private @DrawableRes int tabIcon;
    /**
     * the size of inner icon
     */
    private int mTabIconSize = 20;
    /**
     * the title of tab
     */
    private String tabText;
    /**
     * the color when select the tab
     */
    private int mSelectedColor = Color.parseColor("#343434");
    /**
     * the color of normal state
     */
    private int mUnSelectedColor = Color.parseColor("#e2e2e2");
    /**
     * the counts of unread msg
     */
    private TextView mTabUnreadCount;
    /**
     * the tab animated or not when selected
     */
    private boolean isTabAnimated = false;
    /**
     * the size of tab text
     */
    private int mTabTextSize = 12;
    /**
     * the padding of tab
     */
    private int mTabPadding = 5;
    /**
     * unread msg text background
     */
    private @DrawableRes int mBubbleBackground;
    /**
     * the color of unread msg
     */
    private int mUnreadTextColor = Color.parseColor("#ffffff");
    /**
     * the size of unread text
     */
    private int mUnreadTextSize = 10;
    /**
     * the size of bubble(unread msg)
     */
    private int mBubbleSize = 20;
    /**
     * the padding of unread text
     */
    private int mUnreadTextPadding = 5;
    /**
     * the counts of unread msg fot tab
     */
    private int mUnreadMsgCount = 0;


    public BottomTabView(@NonNull Context context) {
        super(context);
        initAttrs(context, null);

    }

    public BottomTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public BottomTabView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs){
        this.mContext = context;
        TypedArray rippleTypeArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackgroundBorderless});
        Drawable rippleDrawable = rippleTypeArray.getDrawable(0);
        setBackground(rippleDrawable);
        rippleTypeArray.recycle();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BottomTabView);
        tabIcon = typedArray.getResourceId(R.styleable.BottomTabView_tab_icon, R.mipmap.ic_launcher);
        tabText = typedArray.getString(R.styleable.BottomTabView_tab_text);
        mTabIconSize = typedArray.getDimensionPixelSize(R.styleable.BottomTabView_tab_icon_size, getResources().getDimensionPixelSize(R.dimen.tab_icon_default_size));
        mSelectedColor = typedArray.getColor(R.styleable.BottomTabView_tab_selected_color, Color.parseColor("#343434"));
        mUnSelectedColor = typedArray.getColor(R.styleable.BottomTabView_tab_unSelect_color, Color.parseColor("#e2e2e2"));
        isTabAnimated = typedArray.getBoolean(R.styleable.BottomTabView_tab_is_animated, false);
        mTabTextSize = typedArray.getDimensionPixelSize(R.styleable.BottomTabView_tab_text_size, getResources().getDimensionPixelSize(R.dimen.tab_text_default_size));
        mTabPadding = typedArray.getDimensionPixelSize(R.styleable.BottomTabView_tab_padding, getResources().getDimensionPixelSize(R.dimen.tab_default_padding));
        mBubbleBackground = typedArray.getResourceId(R.styleable.BottomTabView_tab_unread_background, R.drawable.tab_unread_default_bg);
        mUnreadTextColor = typedArray.getColor(R.styleable.BottomTabView_tab_unread_text_color, Color.parseColor("#ffffff"));
        mBubbleSize = typedArray.getDimensionPixelSize(R.styleable.BottomTabView_tab_bubble_size, getResources().getDimensionPixelSize(R.dimen.tab_bubble_default_size));
        mUnreadTextSize = typedArray.getDimensionPixelSize(R.styleable.BottomTabView_tab_unread_text_size, getResources().getDimensionPixelSize(R.dimen.tab_unread_text_size));
        mUnreadTextPadding = typedArray.getDimensionPixelSize(R.styleable.BottomTabView_tab_unread_text_padding, getResources().getDimensionPixelSize(R.dimen.tab_default_padding));
        typedArray.recycle();

        initView(context);
    }

    private void initView(Context context){


        /**
         * init the tab container
         */
        LinearLayout tabContainer = new LinearLayout(context);
        tabContainer.setOrientation(LinearLayout.VERTICAL);
        tabContainer.setGravity(Gravity.CENTER);
        LayoutParams containerParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        containerParams.gravity = Gravity.CENTER;
        tabContainer.setLayoutParams(containerParams);
        /**
         * init and add the tab icon into container
         */
        mTabIcon = new ImageView(context);
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(mTabIconSize, mBubbleSize);
        mTabIcon.setImageResource(tabIcon);
        mTabIcon.setLayoutParams(iconParams);
        mTabIcon.setColorFilter(mUnSelectedColor);
        tabContainer.addView(mTabIcon);
        /**
         * init and add the tab text into container
         */
        mTabTitle = new TextView(context);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.topMargin = dp2px(context, 2);
        mTabTitle.setText(tabText);
        mTabTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,mTabTextSize);
        mTabTitle.setTextColor(mUnSelectedColor);
        mTabTitle.setLayoutParams(titleParams);
        tabContainer.addView(mTabTitle);

        addView(tabContainer);
        /**
         * init and add the unread text into container
         */
        mTabUnreadCount = new TextView(context);
        mTabUnreadCount.setTextSize(mUnreadTextSize);
        mTabUnreadCount.setTextColor(mUnreadTextColor);
        mTabUnreadCount.setGravity(Gravity.CENTER);
        mTabUnreadCount.setPadding(mUnreadTextPadding, 0, mUnreadTextPadding, 0);
        mTabUnreadCount.setBackgroundResource(R.drawable.tab_unread_default_bg);
        LinearLayout.LayoutParams unreadParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, mBubbleSize);
        unreadParams.gravity = Gravity.RIGHT;
        unreadParams.topMargin = dp2px(context,3);
        unreadParams.rightMargin = dp2px(context, 3);
        mTabUnreadCount.setLayoutParams(unreadParams);
        mTabUnreadCount.setVisibility(View.GONE);
        addView(mTabUnreadCount);

    }


    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if(selected){

            //startTabJumpAnimation(300);
            Log.e(TAG, "setSelected: 选中了");
            mTabTitle.setTextColor(mSelectedColor);
            mTabIcon.setColorFilter(mSelectedColor);
        }else {
            //stopTabJumpAnimation(300);
            mTabTitle.setTextColor(mUnSelectedColor);
            mTabIcon.setColorFilter(mUnSelectedColor);
        }
    }

    /**
     * set unread msg count for tab
     * @param count unread msg count
     */
    public void setUnreadCount(int count){
        this.mUnreadMsgCount = count;
        if(count > 0){
            mTabTitle.setVisibility(View.VISIBLE);
            if(count > 99){
                mTabTitle.setText(getResources().getString(R.string.unread_count_99_more));
            }else {
                mTabTitle.setText(String.valueOf(count));
            }

        }else {
            mTabTitle.setText(String.valueOf(0));
            mTabTitle.setVisibility(View.GONE);
        }
    }

    /**
     * set the position for tab
     * @param position pos
     */
    public void setTabPosition(int position) {
        this.mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
    }

    public int getTabPosition() {
        return this.mTabPosition;
    }

    /**
     * set the icon res for tab
     * @param icon res
     */
    public void setTabIcon(@DrawableRes int icon){
        this.tabIcon = icon;
        mTabIcon.setImageResource(tabIcon);
    }

    /**
     * set the title for tab
     * @param title tab text
     */
    public void setTabTitle(String title){
        this.tabText = title;
        mTabTitle.setText(tabText);
    }

    /**
     * set the icon size for tab
     * @param size icon size
     */
    public void setTabIconSize(int size){
        this.mTabIconSize = size;
    }

    /**
     * set title text size for tab
     * @param titleSize text size
     */
    public void setTabTitleSize(int titleSize){
        this.mTabTextSize = titleSize;
    }

    /**
     * set padding for each tab
     * @param tabPadding padding value
     */
    public void setTabPadding(int tabPadding){
        this.mTabPadding = tabPadding;
    }

    /**
     * set selected color for tab
     * @param selectColor selected state color
     */
    public void setSelectedColor(@ColorInt int selectColor){
        this.mSelectedColor = selectColor;
    }

    /**
     * set unselected color for tab
     * @param unSelectColor unSelected state color
     */
    public void setUnselectedColor(@ColorInt int unSelectColor){
        this.mUnSelectedColor = unSelectColor;
    }

    /**
     * set bg for unread bubble view
     * @param bubbleDrawable drawable res
     */
    public void setBubbleBackground(@DrawableRes int bubbleDrawable){
        this.mBubbleBackground = bubbleDrawable;
    }

    /**
     * set size for bubble, only update the 'vertical size'
     * @param bubbleSize size
     */
    public void setBubbleSize(int bubbleSize){
        this.mBubbleSize = bubbleSize;
    }

    /**
     * set size for unread text
     * @param textSize size of text
     */
    public void setUnreadTextSize(int textSize){
        this.mUnreadTextSize = textSize;
    }

    /**
     * set color for unread text
     * @param textColor color of text
     */
    public void setUnreadTextColor(@ColorInt int textColor){
        this.mUnreadTextColor = textColor;
    }

    /**
     * set padding of unread text
     * @param textPadding padding
     */
    public void setUnreadTextPadding(int textPadding){
        this.mUnreadTextPadding = textPadding;
    }

    /**
     * whether tab animated when selected or not
     * @param isTabAnimated animated?
     */
    public void setTabAnimated(boolean isTabAnimated){
        this.isTabAnimated = isTabAnimated;
    }

    private void startTabJumpAnimation(int duration){
        /*ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "scaleX",1, 1.2f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "scaleY",1, 1.2f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorX).with(animatorY);
        animatorSet.setDuration(duration);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();*/
        this.animate().scaleX(1.2f).scaleY(1.2f)
                .setDuration(duration)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    private void stopTabJumpAnimation(int duration){
        this.animate().scaleX(1f).scaleY(1f)
                .setDuration(duration)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    public BottomTabView create(String tabTitle, int tabIcon){
        setTabIcon(tabIcon);
        setTabTitle(tabTitle);
        return this;
    }



    /**
     * transform dip to px
     * @param context context
     * @param dp dip
     * @return px
     */
    private int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
