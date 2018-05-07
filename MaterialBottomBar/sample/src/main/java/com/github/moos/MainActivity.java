package com.github.moos;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import com.github.moos.fragment.HorizontalSampleFragment;
import com.github.moos.fragment.VerticalSampleFragment;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private SwitchCompat switcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switcher = findViewById(R.id.fragment_switcher);
        replaceFragment(new HorizontalSampleFragment());
        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    replaceFragment(new VerticalSampleFragment());
                }else {
                    replaceFragment(new HorizontalSampleFragment());
                }
            }
        });




    }

    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

}
