package com.hmct.add_delete;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hmct.add_delete.Fragment.MyFragment;
import com.hmct.add_delete.Fragment.MyFragment1;
import com.hmct.add_delete.Fragment.MyFragment2;
import com.hmct.add_delete.Fragment.MyFragment3;
import com.hmct.add_delete.Fragment.MyFragment4;
import com.hmct.add_delete.Fragment.MyFragment5;
import com.hmct.add_delete.Fragment.MyFragment6;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.id.list;

/**
 * Created by wangyajie on 2018/10/26.
 */

public class FoodListActivity extends AppCompatActivity {
    private  FlowLayout getmTagLayout_usual;
    private Map<String,String> map;
//    private String[] mTextUsual = { "苹果",  "西瓜"};
    private String[] mTextUsual = { "苹果", "西兰花", "樱桃","萝卜", "猕猴桃", "梨","西红柿", "西瓜", "猕猴桃", "梨","西红柿", "西瓜"};
     ArrayList<String> list_usual = new ArrayList<String>();
    private MyFragment fg0;
    private MyFragment1 fg1;
    private MyFragment2 fg2;
    private MyFragment3 fg3;
    private MyFragment4 fg4;
    private MyFragment5 fg5;
    private MyFragment6 fg6;
    private FragmentManager fManager;
    private MyBtnClicker myBtnClicker = new MyBtnClicker();
    private TextView Vegetable,Fruit,Meat,Seafood,EggMilk,Drink,Other,textView_back;
//    private View view;

//    private LocalReceiver mReceiver;
//LocalBroadcastManager broadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodlist);
        initMap();
        initID();
        initList();
        Log.e("mTextUsual", String.valueOf(list_usual.size()));
        initLayoutUsual(list_usual);

        fManager = getFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        Fruit.performClick();   //模拟一次点击，既进去后选择第一项


//接收广播
//        try {
//            IntentFilter filter = new IntentFilter();
//            filter.addAction("fragment_home_left");
//            mReceiver = new LocalReceiver();
//            BroadCastManager.getInstance().registerReceiver(this,
//                    mReceiver, filter);//注册广播接收者
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        EventBus.getDefault().register(this);


//        fg0 = new MyFragment();
//        fTransaction.add(R.id.ly_content,fg0);
//        fTransaction.show(fg0);
//        fTransaction.commit();
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
    private void initID(){
        getmTagLayout_usual= (FlowLayout) findViewById(R.id.tag_usual);
        Vegetable= (TextView) findViewById(R.id.vegetable);
        Fruit= (TextView) findViewById(R.id.fruit);
        Meat= (TextView) findViewById(R.id.meat);
        Seafood= (TextView) findViewById(R.id.seafood);
        EggMilk= (TextView) findViewById(R.id.eggmilk);
        Drink= (TextView) findViewById(R.id.drink);
        Other= (TextView) findViewById(R.id.other);
        textView_back=(TextView) findViewById(R.id.textview_back);

        Vegetable.setOnClickListener(myBtnClicker);
        Fruit.setOnClickListener(myBtnClicker);
        Meat.setOnClickListener(myBtnClicker);
        Seafood.setOnClickListener(myBtnClicker);
        EggMilk.setOnClickListener(myBtnClicker);
        Drink.setOnClickListener(myBtnClicker);
        Other.setOnClickListener(myBtnClicker);
        textView_back.setOnClickListener(myBtnClicker);

//       view = (View) LayoutInflater.from(FoodListActivity.this).inflate(R.layout.text_view, getmTagLayout_usual, false);
    }
    private void initList() {


        for(int i=0;i<mTextUsual.length;i++){
            list_usual.add(mTextUsual[i]);
        }
Log.e("mTextUsual", String.valueOf(mTextUsual.length));
    }

    //重置所有文本的选中状态
    private void setSelected(){
        Fruit.setSelected(false);
        Vegetable.setSelected(false);
        Meat.setSelected(false);
        Seafood.setSelected(false);
        EggMilk.setSelected(false);
        Drink.setSelected(false);
        Other.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);
        if(fg4 != null)fragmentTransaction.hide(fg4);
        if(fg5 != null)fragmentTransaction.hide(fg5);
        if(fg6 != null)fragmentTransaction.hide(fg6);

