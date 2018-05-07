package com.github.moos.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.moos.R;

/**
 * Created by moos on 2018/5/7.
 */

public class ContentFragment extends Fragment{

    private String content;
    private TextView textView;

    public ContentFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ContentFragment(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fm_content, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        textView = view.findViewById(R.id.fm_content_text);
        textView.setText(content);
    }
}
