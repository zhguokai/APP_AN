package com.example.myapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kuajie on 15/7/22.
 */
public class friendFRagement extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        ScrollView sv = (ScrollView)this.getView().findViewById(R.id.scroller);
//
//        sv.setOnTouchListener(new View.OnTouchListener(){
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()){
//                    case MotionEvent.ACTION_UP:
//                        break;
//                    case MotionEvent.ACTION_DOWN:
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                       // CheckBox cb = (CheckBox) findViewById(R.id.topVisable);
//                       // cb.setVisibility(View.INVISIBLE);
//                        break;
//                }
//
//                return false;
//            }
//        });

        return inflater.inflate(R.layout.frend, container, false);


    }
}