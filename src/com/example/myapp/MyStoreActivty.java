package com.example.myapp;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuajie on 15/7/22.
 */
public class MyStoreActivty extends FragmentActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintabs);

        fragmentList.add(new indexFragment());
        fragmentList.add(new msgFragment());
        fragmentList.add(new timeFragment());
        fragmentList.add(new frendFragment());

        viewPager = (ViewPager) findViewById(R.id.navi_view_pager);


        StoreAdapter myAdapter = new StoreAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myAdapter);

        //禁止滑动
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        RadioButton index_radio = (RadioButton) findViewById(R.id.radio_button0);
        RadioButton msg_radio = (RadioButton) findViewById(R.id.radio_button1);
        RadioButton time_radio = (RadioButton) findViewById(R.id.radio_button2);
        RadioButton friend_radio = (RadioButton) findViewById(R.id.radio_button3);
        RadioButton more_radio = (RadioButton) findViewById(R.id.radio_button4);

        index_radio.setOnClickListener(this);
        msg_radio.setOnClickListener(this);
        time_radio.setOnClickListener(this);
        friend_radio.setOnClickListener(this);
        more_radio.setOnClickListener(this);

        CheckBox cb = (CheckBox) findViewById(R.id.topVisable);
        cb.setVisibility(View.INVISIBLE);


        ActionBar acr = this.getActionBar();
        acr.setDisplayShowHomeEnabled(true);
        acr.setHomeButtonEnabled(true);
        acr.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        //添加菜单项
        MenuItem add = menu.add(0, 0, 0, "add");
        MenuItem del = menu.add(0, 0, 0, "del");
        MenuItem save = menu.add(0, 0, 0, "save");
        //绑定到ActionBar
        add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        del.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        save.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radio_button0:
                changeView(0);
                break;
            case R.id.radio_button1:
                changeView(1);
                break;
            case R.id.radio_button2:
                changeView(2);
                break;
            case R.id.radio_button3:
                changeView(3);
                break;
            case R.id.radio_button4:
                changeView(4);
                break;
            default:
                break;

        }
    }

    public void changeView(int index) {
        viewPager.setCurrentItem(index, true);

    }
    //建立数据适配器

    class StoreAdapter extends FragmentStatePagerAdapter {


        FragmentManager fmanger = null;

        public StoreAdapter(FragmentManager fm) {
            super(fm);
            fmanger = fm;
        }


        @Override
        public android.support.v4.app.Fragment getItem(int i) {

            return fragmentList.get(i);

        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }


}





