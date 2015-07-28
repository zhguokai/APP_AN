package com.example.myapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ScrollView;

/**
 * Created by kuajie on 15/7/22.
 */
public class indexFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.indexfragment, container, false);
        return vi;

    }

    @Override
    public void onStart() {
        //先调用父类的初始化方法
        super.onStart();

        ScrollView sv = (ScrollView) this.getView().findViewById(R.id.scroller);
        CheckBox cb = (CheckBox) getActivity().findViewById(R.id.topVisable);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.os.Handler hander = new android.os.Handler();
                hander.post(new Runnable() {
                    @Override
                    public void run() {
                        sv.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
            }
        });

        sv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                System.out.println("AAAAAA" + motionEvent.getAction());
                switch (motionEvent.getAction()) {
                    // case MotionEvent.ACTION_DOWN:
                    //   getActivity().findViewById(R.id.topVisable).setVisibility(View.VISIBLE);
                    //    break;
                    case MotionEvent.ACTION_MOVE:

                        int y = view.getScrollY();
                        if (y == 0) {
                            cb.setVisibility(View.INVISIBLE);
                        } else {
                            if (cb.getVisibility() == View.INVISIBLE) {
                                cb.setVisibility(View.VISIBLE);
                            }
                        }

                        break;
                    default:
                        break;
                    //case MotionEvent.ACTION_UP:
                    //   getActivity().findViewById(R.id.topVisable).setVisibility(View.INVISIBLE);
                }

                return false;
            }
        });
    }


}