package com.myapp.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.example.myapp.MyStoreActivty;
import com.example.myapp.R;
import com.kj.myapp.data.CommentData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.apache.http.Header;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kuajie on 15/7/28.
 */
public class PullMsgService extends Service {
    static int msg_id = 101;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {

                //发送通知拦消息
                pullMsgFromServer();

            }
            super.handleMessage(msg);
        }
    };
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            // 需要做的事:发送消息
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //定时器
        timer.schedule(task, 1000, 60 * 1000);

    }

    public void pullMsgFromServer() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(CommentData.SERVER_URL + "rest/msgPush", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                CreateInform(String.valueOf(bytes));
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                System.out.println(i);
            }
        });

    }

    //创建通知
    public void CreateInform(String msg) {
        //定义一个PendingIntent，当用户点击通知时，跳转到某个Activity(也可以发送广播等)
        Intent intent = new Intent(this.getApplicationContext(), MyStoreActivty.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, intent, 0);

        //创建一个通知
//        Notification notification = new Notification(R.drawable.icon_1_n, "巴拉巴拉~~", System.currentTimeMillis());
//        notification.setLatestEventInfo(context, "点击查看一个人是看，2个人也是看", "点击查看详细内容", pendingIntent);

        Notification notification = new Notification.Builder(this.getApplicationContext())
                .setContentTitle("商品更新").setSmallIcon(R.drawable.icon_2_d)
                .setContentIntent(pendingIntent)
                .setContentText(msg)
                .build();


        //用NotificationManager的notify方法通知用户生成标题栏消息通知
        NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        nManager.notify(++msg_id, notification);//id是应用中通知的唯一标识
        //如果拥有相同id的通知已经被提交而且没有被移除，该方法会用更新的信息来替换之前的通知。

        //关闭自己
        //stopSelf();


    }
}
