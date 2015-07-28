package com;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.example.myapp.MyStoreActivty;
import com.example.myapp.R;

/**
 * Created by kuajie on 15/7/27.
 */
public class BgService extends Service {

    private static final String TAG = "BgService";
    private Context context;

    private IBinder binder = new BgService.LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {


        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int start) {

        CreateInform();

        return START_STICKY;

    }

    //创建通知
    public void CreateInform() {
        //定义一个PendingIntent，当用户点击通知时，跳转到某个Activity(也可以发送广播等)
        Intent intent = new Intent(context, MyStoreActivty.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        //创建一个通知
//        Notification notification = new Notification(R.drawable.icon_1_n, "巴拉巴拉~~", System.currentTimeMillis());
//        notification.setLatestEventInfo(context, "点击查看一个人是看，2个人也是看", "点击查看详细内容", pendingIntent);

        Notification notification = new Notification.Builder(this.getApplicationContext())
                .setContentTitle("ABC:").setSmallIcon(R.drawable.icon_2_d)
                .setContentIntent(pendingIntent)
                .build();


        //用NotificationManager的notify方法通知用户生成标题栏消息通知
        NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        nManager.notify(100, notification);//id是应用中通知的唯一标识
        //如果拥有相同id的通知已经被提交而且没有被移除，该方法会用更新的信息来替换之前的通知。

        //关闭自己
        stopSelf();


    }

    @Override
    public void onDestroy() {
        System.out.println("Destory：AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    }

    public class LocalBinder extends Binder {
        BgService getService() {
            return BgService.this;
        }
    }

}