        if(fg0 != null)fragmentTransaction.hide(fg0);
    }

    //常用食材
    public   void initLayoutUsual(final ArrayList<String> arr) {

        getmTagLayout_usual.removeAllViewsInLayout();
        /**
         * 创建 textView数组
         */
        final TextView[] textViews = new TextView[arr.size()];
        final TextView[] icons = new TextView[arr.size()];
        Log.e("arr.size()", String.valueOf(arr.size()));
        for (int i = 0; i < arr.size(); i++) {

            final int pos = i;

           final View view = (View) LayoutInflater.from(FoodListActivity.this).inflate(R.layout.text_view, getmTagLayout_usual, false);

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
                            getmTagLayout_usual.removeViewAt(j);
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

                        icons[j].setVisibility(View.VISIBLE);

                    }
                    for(int j = 0;j< MyFragment.textViews.length;j++){
                        MyFragment.icons[j].setVisibility(View.VISIBLE);
                    }
                    return true;
                }
            });

            getmTagLayout_usual.addView(view);
        }

//        getmTagLayout_usual.addView(inputLabel);
    }

    private void ChoseImage(String name,ImageView imageView){
        ApplicationInfo appInfo = getApplicationInfo();
        int id1 = getResources().getIdentifier(map.get(name), "drawable", appInfo.packageName);
        imageView.setImageResource(id1);


    }


    private class MyBtnClicker implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            FragmentTransaction fTransaction = fManager.beginTransaction();
            hideAllFragment(fTransaction);
            switch (view.getId()) {
                case R.id.fruit:
                    setSelected();
                    Fruit.setSelected(true);

                    if(fg0 == null){
                        fg0 = new MyFragment();

                        fTransaction.add(R.id.ly_content,fg0);
                    }else{
                        fTransaction.show(fg0);
                    }
                    break;

                case R.id.vegetable:
                    setSelected();
                    Vegetable.setSelected(true);
                    if(fg1 == null){
                        fg1 = new MyFragment1();

                        fTransaction.add(R.id.ly_content,fg1);
                    }else{
                        fTransaction.show(fg1);
                    }
                    break;

                case R.id.meat:
                    setSelected();
                    Meat.setSelected(true);

                    if(fg2 == null){
                        fg2 = new MyFragment2();

                        fTransaction.add(R.id.ly_content,fg2);
                    }else{
                        fTransaction.show(fg2);
                    }
                    break;

                case R.id.seafood:
                    setSelected();
                    Seafood.setSelected(true);

                    if(fg3 == null){
                        fg3 = new MyFragment3();

                        fTransaction.add(R.id.ly_content,fg3);
                    }else{
                        fTransaction.show(fg3);
                    }
                    break;

                case R.id.eggmilk:
                    setSelected();
                    EggMilk.setSelected(true);

                    if(fg4 == null){
                        fg4 = new MyFragment4();

                        fTransaction.add(R.id.ly_content,fg4);
                    }else{
                        fTransaction.show(fg4);
                    }
                    break;

                case R.id.drink:
                    setSelected();
                    Drink.setSelected(true);

                    if(fg5 == null){
                        fg5 = new MyFragment5();

                        fTransaction.add(R.id.ly_content,fg5);
                    }else{
                        fTransaction.show(fg5);
                    }
                    break;

                case R.id.other:
                    setSelected();
                    Other.setSelected(true);

                    if(fg6 == null){
                        fg6 = new MyFragment6();

                        fTransaction.add(R.id.ly_content,fg6);
                    }else{
                        fTransaction.show(fg6);
                    }
                    break;

                case R.id.textview_back:


                        finish();
                    break;

            }
            fTransaction.commit();
        }
    }





//    class LocalReceiver extends BroadcastReceiver{
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            //收到广播后的处理
//            String orderid = intent.getStringExtra("order");
//            loadData(orderid);
//            list_usual.add(orderid);
//            initLayoutUsual(list_usual);
//        }
//
//    }
//
//
//    private void loadData(String orderid){
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        BroadCastManager.getInstance().unregisterReceiver(this,mReceiver);//注销广播接收者
//        super.onDestroy();
//    }



    /**
     * 接收消息
     * @param msg
     */
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventComing(EventMessage<String> msg){
        if(msg.getType() == 2){
        list_usual.add(msg.getData());
            initLayoutUsual(list_usual);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


}