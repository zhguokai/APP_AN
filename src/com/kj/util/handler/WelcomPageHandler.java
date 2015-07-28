package com.kj.util.handler;


import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import com.kj.myapp.data.CommentData;

/**
 * Created by kuajie on 15/7/21.
 */
public class WelcomPageHandler extends Handler {
    //欢迎页面顺序
    private int currentItem = 0;
    private ViewPager viewPager = null;

    @Override
    public void handleMessage(Message msg) {
        //取消欢迎页面
        if (msg.what == CommentData.WELCOME_MSG_CANCLE) {
            this.removeMessages(CommentData.WELCOME_MSG_SEND);
            return;
        } else if (msg.what == CommentData.WELCOME_MSG_SEND) {
            //如果接收到欢迎页面发过来的消息
            currentItem++;
            viewPager.setCurrentItem(currentItem);

            if (currentItem <= CommentData.WELCOME_MSG_COUNT) {
                //五秒之后继续发送
                this.sendMessageDelayed(msg, 5 * 1000);
            }
        }

    }

    public void autoRunWelcomePager(ViewPager pager, int currentItem) {
        this.viewPager = pager;
        this.currentItem = currentItem;
    }
}
