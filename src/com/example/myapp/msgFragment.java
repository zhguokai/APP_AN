package com.example.myapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.kj.mode.Person;
import org.org.xrhh.util.orm.DBManger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kuajie on 15/7/22.
 */
public class msgFragment extends Fragment {
    ListView lv = null;
    MyListAdapter adapter = null;
    private List<Map<String, String>> listItems = new ArrayList<Map<String, String>>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.msgfragment, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        //取数据
        DBManger dbManger = new DBManger(getActivity().getApplicationContext());
        List<Person> perList = dbManger.query();


        for (int i = 0; i < perList.size(); i++) {
            Map map = new HashMap<String, String>();
            map.put("cu", String.valueOf(i));
            map.put("title", "商品阿蛟：" + perList.get(i).name);
            listItems.add(map);

        }

        lv = (ListView) this.getView().findViewById(R.id.listView);
        adapter = new MyListAdapter(this.getActivity().getApplicationContext());
        lv.setAdapter(adapter);

//        //初始化商品
//        for (int i = 0; i<20;i++){
//            Map<String,String> item = new HashMap<String,String>();
//            item.put("name","ITEM"+i);
//            item.put("count",String.valueOf(20 - i));
//            item.put("price","$"+String.valueOf(20-i)+".00");
//        }
    }


    // private ListAdapter adapter = new SimpleAdapter();

    //商品列表项
    //private List<Map<String,String>> itemList = new ArrayList<Map<String,String> >();

    protected void dialog(int position) {
        AlertDialog.Builder bu = new AlertDialog.Builder(getActivity());
        bu.setTitle("删除商品");
        bu.setMessage("放弃该商品的购买么？");
        bu.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listItems.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        bu.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        bu.show();
    }

    private class MyListAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater listContainer;

        public MyListAdapter(Context context1) {
            this.context = context1;
            listContainer = LayoutInflater.from(context);


        }

        @Override
        public int getCount() {
            return listItems.size();
        }

        @Override
        public Object getItem(int i) {
            return listItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            ListItemView listItemView = new ListItemView();

            if (view == null) {

                view = listContainer.inflate(R.layout.item, null);
                listItemView.image = (ImageView) view.findViewById(R.id.item_image);
                listItemView.addButton = (Button) view.findViewById(R.id.item_addBtn);
                listItemView.delBtn = (Button) view.findViewById(R.id.item_delBtn);
                listItemView.subBtton = (Button) view.findViewById(R.id.item_subBtn);
                listItemView.item_count = (EditText) view.findViewById(R.id.item_count);
                listItemView.title = (TextView) view.findViewById(R.id.item_shangpin);
                view.setTag(listItemView);

            } else {
                listItemView = (ListItemView) view.getTag();
            }

            String count = listItems.get(i).get("cu");
            listItemView.item_count.setText(count);
            listItemView.title.setText(listItems.get(i).get("title"));
            final ListItemView tempView = listItemView;

            listItemView.addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int oldcount = Integer.parseInt(tempView.item_count.getText().toString()) + 1;
                    Map map = listItems.get(i);
                    map.put("cu", String.valueOf(oldcount));
                    tempView.item_count.setText(String.valueOf(oldcount));

                    MyListAdapter.this.notifyDataSetChanged();
                }
            });

            listItemView.subBtton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int oldcount = Integer.parseInt(tempView.item_count.getText().toString()) - 1;

                    if (oldcount <= 0) {
                        dialog(i);

                    } else {
                        Map map = listItems.get(i);
                        map.put("cu", String.valueOf(oldcount));
                        tempView.item_count.setText(String.valueOf(oldcount));
                        MyListAdapter.this.notifyDataSetChanged();
                    }


                }
            });

            listItemView.delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog(i);
                }
            });


            return view;
        }

        private final class ListItemView {

            public ImageView image;
            public TextView title;
            public Button subBtton;
            public Button addButton;
            public Button delBtn;
            public EditText item_count;

        }

    }


}