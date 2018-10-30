package com.hmct.add_delete.Fragment;

import android.app.Fragment;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hmct.add_delete.FlowLayout;
import com.hmct.add_delete.FoodListActivity;
import com.hmct.add_delete.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangyajie on 2018/7/27.
 */


public class MyFragment5 extends Fragment {

    private FlowLayout fragment_layout;
    private Map<String,String> map;
    String[] mTextUsual = { "西兰花", "猕猴桃", "西红柿","西兰花", "猕猴桃", "西红柿","西兰花","萝卜", "猕猴桃", "西红柿"};
    ArrayList<String> list_usual = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fruit,container,false);

        fragment_layout = view.findViewById(R.id.fragment_flowlayout );

        initMap();
//        initID();
        initList();
        initLayoutUsual(list_usual);
        return view;
    }

    private void initMap(){
        map = new HashMap<>();
        map.put("苹果","apple");
        map.put("西红柿","tomato");
        map.put("樱桃","cherry");
        map.put("西兰花","broccoli");
        map.put("萝卜","dadish");
        map.put("猕猴桃","kiwifruit");
        map.put("梨","pear");
        map.put("西瓜","watermelon");
    }

    private void initList() {


        for(int i=0;i<mTextUsual.length;i++){
            list_usual.add(mTextUsual[i]);
        }

    }

    //常用食材
    private void initLayoutUsual(final ArrayList<String> arr) {

        fragment_layout.removeAllViewsInLayout();
        /**
         * 创建 textView数组
         */
        final TextView[] textViews = new TextView[arr.size()];
        final TextView[] icons = new TextView[arr.size()];

        for (int i = 0; i < arr.size(); i++) {

            final int pos = i;

            final View view = (View) LayoutInflater.from(getActivity()).inflate(R.layout.text_view_big, fragment_layout, false);

            final TextView text = (TextView) view.findViewById(R.id.text);  //查找  到当前     textView
            final TextView icon = (TextView) view.findViewById(R.id.delete_icon);  //查找  到当前  删除小图标
            final ImageView imageView=view.findViewById(R.id.image);
            final FrameLayout frameLayout=view.findViewById(R.id.framelayout);//整个图标

            // 将     已有标签设置成      可选标签
            text.setText(list_usual.get(i));
            /**
             * 将当前  textView  赋值给    textView数组
             */
            textViews[i] = text;
            icons[i] = icon;

            ChoseImage(list_usual.get(i),imageView);


            //设置    单击事件：
            icon.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //遍历  图标  删除 当前  被点击项
                    for(int j = 0; j < icons.length;j++){
                        if(icon.equals(icons[j])){  //获取   当前  点击删除图标的位置：
                            fragment_layout.removeViewAt(j);
                            list_usual.remove(j);
                            initLayoutUsual(list_usual);
                        }
                    }
                }
            });

            final int finalI = i;
            frameLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    frameLayout.setActivated(!frameLayout.isActivated());
//                    if (addtag==1){
//                        Toast.makeText(MainActivity.this,"只能选择一个",Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    addname=list_usual.get(finalI);
//                    if (frameLayout.isActivated()) {
//                        frameLayout.setBackgroundColor(Color.YELLOW);
//                        detal_layout_cang.setBackgroundColor(Color.parseColor("#7597b3"));
//                        addtag = 1;
//                    } else {
//                        frameLayout.setBackgroundColor(0);
//                        addtag = 0;
//                    }




                }
            });

            frameLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    for(int j = 0;j< textViews.length;j++){
//                            if(text.equals(textViews[j])){//非当前  textView
//                                icons[j].setVisibility(View.VISIBLE);
//                            }

                        icons[j].setVisibility(View.VISIBLE);
                    }
                    return true;
                }
            });



            fragment_layout.addView(view);
        }

//        getmTagLayout_usual.addView(inputLabel);
    }

    private void ChoseImage(String name,ImageView imageView){
        ApplicationInfo appInfo = getActivity().getApplicationInfo();
        int id1 = getResources().getIdentifier(map.get(name), "drawable", appInfo.packageName);
        imageView.setImageResource(id1);


    }
}




