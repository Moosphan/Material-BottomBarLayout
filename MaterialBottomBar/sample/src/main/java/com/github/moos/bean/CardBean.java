package com.github.moos.bean;

import android.support.annotation.DrawableRes;

/**
 * Created by moos on 2018/4/29.
 * the example of data
 */

public class CardBean {
    private @DrawableRes int imageRes;
    private String titleRes;

    public CardBean(int imageRes, String titleRes) {
        this.imageRes = imageRes;
        this.titleRes = titleRes;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getTitleRes() {
        return titleRes;
    }

    public void setTitleRes(String titleRes) {
        this.titleRes = titleRes;
    }
}
