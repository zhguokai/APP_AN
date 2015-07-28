package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import com.BgService;
import com.kj.myapp.data.CommentData;
import org.org.xrhh.util.orm.DBManger;

import java.util.ArrayList;
import java.util.List;
public class MyActivity extends Activity {
    private int currentItem = 0;
    //欢迎消息捕获器

    //页面切换器
    private ViewPager viewPager;
    //页面列表
    private List<View> views;
    //自定句柄内部类
    private Handler welcomePageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case CommentData.WELCOME_MSG_CANCLE:
                    currentItem = CommentData.WELCOME_MSG_COUNT;
                    this.removeMessages(CommentData.WELCOME_MSG_SEND);
                    break;
                case CommentData.WELCOME_MSG_SEND:
                    if (currentItem < CommentData.WELCOME_MSG_COUNT) {
                        viewPager.setCurrentItem(currentItem);
                        System.out.println("页面样式：" + currentItem);
                        //五秒之后继续发送
                        // welcomePageHandler.sendMessageDelayed(msg, 5 * 1000);
                        Message msg2 = new Message();
                        msg2.what = CommentData.WELCOME_MSG_SEND;
                        currentItem++;
                        if (currentItem < CommentData.WELCOME_MSG_COUNT) {
                            welcomePageHandler.sendMessageDelayed(msg2, 1 * 1000);
                        }
                    }
                    break;
                case CommentData.OPEN_KJ_APP_MAIN:
                    //开启新的Activity
                    //startActivity();
                    Intent intent = new Intent(MyActivity.this, MyStoreActivty.class);
                    startActivity(intent);
                    finish();

                    break;
                default:
                    break;
            }

//            //取消欢迎页面
//            if(msg.what == CommentData.WELCOME_MSG_CANCLE){
//                currentItem  = CommentData.WELCOME_MSG_COUNT;
//                this.removeMessages(CommentData.WELCOME_MSG_SEND);
//                return;
//            }else if(msg.what == CommentData.WELCOME_MSG_SEND){
//                //如果接收到欢迎页面发过来的消息
//
//                if(currentItem < CommentData.WELCOME_MSG_COUNT) {
//                    viewPager.setCurrentItem(currentItem);
//                    System.out.println("页面样式："+currentItem);
//                    //五秒之后继续发送
//                   // welcomePageHandler.sendMessageDelayed(msg, 5 * 1000);
//                    Message msg2 = new Message();
//                    msg2.what = CommentData.WELCOME_MSG_SEND;
//                    currentItem ++;
//                    if(currentItem < CommentData.WELCOME_MSG_COUNT) {
//                        welcomePageHandler.sendMessageDelayed(msg2, 5 * 1000);
//                    }
//                }
//            }
        }
    };

    //当Activity第一次创建时调用
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        viewPager = (ViewPager) findViewById(R.id.pager);

        views = new ArrayList<View>();

        LayoutInflater layoutInflater = getLayoutInflater();
        View view1 = layoutInflater.inflate(R.layout.view1, null);
        View view2 = layoutInflater.inflate(R.layout.view2, null);
        View view3 = layoutInflater.inflate(R.layout.view3, null);

        views.add(view1);
        views.add(view2);
        views.add(view3);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == CommentData.WELCOME_MSG_COUNT - 1) {
                    Message newMsg = new Message();
                    newMsg.what = CommentData.OPEN_KJ_APP_MAIN;
                    //三秒中之后开启转向新的页面
                    welcomePageHandler.sendMessageDelayed(newMsg, 1 * 1000);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                //实现导航点的滚动

            }


        });

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (currentItem <= CommentData.WELCOME_MSG_COUNT) {
                    currentItem = CommentData.WELCOME_MSG_COUNT + 1;
                    Message msg = new Message();
                    msg.what = CommentData.WELCOME_MSG_CANCLE;
                    welcomePageHandler.sendMessage(msg);
                }

                return false;
            }
        });

        //设置消息接收器

        final Intent serv_intent = new Intent(this, BgService.class);
        startService(serv_intent);


        PagerAdapter pageAdapter = new PagerAdapter() {


            @Override
            public void destroyItem(View container, int position, Object object) {
                // TODO Auto-generated method stub
                ((ViewPager) container).removeView(views.get(position));
                System.out.print("A:000000000001");
            }

            @Override
            public Object instantiateItem(View container, int position) {
                // TODO Auto-generated method stub
                ((ViewPager) container).addView(views.get(position));
                System.out.print("A:000000000002");
                return views.get(position);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                System.out.print("A:000000000003");
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return views.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                // TODO Auto-generated method stub
                // return titles.get(position);
                return "";
            }
        };

        viewPager.setAdapter(pageAdapter);


        initSQLiteDB();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        Message msg = new Message();
        msg.what = CommentData.WELCOME_MSG_SEND;
        currentItem = 1;
        welcomePageHandler.sendMessageDelayed(msg, 1 * 1000);

        //调用服务发送通知


        super.onStart();
    }

    @Override
    protected void onStop() {
        //停止图片切换

        super.onStop();
    }

    public void initSQLiteDB() {
        DBManger dbManger = new DBManger(getApplicationContext());
        List<List<String>> persons = new ArrayList<List<String>>();
        List<String> pers;
        for (int i = 0; i < 20; i++) {

            pers = new ArrayList<String>();
            pers.add("Emdgfit:" + i);
            pers.add(String.valueOf(i));
            persons.add(i, pers);
        }

        dbManger.add("person", persons);
        dbManger.closeDB();
    }

}
