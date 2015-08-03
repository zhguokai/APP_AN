package com.example.myapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.kj.myapp.data.CommentData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myapp.view.PullMsgService;
import org.apache.http.Header;


/**
 * Created by kuajie on 15/7/22.
 */
public class timeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.time, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Button btn = (Button) getView().findViewById(R.id.Remote_btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                String msg = requestJsonData();
                //调用远程数据
                //  remote_text.setText(msg);
                getImage();

            }
        });

        final Intent service = new Intent(this.getActivity(), PullMsgService.class);
        this.getActivity().startService(service);


    }

    public String requestJsonData() {
        final EditText remote_text = (EditText) getView().findViewById(R.id.editText_remote);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("username", "李四");


        client.post(CommentData.SERVER_URL + "rest/login", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                if (i == 200) {
                    String msg = new String(bytes);
                    String nowMsg = remote_text.getText().toString();
                    nowMsg += msg;
                    remote_text.setText(nowMsg);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

        return "";
    }

    public void getImage() {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        params.put("imgId", "0001");
        String[] allowedContentTypes = new String[]{"application/octet-stream", "image/png", "image/jpeg", "image/ico"};

        client.get(CommentData.SERVER_URL + "rest/imgUtil", params, new BinaryHttpResponseHandler(allowedContentTypes) {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                //缓存数据
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                ImageView imgview = (ImageView) getView().findViewById(R.id.remote_img);
                imgview.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                System.out.print(i);
            }
        });

    }

    public Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

}