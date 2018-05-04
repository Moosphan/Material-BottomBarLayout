package com.moos.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by moos on 2018/4/26.
 * <p>the tab in bottom bar layout</p>
 * todo:使用建造者模式创建tab，并将setLayoutParams放在最后的create方法里调用
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
    private float mTabIconSize = 20;
    /**
     * the title of tab
     */
    private String tabText;
    /**
     * the color when select the tab
     */
    private int mSelectedColor = Color.parseColor("#000000");
    /**
     * the color of normal state
     */
    private int mUnSelectedColor = Color.parseColor("#a5a5a5");
    /**
     * the counts of unread msg
     */
    private TextView mTabUnreadCount;

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
    private @DrawableRes int mBubbleBackground = R.drawable.tab_unread_default_bg;
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
    private int mBubbleSize = 18;
    /**
     * the padding of unread text
     */
    private int mUnreadTextPadding = 3;
    /**
     * the margin top value of unread bubble
     */
    private int mUnreadTextMarginTop = 3;
    /**
     * the margin right value of unread bubble
     */
    private int mUnreadTextMarginRight = 32;
    /**
     * the counts of unread msg fot tab
     */
    private int mUnreadMsgCount = 0;
    /**
     * weather show the tab title
     */
    private boolean isIconOnly = false;
    /**
     * weather show the tab icon
     */
    private boolean isTitleOnly = false;

    /**
     * the LayoutParams of icon view
     */
    private LinearLayout.LayoutParams iconParams;
    /**
     * the LayoutParams of title view
     */
    private LinearLayout.LayoutParams titleParams;
    /**
     * the container of tab
     */
    private LinearLayout tabContainer;
    /**
     * the LayoutParams of bubble view
     */
    private LayoutParams unreadParams;


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

        /*TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BottomTabView);
        tabIcon = typedArray.getResourceId(R.styleable.BottomTabView_tab_icon, R.mipmap.ic_launcher);
        tabText = typedArray.getString(R.styleable.BottomTabView_tab_text);
        mTabIconSize = typedArray.getDimensionPixelSize(R.styleable.BottomTabView_tab_icon_size, getResources().getDimensionPixelSize(R.dimen.tab_icon_default_size));
        mSelectedColor = typedArray.getColor(R.styleable.BottomTabView_tab_selected_color, Color.parseColor("#000000"));
        mUnSelectedColor = typedArray.getColor(R.styleable.BottomTabView_tab_unSelect_color, Color.parseColor("#a5a5a5"));
        //isTabAnimated = typedArray.getBoolean(R.styleable.BottomTabView_tab_is_animated, false);
        mTabTextSize = typedArray.getDimensionPixelSize(R.styleable.BottomTabView_tab_text_size, getResources().getDimensionPixelSize(R.dimen.tab_text_default_size));
        mTabPadding = typedArray.getDimensionPixelSize(R.styleable.BottomTabView_tab_padding, getResources().getDimensionPixelSize(R.dimen.tab_default_padding));
        mBubbleBackground = typedArray.getResourceId(R.styleable.BottomTabView_tab_unread_background, R.drawable.tab_unread_default_bg);
        mUnreadTextColor = typedArray.getColor(R.styleable.BottomTabView_tab_unread_text_color, Color.parseColor("#ffffff"));
        mBubbleSize = typedArray.getDimensionPixelSize(R.styleable.BottomTabView_tab_bubble_size, getResources().getDimensionPixelSize(R.dimen.tab_bubble_default_size));
        mUnreadTextSize = typedArray.getDimensionPixelSize(R.styleable.BottomTabView_tab_unread_text_size, getResources().getDimensionPixelSize(R.dimen.tab_unread_text_size));
        mUnreadTextPadding = typedArray.getDimensionPixelSize(R.styleable.BottomTabView_tab_unread_text_padding, getResources().getDimensionPixelSize(R.dimen.tab_default_padding));
        isIconOnly = typedArray.getBoolean(R.styleable.BottomTabView_tab_icon_only, false);
        isTitleOnly = typedArray.getBoolean(R.styleable.BottomTabView_tab_text_only, false);
        typedArray.recycle();
        */

        initView(context);
    }

    private void initView(Context context){


        /**
         * init the tab container
         */
        tabContainer = new LinearLayout(context);
        tabContainer.setOrientation(LinearLayout.VERTICAL);
        tabContainer.setGravity(Gravity.CENTER);
        LayoutParams containerParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        containerParams.gravity = Gravity.CENTER;
        tabContainer.setPadding(0, mTabPadding, 0, mTabPadding);
        tabContainer.setLayoutParams(containerParams);
        /**
         * init and add the tab icon into container
         */
        mTabIcon = new ImageView(context);
        iconParams = new LinearLayout.LayoutParams(dp2px(context, mTabIconSize), dp2px(context, mTabIconSize));
        mTabIcon.setImageResource(tabIcon);
        mTabIcon.setLayoutParams(iconParams);
        mTabIcon.setColorFilter(mUnSelectedColor);
        tabContainer.addView(mTabIcon);
        if(isTitleOnly){
            mTabIcon.setVisibility(View.GONE);
        }
        /**
         * init and add the tab text into container
         */
        mTabTitle = new TextView(context);
        titleParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.topMargin = dp2px(context, 2);
        mTabTitle.setText(tabText);
        mTabTitle.setTextSize(mTabTextSize);
        mTabTitle.setTextColor(mUnSelectedColor);
        mTabTitle.setLayoutParams(titleParams);
        tabContainer.addView(mTabTitle);
        if(isIconOnly){
            mTabTitle.setVisibility(View.GONE);
        }

        addView(tabContainer);
        /**
         * init and add the unread text into container
         */
        mTabUnreadCount = new TextView(context);
        mTabUnreadCount.setTextSize( mUnreadTextSize);
        mTabUnreadCount.setTextColor(mUnreadTextColor);
        mTabUnreadCount.setGravity(Gravity.CENTER);
        mTabUnreadCount.setPadding(dp2px(context, mUnreadTextPadding), 0, dp2px(context, mUnreadTextPadding), 0);
        mTabUnreadCount.setBackgroundResource(mBubbleBackground);
        unreadParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dp2px(context, mBubbleSize));
        unreadParams.gravity = Gravity.RIGHT;
        unreadParams.topMargin = dp2px(context,mUnreadTextMarginTop);
        unreadParams.rightMargin = dp2px(context, mUnreadTextMarginRight);
        mTabUnreadCount.setLayoutParams(unreadParams);
        mTabUnreadCount.setVisibility(View.GONE);
        addView(mTabUnreadCount);



    }


    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if(selected){

            Log.e(TAG, "setSelected: 选中了");
            mTabTitle.setTextColor(mSelectedColor);
            mTabIcon.setColorFilter(mSelectedColor);
        }else {
            mTabTitle.setTextColor(mUnSelectedColor);
            mTabIcon.setColorFilter(mUnSelectedColor);
        }
    }

    /**
     * set unread msg count for tab
     * @param count unread msg count
     * [pass-test]
     */
    public void setUnreadCount(int count){
        this.mUnreadMsgCount = count;
        if(count > 0){
            mTabUnreadCount.setVisibility(View.VISIBLE);
            if(count > 99){
                mTabUnreadCount.setText(getResources().getString(R.string.unread_count_99_more));
            }else {
                mTabUnreadCount.setText(String.valueOf(count));
            }

        }else {
            mTabUnreadCount.setText(String.valueOf(0));
            mTabUnreadCount.setVisibility(View.GONE);
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
     * [pass-test]
     */
    public void setTabIcon(@DrawableRes int icon){
        this.tabIcon = icon;
        mTabIcon.setImageResource(tabIcon);
    }

    /**
     * set the title for tab
     * @param title tab text
     * [pass-test]
     */
    public void setTabTitle(String title){
        this.tabText = title;
        mTabTitle.setText(tabText);
    }

    /**
     * set the icon size for tab
     * @param size icon size
     * [pass-test]
     */
    public void setTabIconSize(int size){
        this.mTabIconSize = size;
        /**
         * refresh the view
         */
        iconParams.width = dp2px(mContext, size);
        iconParams.height = dp2px(mContext, size);

    }

    /**
     * set title text size for tab
     * @param titleSize text size
     * [pass-test]
     */
    public void setTabTitleSize(int titleSize){
        this.mTabTextSize = titleSize;
        mTabTitle.setTextSize(mTabTextSize);
    }

    /**
     * set padding for each tab
     * @param tabPadding padding value
     * [pass-test]
     */
    public void setTabPadding(int tabPadding){
        this.mTabPadding = tabPadding;
        tabContainer.setPadding(0, dp2px(mContext,mTabPadding), 0, dp2px(mContext,mTabPadding));

    }

    /**
     * set selected color for tab
     * @param selectColor selected state color
     * [pass-test]
     */
    public void setSelectedColor(@ColorInt int selectColor){
        this.mSelectedColor = selectColor;

    }

    /**
     * set unselected color for tab
     * @param unSelectColor unSelected state color
     * [pass-test]
     */
    public void setUnselectedColor(@ColorInt int unSelectColor){
        this.mUnSelectedColor = unSelectColor;
    }

    /**
     * set bg for unread bubble view
     * @param bubbleDrawable drawable res
     * [pass-test]
     */
    public void setBubbleBackground(@DrawableRes int bubbleDrawable){
        this.mBubbleBackground = bubbleDrawable;
        mTabUnreadCount.setBackgroundResource(mBubbleBackground);
    }

    /**
     * set size for bubble, only update the 'vertical size'
     * @param bubbleSize size
     * [pass-test]
     */
    public void setBubbleSize(int bubbleSize){
        this.mBubbleSize = dp2px(mContext, bubbleSize);
        unreadParams.height = mBubbleSize;
    }

    /**
     * set size for unread text
     * @param textSize size of text
     * [pass-test]
     */
    public void setUnreadTextSize(int textSize){
        this.mUnreadTextSize = textSize;
        mTabUnreadCount.setTextSize(mUnreadTextSize);
    }

    /**
     * set color for unread text
     * @param textColor color of text
     * [pass-test]
     */
    public void setUnreadTextColor(@ColorInt int textColor){
        this.mUnreadTextColor = textColor;
        mTabUnreadCount.setTextColor(mUnreadTextColor);
    }

    /**
     * set padding of unread text, only for padding left and right
     * @param textPadding padding
     * [pass-test]
     */
    public void setUnreadTextPadding(int textPadding){
        this.mUnreadTextPadding = textPadding;
        mTabUnreadCount.setPadding(mUnreadTextPadding, 0, mUnreadTextPadding, 0);
    }

    /**
     * weather show the tab title
     * @param iconOnly only show the icon?
     * [pass-test]
     */
    public void setTabIconOnly(boolean iconOnly){
        this.isIconOnly = iconOnly;
        if(isIconOnly){
            mTabTitle.setVisibility(View.GONE);
        }else {
            mTabTitle.setVisibility(View.VISIBLE);
        }
    }

    /**
     * weather show the tab icon
     * @param textOnly only show the text?
     * [pass-test]
     */
    public void setTabTitleOnly(boolean textOnly){
        this.isTitleOnly = textOnly;
        if(isTitleOnly){
            mTabIcon.setVisibility(View.GONE);
        }else {
            mTabIcon.setVisibility(View.VISIBLE);
        }
    }

    /**
     * set margin top to unreadText
     * @param marginTop margin value
     * [pass-test]
     *
     */
    public void setUnreadTextMarginTop(int marginTop){
        this.mUnreadTextMarginTop = marginTop;
        unreadParams.topMargin = dp2px(mContext, marginTop);
    }

    /**
     * set margin right to unreadText
     * @param marginRight margin value
     * [pass-test]
     */
    public void setUnreadTextMarginRight(int marginRight){
        this.mUnreadTextMarginRight = marginRight;
        unreadParams.rightMargin = dp2px(mContext, marginRight);
    }

    /**
     * get the tab icon, you can use it to realize more animations
     * @return icon
     */
    public ImageView getTabIconView(){
        return mTabIcon;
    }

    /**
     * get the tab title view
     * @return title
     */
    public TextView getTabTextView(){
        return mTabTitle;
    }

    /**
     * get the container of tab
     * @return container
     */
    public LinearLayout getTabContainer(){
        return tabContainer;
    }


    /**
     * transform dip to px
     * @param context mContext
     * @param dp dip
     * @return px
     */
    private int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
