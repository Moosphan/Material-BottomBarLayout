package com.github.moos.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.moos.R;
import com.github.moos.bean.CardBean;

import java.util.List;

/**
 * Created by moos on 2018/5/7.
 */

public class CardViewPagerAdapter extends PagerAdapter {
    private List<CardBean> list;
    private Context context;

    public CardViewPagerAdapter(Context context, List<CardBean> list) {
        this.list = list;
        this.context = context;
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
        View view = View.inflate(context, R.layout.card_item_layout, null);
        ImageView imageView = view.findViewById(R.id.item_image);
        TextView textView = view.findViewById(R.id.item_title);
        Glide.with(context)
                .load(list.get(position).getImageRes())
                .animate(new AlphaAnimation(0.3f, 1f))
                .into(imageView);
        textView.setText(list.get(position).getTitleRes());
        ((ViewPager)container).addView(view);
        return view;
    }


}
