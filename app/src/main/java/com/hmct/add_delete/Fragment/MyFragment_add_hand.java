package com.hmct.add_delete.Fragment;

/**
 * Created by wangyajie on 2018/10/30.
 */

import android.app.AlertDialog;
import android.app.Fragment;

        import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
        import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
        import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hmct.add_delete.EasyPickerView;
import com.hmct.add_delete.EventMessage;
import com.hmct.add_delete.FlowLayout;
        import com.hmct.add_delete.FoodListActivity;
import com.hmct.add_delete.MainActivity;
import com.hmct.add_delete.R;
import com.hmct.add_delete.TagItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Map;

/**
 * Created by wangyajie on 2018/7/27.
 */


public class MyFragment_add_hand extends Fragment {

    private FlowLayout mTagLayout,getmTagLayout_usual,mTagLayout_bian,mTagLayout_dong;
    private ArrayList<TagItem> mAddTags = new ArrayList<TagItem>();

    private EditText inputLabel;
    private Button btnSure;
    public int addtag=0;
    public String addname;
    //食材信息
    private PopupWindow popWin = null; // 弹出窗口
    private View popView = null; // 保存弹出窗口布局
    private LinearLayout detai_layout;
    private RelativeLayout detal_layout_cang,detal_layout_bian,detal_layout_dong;
    private ImageView detal_close,detal_image,imageView_add;
    private TextView detal_Name,detal_Freshday,detal_Shi,detal_Ge,textView_cang,textView_bian,textView_dong,textView_back;
    private EasyPickerView epvH,epvM;
    private String shi,ge;
    private    Map<String,String> map;

    // 存放标签数据的数组
//    String[] mTextStr = { "苹果", "梨", "西瓜"};
    String[] mTextStr = { "苹果", "梨", "西瓜"};
    String[] mTextStr_bian = {"樱桃","萝卜"};
    String[] mTextStr_dong = { "西兰花", "樱桃","萝卜"};
    String[] mTextUsual = { "苹果", "西兰花", "樱桃","萝卜", "猕猴桃", "梨","西红柿", "西瓜", "猕猴桃", "梨","西红柿", "西瓜"};



    ArrayList<String>  list = new ArrayList<String>();
    ArrayList<String>  list_bian = new ArrayList<String>();
    ArrayList<String>  list_dong = new ArrayList<String>();
    ArrayList<String>  list_usual = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_hand,container,false);

        initMap();
        detal_layout_cang = view.findViewById(R.id.tag_layoutcang);
        detal_layout_bian=view.findViewById(R.id.tag_layoutbianwen);
        detal_layout_dong=view.findViewById(R.id.tag_layoutlengdong);

        mTagLayout = (FlowLayout) view.findViewById(R.id.tag_layout);
        mTagLayout_bian = view.findViewById(R.id.tag_layoutbian);
        mTagLayout_dong = view.findViewById(R.id.tag_layoutdong);
        getmTagLayout_usual=view.findViewById(R.id.tag_usual);
        textView_bian=view.findViewById(R.id.textview_bian);
        textView_dong=view.findViewById(R.id.textview_dong);
        textView_cang=view.findViewById(R.id.textview_cang);
        imageView_add=view.findViewById(R.id.image_add);
//        textView_back=view.findViewById(R.id.textview_back);
        initList();
        initLayout(list);
        initLayout_bian(list_bian);
        initLayout_dong(list_dong);
        initLayoutUsual(list_usual);
        initPopView();
        initBtnListener();

        EventBus.getDefault().register(this);
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
        for(int i=0;i<mTextStr.length;i++){
            list.add(mTextStr[i]);
        }

        for(int i=0;i<mTextStr_bian.length;i++){
            list_bian.add(mTextStr_bian[i]);
        }

        for(int i=0;i<mTextStr_dong.length;i++){
            list_dong.add(mTextStr_dong[i]);
        }

        for(int i=0;i<mTextUsual.length;i++){
            list_usual.add(mTextUsual[i]);
        }

    }


    private void initPopView(){
        //弹出食材详情
        popView=LayoutInflater.from(getActivity()).inflate(R.layout.material_detail, null);
        popWin = new PopupWindow(popView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true); // 实例化PopupWindow
        popWin.setAnimationStyle(R.style.popupAnimation);
        detai_layout = popView.findViewById(R.id.detal_linearlayout);
        detal_close = popView.findViewById(R.id.close);
        detal_image = popView.findViewById(R.id.detal_img);
        detal_Name = popView.findViewById(R.id.detal_name);
        detal_Freshday = popView.findViewById(R.id.detal_freshday);
        detal_Shi = popView.findViewById(R.id.detal_shi);
        detal_Ge = popView.findViewById(R.id.detal_ge);

    }


    private void initBtnListener() {
        /**
         * 初始化  单击事件：
         */
//        btnSure.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
////                String label = inputLabel.getText().toString().trim();
//
//                String[] newStr = new String[mTagLayout.getChildCount()];
//
//                /**
//                 * 获取  子view的数量   并添加进去
//                 */
//                if(label!=null&&!label.equals("")){
//                    for(int m = 0;m < mTagLayout.getChildCount()-1;m++){
//                        newStr[m] =((TextView)mTagLayout.getChildAt(m).
//                                findViewById(R.id.text)).getText().toString();//根据  当前   位置查找到 当前    textView中标签  内容
//                    }
//                    list.add(label);
//                    initLayout(list);
//
//                    inputLabel.setText("");
//                }
//            }
//        });

        //冷藏区域点击
        mTagLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


//                String[] newStr = new String[mTagLayout.getChildCount()];
                /**
                 * 获取  子view的数量   并添加进去
                 */
                if(addname!=null&&!addname.equals("")&&addtag==1){
//                    for(int m = 0;m < mTagLayout.getChildCount()-1;m++){
//                        newStr[m] =((TextView)mTagLayout.getChildAt(m).
//                                findViewById(R.id.text)).getText().toString();//根据  当前   位置查找到 当前    textView中标签  内容
//                    }
                    detal_layout_cang.setBackgroundColor(Color.parseColor("#00bcd4"));
                    detal_layout_bian.setBackgroundColor(Color.parseColor("#88AD66"));
                    detal_layout_dong.setBackgroundColor(Color.parseColor("#0099ff"));
//                    frameLayout.setBackgroundColor(0);
                    addtag=0;
                    Log.e("加",addname);
                    list.add(addname);
                    initLayout(list);
                    //之前选中的背景消除
                    initLayoutUsual(list_usual);

                }
            }
        });


        //变温区域点击
        mTagLayout_bian.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


//                String[] newStr = new String[mTagLayout_bian.getChildCount()];
                /**
                 * 获取  子view的数量   并添加进去
                 */
                if(addname!=null&&!addname.equals("")&&addtag==1){
//                    for(int m = 0;m < mTagLayout_bian.getChildCount()-1;m++){
//                        newStr[m] =((TextView)mTagLayout_bian.getChildAt(m).
//                                findViewById(R.id.text)).getText().toString();//根据  当前   位置查找到 当前    textView中标签  内容
//                    }
                    detal_layout_cang.setBackgroundColor(Color.parseColor("#00bcd4"));
                    detal_layout_bian.setBackgroundColor(Color.parseColor("#88AD66"));
                    detal_layout_dong.setBackgroundColor(Color.parseColor("#0099ff"));

//                    frameLayout.setBackgroundColor(0);
                    addtag=0;
                    list_bian.add(addname);
                    initLayout_bian(list_bian);
                    //之前选中的背景消除
                    initLayoutUsual(list_usual);

                }
            }
        });


        //冷冻区域点击
        mTagLayout_dong.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


//                String[] newStr = new String[mTagLayout_dong.getChildCount()];
                /**
                 * 获取  子view的数量   并添加进去
                 */
                if(addname!=null&&!addname.equals("")&&addtag==1){
//                    for(int m = 0;m < mTagLayout_dong.getChildCount()-1;m++){
//                        newStr[m] =((TextView)mTagLayout_dong.getChildAt(m).
//                                findViewById(R.id.text)).getText().toString();//根据  当前   位置查找到 当前    textView中标签  内容
//                    }
                    detal_layout_cang.setBackgroundColor(Color.parseColor("#00bcd4"));
                    detal_layout_bian.setBackgroundColor(Color.parseColor("#88AD66"));
                    detal_layout_dong.setBackgroundColor(Color.parseColor("#0099ff"));
//                    frameLayout.setBackgroundColor(0);
                    addtag=0;
                    list_dong.add(addname);
                    initLayout_dong(list_dong);
                    //之前选中的背景消除
                    initLayoutUsual(list_usual);

                }
            }
        });

        imageView_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), FoodListActivity.class);
                startActivity(intent);
            }
        });

//        textView_back.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                getActivity().finish();
//            }
//        });

        //关闭食材详情
//        detal_close.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                popWin.dismiss();
//            }
//        });
    }



    //冷藏室
    private void initLayout(final ArrayList<String> arr) {

        mTagLayout.removeAllViewsInLayout();

        if (arr.size()==0)
        {textView_cang.setVisibility(View.VISIBLE);}
        else {
            textView_cang.setVisibility(View.GONE);

        }

        /**
         * 创建 textView数组
         */
        final TextView[] textViews = new TextView[arr.size()];
        final TextView[] icons = new TextView[arr.size()];
        final TextView[] freshday_cangs = new TextView[arr.size()];
        for (int i = 0; i < arr.size(); i++) {

            final int pos = i;

            final View view = (View) LayoutInflater.from(getActivity()).inflate(R.layout.text_view, mTagLayout, false);

            final TextView text = (TextView) view.findViewById(R.id.text);  //查找  到当前     textView
            final TextView freshday_cang = (TextView) view.findViewById(R.id.freshday);  //查找  到当前     textView

            final TextView icon = (TextView) view.findViewById(R.id.delete_icon);  //查找  到当前  删除小图标
            final ImageView imageView=view.findViewById(R.id.image);
            final FrameLayout frameLayout=view.findViewById(R.id.framelayout);//整个图标

            // 将     已有标签设置成      可选标签
            text.setText(list.get(i));
            /**
             * 将当前  textView  赋值给    textView数组
             */
            textViews[i] = text;
            icons[i] = icon;
            freshday_cangs[i]=freshday_cang;

            freshday_cangs[i].setVisibility(View.VISIBLE);


            ChoseImage(list.get(i),imageView);



            //设置    单击事件：
            icon.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //遍历  图标  删除 当前  被点击项
                    for(int j = 0; j < icons.length;j++){
                        if(icon.equals(icons[j])){  //获取   当前  点击删除图标的位置：
                            mTagLayout.removeViewAt(j);
                            list.remove(j);
                            initLayout(list);
                        }
                    }
                }
            });

            final int finalI = i;
            frameLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //弹出食材详情,popview
                    popWin.setFocusable(false);
                    detai_layout.getBackground().setAlpha(150);
                    popWin.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0); // 显示弹出窗口

                    detal_Name.setText(textViews[finalI].getText());
                    ChoseImage(textViews[finalI].getText().toString(),detal_image);


                    detal_close.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

//                            textViews[finalI].setText("苹果");

                            freshday_cangs[finalI].setText(detal_Shi.getText().toString() + detal_Ge.getText().toString());
                            if (Integer.parseInt(freshday_cangs[finalI].getText().toString())>10)
                            {
                                freshday_cangs[finalI].setBackgroundResource(R.drawable.text_view_freshday_red);;
                            }
//                            if (Integer.parseInt(freshday_cangs[finalI].getText().toString())<5)
//                            {
//                                freshday_cangs[finalI].setBackgroundResource(R.drawable.text_view_freshday_red);;
//                            }

                            popWin.dismiss();

                        }
                    });

                    detal_Freshday.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            //dialog方式弹出
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            final AlertDialog dialog = builder.create();

//                         dialog = new AlertDialog.Builder(MainActivity.this, R.style.CustomDialogStyle).create();

                            final View view_dialog = View.inflate(getActivity(), R.layout.dialog, null);
                            // dialog.setView(view);// 将自定义的布局文件设置给dialog
                            dialog.setView(view_dialog, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题

//                            RelativeLayout dialog_relative=view_dialog.findViewById(R.id.dialog_linlayout);
//                            RelativeLayout dialog_relative1=view_dialog.findViewById(R.id.dialog_relayout);
//                            LinearLayout dialog_linearlayout=view_dialog.findViewById(R.id.dialog_linlayout);

//                           dialog_linearlayout.getBackground().setAlpha(0);



                            ImageView dialog_Fault= view_dialog.findViewById(R.id.dialog_cancel);
                            ImageView dialog_Correct= view_dialog.findViewById(R.id.dialog_ok);
                            ImageView dialog_imgage= view_dialog.findViewById(R.id.dialog_img);

                            ChoseImage(detal_Name.getText().toString(),dialog_imgage);

                            shi= detal_Shi.getText().toString();
                            ge= detal_Ge.getText().toString();

                            //滚动选择
                            //十位
                            epvH = (EasyPickerView)view_dialog.findViewById(R.id.epv_h);
                            final ArrayList<String> hDataList = new ArrayList<>();
                            for (int i = 0; i < 10; i++)
                                hDataList.add("" + i);

                            epvH.setDataList(hDataList);
                            epvH.moveTo(Integer.parseInt(detal_Shi.getText().toString()));
                            epvH.setOnScrollChangedListener(new EasyPickerView.OnScrollChangedListener() {
                                @Override
                                public void onScrollChanged(int curIndex) {
//                                    hour = Integer.parseInt(hDataList.get(curIndex));
////                                    tv.setText(hour + "h" + minute + "m");
//                                    etriqi.setText(hour*30+minute+"天");
//                                    detal_Shi.setText(hDataList.get(curIndex));
                                }

                                @Override
                                public void onScrollFinished(int curIndex) {
                                    shi = hDataList.get(curIndex);
////                                    tv.setText(hour + "h" + minute + "m");
//                                    etriqi.setText(hour*30+minute+"天");

//                                    detal_Shi.setText(hDataList.get(curIndex));
                                }
                            });

                            //个位

                            epvM = (EasyPickerView)view_dialog.findViewById(R.id.epv_m);
                            final ArrayList<String> dataList2 = new ArrayList<>();
                            for (int i = 0; i < 10; i++)
                                dataList2.add("" + i);

                            epvM.setDataList(dataList2);
                            epvM.moveTo(Integer.parseInt(detal_Ge.getText().toString()));
                            epvM.setOnScrollChangedListener(new EasyPickerView.OnScrollChangedListener() {
                                @Override
                                public void onScrollChanged(int curIndex) {

                                }

                                @Override
                                public void onScrollFinished(int curIndex) {
                                    ge = dataList2.get(curIndex);
////                                        tv.setText(hour + "h" + minute + "m");
//                                    etriqi.setText(hour*30+minute+"天");
//                                    detal_Ge.setText(dataList2.get(curIndex));
                                }
                            });


                            dialog_Fault.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();// 隐藏dialog
                                }
                            });

                            dialog_Correct.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    detal_Shi.setText(shi);
                                    detal_Ge.setText(ge);
                                    dialog.dismiss();// 隐藏dialog
                                }
                            });

                            dialog.show();

//                            dialog_relative.getBackground().setAlpha(150);
// dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                            WindowManager.LayoutParams lp=dialog.getWindow().getAttributes();
//                            lp.alpha=0.5f;
//
//                            dialog.getWindow().setAttributes(lp);
                        }
                    });





//                    Toast.makeText(MainActivity.this,textViews[finalI].getText().toString(),Toast.LENGTH_SHORT).show();
//                    textViews[finalI].setText("苹果");

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
                        freshday_cangs[j].setVisibility(View.INVISIBLE);
                    }
                    return true;
                }
            });

            mTagLayout.addView(view);
        }

//        mTagLayout.addView(inputLabel);
    }


    //变温室
    private void initLayout_bian(final ArrayList<String> arr) {

        mTagLayout_bian.removeAllViewsInLayout();
        if (arr.size()==0)
        {textView_bian.setVisibility(View.VISIBLE);}
        else {
            textView_bian.setVisibility(View.GONE);
        }

        /**
         * 创建 textView数组
         */
        final TextView[] textViews_bian = new TextView[arr.size()];
        final TextView[] icons_bian = new TextView[arr.size()];
        final TextView[] freshday_bians = new TextView[arr.size()];
        for (int i = 0; i < arr.size(); i++) {

            final int pos = i;

            final View view_bian = (View) LayoutInflater.from(getActivity()).inflate(R.layout.text_view, mTagLayout_bian, false);

            final TextView text_bian = (TextView) view_bian.findViewById(R.id.text);  //查找  到当前     textView
            final TextView freshday_bian = (TextView) view_bian.findViewById(R.id.freshday);  //查找  到当前     textView

            final TextView icon_bian = (TextView) view_bian.findViewById(R.id.delete_icon);  //查找  到当前  删除小图标
            final ImageView imageView_bian=view_bian.findViewById(R.id.image);
            final FrameLayout frameLayout_bian=view_bian.findViewById(R.id.framelayout);//整个图标

            // 将     已有标签设置成      可选标签
            text_bian.setText(list_bian.get(i));
            /**
             * 将当前  textView  赋值给    textView数组
             */
            textViews_bian[i] = text_bian;
            icons_bian[i] = icon_bian;
            freshday_bians[i]=freshday_bian;

            freshday_bians[i].setVisibility(View.VISIBLE);


            ChoseImage(list_bian.get(i),imageView_bian);



            //设置    单击事件：
            icon_bian.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //遍历  图标  删除 当前  被点击项

//                    if (icons.length==1){
//                        textView_bian.setVisibility(View.VISIBLE);
//                    }
                    for(int j = 0; j < icons_bian.length;j++){
                        if(icon_bian.equals(icons_bian[j])){  //获取   当前  点击删除图标的位置：
                            mTagLayout_bian.removeViewAt(j);
                            list_bian.remove(j);
                            initLayout_bian(list_bian);
                        }
                    }
                }
            });

            final int finalI = i;
            frameLayout_bian.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //弹出食材详情,popview
                    popWin.setFocusable(false);
                    detai_layout.getBackground().setAlpha(150);
                    popWin.showAtLocation(view_bian, Gravity.NO_GRAVITY, 0, 0); // 显示弹出窗口

                    detal_Name.setText(textViews_bian[finalI].getText());
                    ChoseImage(textViews_bian[finalI].getText().toString(),detal_image);


                    detal_close.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

//                            textViews[finalI].setText("苹果");

                            freshday_bians[finalI].setText(detal_Shi.getText().toString() + detal_Ge.getText().toString());
                            if (Integer.parseInt(freshday_bians[finalI].getText().toString())>10)
                            {
                                freshday_bians[finalI].setBackgroundResource(R.drawable.text_view_freshday_red);;
                            }
//                            if (Integer.parseInt(freshday_cangs[finalI].getText().toString())<5)
//                            {
//                                freshday_cangs[finalI].setBackgroundResource(R.drawable.text_view_freshday_red);;
//                            }

                            popWin.dismiss();

                        }
                    });

                    detal_Freshday.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            //dialog方式弹出
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            final AlertDialog dialog = builder.create();

//                         dialog = new AlertDialog.Builder(MainActivity.this, R.style.CustomDialogStyle).create();

                            final View view_dialog = View.inflate(getActivity(), R.layout.dialog, null);
                            // dialog.setView(view);// 将自定义的布局文件设置给dialog
                            dialog.setView(view_dialog, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题

//                            RelativeLayout dialog_relative=view_dialog.findViewById(R.id.dialog_linlayout);
//                            RelativeLayout dialog_relative1=view_dialog.findViewById(R.id.dialog_relayout);
//                            LinearLayout dialog_linearlayout=view_dialog.findViewById(R.id.dialog_linlayout);

//                           dialog_linearlayout.getBackground().setAlpha(0);



                            ImageView dialog_Fault= view_dialog.findViewById(R.id.dialog_cancel);
                            ImageView dialog_Correct= view_dialog.findViewById(R.id.dialog_ok);
                            ImageView dialog_imgage= view_dialog.findViewById(R.id.dialog_img);

                            ChoseImage(detal_Name.getText().toString(),dialog_imgage);

                            shi= detal_Shi.getText().toString();
                            ge= detal_Ge.getText().toString();

                            //滚动选择
                            //十位
                            epvH = (EasyPickerView)view_dialog.findViewById(R.id.epv_h);
                            final ArrayList<String> hDataList = new ArrayList<>();
                            for (int i = 0; i < 10; i++)
                                hDataList.add("" + i);

                            epvH.setDataList(hDataList);
                            epvH.moveTo(Integer.parseInt(detal_Shi.getText().toString()));
                            epvH.setOnScrollChangedListener(new EasyPickerView.OnScrollChangedListener() {
                                @Override
                                public void onScrollChanged(int curIndex) {
//                                    hour = Integer.parseInt(hDataList.get(curIndex));
////                                    tv.setText(hour + "h" + minute + "m");
//                                    etriqi.setText(hour*30+minute+"天");
//                                    detal_Shi.setText(hDataList.get(curIndex));
                                }

                                @Override
                                public void onScrollFinished(int curIndex) {
                                    shi = hDataList.get(curIndex);
////                                    tv.setText(hour + "h" + minute + "m");
//                                    etriqi.setText(hour*30+minute+"天");

//                                    detal_Shi.setText(hDataList.get(curIndex));
                                }
                            });

                            //个位

                            epvM = (EasyPickerView)view_dialog.findViewById(R.id.epv_m);
                            final ArrayList<String> dataList2 = new ArrayList<>();
                            for (int i = 0; i < 10; i++)
                                dataList2.add("" + i);

                            epvM.setDataList(dataList2);
                            epvM.moveTo(Integer.parseInt(detal_Ge.getText().toString()));
                            epvM.setOnScrollChangedListener(new EasyPickerView.OnScrollChangedListener() {
                                @Override
                                public void onScrollChanged(int curIndex) {

                                }

                                @Override
                                public void onScrollFinished(int curIndex) {
                                    ge = dataList2.get(curIndex);
////                                        tv.setText(hour + "h" + minute + "m");
//                                    etriqi.setText(hour*30+minute+"天");
//                                    detal_Ge.setText(dataList2.get(curIndex));
                                }
                            });


                            dialog_Fault.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();// 隐藏dialog
                                }
                            });

                            dialog_Correct.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    detal_Shi.setText(shi);
                                    detal_Ge.setText(ge);
                                    dialog.dismiss();// 隐藏dialog
                                }
                            });

                            dialog.show();

//                            dialog_relative.getBackground().setAlpha(150);
// dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                            WindowManager.LayoutParams lp=dialog.getWindow().getAttributes();
//                            lp.alpha=0.5f;
//
//                            dialog.getWindow().setAttributes(lp);
                        }
                    });





//                    Toast.makeText(MainActivity.this,textViews[finalI].getText().toString(),Toast.LENGTH_SHORT).show();
//                    textViews[finalI].setText("苹果");

                }
            });

            frameLayout_bian.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    for(int j = 0;j< textViews_bian.length;j++){
//                            if(text.equals(textViews[j])){//非当前  textView
//                                icons[j].setVisibility(View.VISIBLE);
//                            }
                        icons_bian[j].setVisibility(view.VISIBLE);
                        freshday_bians[j].setVisibility(view.GONE);
                    }
                    return true;
                }
            });

            mTagLayout_bian.addView(view_bian);
        }

//        mTagLayout.addView(inputLabel);
    }



    //冷冻室
    private void initLayout_dong(final ArrayList<String> arr) {

        mTagLayout_dong.removeAllViewsInLayout();

        if (arr.size()==0)
        {textView_dong.setVisibility(View.VISIBLE);}
        else {
            textView_dong.setVisibility(View.GONE);
        }

        /**
         * 创建 textView数组
         */
        final TextView[] textViews = new TextView[arr.size()];
        final TextView[] icons = new TextView[arr.size()];
        final TextView[] freshday_cangs = new TextView[arr.size()];
        for (int i = 0; i < arr.size(); i++) {

            final int pos = i;

            final View view = (View) LayoutInflater.from(getActivity()).inflate(R.layout.text_view, mTagLayout_dong, false);

            final TextView text = (TextView) view.findViewById(R.id.text);  //查找  到当前     textView
            final TextView freshday_cang = (TextView) view.findViewById(R.id.freshday);  //查找  到当前     textView

            final TextView icon = (TextView) view.findViewById(R.id.delete_icon);  //查找  到当前  删除小图标
            final ImageView imageView=view.findViewById(R.id.image);
            final FrameLayout frameLayout_dong=view.findViewById(R.id.framelayout);//整个图标

            // 将     已有标签设置成      可选标签
            text.setText(list_dong.get(i));
            /**
             * 将当前  textView  赋值给    textView数组
             */
            textViews[i] = text;
            icons[i] = icon;
            freshday_cangs[i]=freshday_cang;

            freshday_cangs[i].setVisibility(View.VISIBLE);


            ChoseImage(list_dong.get(i),imageView);



            //设置    单击事件：
            icon.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //遍历  图标  删除 当前  被点击项
//                    if (icons.length==1){
//                        textView_bian.setVisibility(View.VISIBLE);
//                    }
                    for(int j = 0; j < icons.length;j++){
                        if(icon.equals(icons[j])){  //获取   当前  点击删除图标的位置：
                            mTagLayout_dong.removeViewAt(j);
                            list_dong.remove(j);
                            initLayout_dong(list_dong);
                        }
                    }
                }
            });

            final int finalI = i;
            frameLayout_dong.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //弹出食材详情,popview
                    popWin.setFocusable(false);
                    detai_layout.getBackground().setAlpha(150);
                    popWin.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0); // 显示弹出窗口

                    detal_Name.setText(textViews[finalI].getText());
                    ChoseImage(textViews[finalI].getText().toString(),detal_image);


                    detal_close.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

//                            textViews[finalI].setText("苹果");

                            freshday_cangs[finalI].setText(detal_Shi.getText().toString() + detal_Ge.getText().toString());
                            if (Integer.parseInt(freshday_cangs[finalI].getText().toString())>10)
                            {
                                freshday_cangs[finalI].setBackgroundResource(R.drawable.text_view_freshday_red);;
                            }
//                            if (Integer.parseInt(freshday_cangs[finalI].getText().toString())<5)
//                            {
//                                freshday_cangs[finalI].setBackgroundResource(R.drawable.text_view_freshday_red);;
//                            }

                            popWin.dismiss();

                        }
                    });

                    detal_Freshday.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            //dialog方式弹出
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            final AlertDialog dialog = builder.create();

//                         dialog = new AlertDialog.Builder(MainActivity.this, R.style.CustomDialogStyle).create();

                            final View view_dialog = View.inflate(getActivity(), R.layout.dialog, null);
                            // dialog.setView(view);// 将自定义的布局文件设置给dialog
                            dialog.setView(view_dialog, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题

//                            RelativeLayout dialog_relative=view_dialog.findViewById(R.id.dialog_linlayout);
//                            RelativeLayout dialog_relative1=view_dialog.findViewById(R.id.dialog_relayout);
//                            LinearLayout dialog_linearlayout=view_dialog.findViewById(R.id.dialog_linlayout);

//                           dialog_linearlayout.getBackground().setAlpha(0);



                            ImageView dialog_Fault= view_dialog.findViewById(R.id.dialog_cancel);
                            ImageView dialog_Correct= view_dialog.findViewById(R.id.dialog_ok);
                            ImageView dialog_imgage= view_dialog.findViewById(R.id.dialog_img);

                            ChoseImage(detal_Name.getText().toString(),dialog_imgage);

                            shi= detal_Shi.getText().toString();
                            ge= detal_Ge.getText().toString();

                            //滚动选择
                            //十位
                            epvH = (EasyPickerView)view_dialog.findViewById(R.id.epv_h);
                            final ArrayList<String> hDataList = new ArrayList<>();
                            for (int i = 0; i < 10; i++)
                                hDataList.add("" + i);

                            epvH.setDataList(hDataList);
                            epvH.moveTo(Integer.parseInt(detal_Shi.getText().toString()));
                            epvH.setOnScrollChangedListener(new EasyPickerView.OnScrollChangedListener() {
                                @Override
                                public void onScrollChanged(int curIndex) {
//                                    hour = Integer.parseInt(hDataList.get(curIndex));
////                                    tv.setText(hour + "h" + minute + "m");
//                                    etriqi.setText(hour*30+minute+"天");
//                                    detal_Shi.setText(hDataList.get(curIndex));
                                }

                                @Override
                                public void onScrollFinished(int curIndex) {
                                    shi = hDataList.get(curIndex);
////                                    tv.setText(hour + "h" + minute + "m");
//                                    etriqi.setText(hour*30+minute+"天");

//                                    detal_Shi.setText(hDataList.get(curIndex));
                                }
                            });

                            //个位

                            epvM = (EasyPickerView)view_dialog.findViewById(R.id.epv_m);
                            final ArrayList<String> dataList2 = new ArrayList<>();
                            for (int i = 0; i < 10; i++)
                                dataList2.add("" + i);

                            epvM.setDataList(dataList2);
                            epvM.moveTo(Integer.parseInt(detal_Ge.getText().toString()));
                            epvM.setOnScrollChangedListener(new EasyPickerView.OnScrollChangedListener() {
                                @Override
                                public void onScrollChanged(int curIndex) {

                                }

                                @Override
                                public void onScrollFinished(int curIndex) {
                                    ge = dataList2.get(curIndex);
////                                        tv.setText(hour + "h" + minute + "m");
//                                    etriqi.setText(hour*30+minute+"天");
//                                    detal_Ge.setText(dataList2.get(curIndex));
                                }
                            });


                            dialog_Fault.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();// 隐藏dialog
                                }
                            });

                            dialog_Correct.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    detal_Shi.setText(shi);
                                    detal_Ge.setText(ge);
                                    dialog.dismiss();// 隐藏dialog
                                }
                            });

                            dialog.show();

//                            dialog_relative.getBackground().setAlpha(150);
// dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                            WindowManager.LayoutParams lp=dialog.getWindow().getAttributes();
//                            lp.alpha=0.5f;
//
//                            dialog.getWindow().setAttributes(lp);
                        }
                    });





//                    Toast.makeText(MainActivity.this,textViews[finalI].getText().toString(),Toast.LENGTH_SHORT).show();
//                    textViews[finalI].setText("苹果");

                }
            });

            frameLayout_dong.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    for(int j = 0;j< textViews.length;j++){
//                            if(text.equals(textViews[j])){//非当前  textView
//                                icons[j].setVisibility(View.VISIBLE);
//                            }
                        icons[j].setVisibility(View.VISIBLE);
                        freshday_cangs[j].setVisibility(View.INVISIBLE);
                    }

                    return true;
                }
            });

            mTagLayout_dong.addView(view);
        }

//        mTagLayout.addView(inputLabel);
    }


    //常用食材
    private void initLayoutUsual(final ArrayList<String> arr) {

        getmTagLayout_usual.removeAllViewsInLayout();
        /**
         * 创建 textView数组
         */
        final TextView[] textViews = new TextView[arr.size()];
        final TextView[] icons = new TextView[arr.size()];

        for (int i = 0; i < arr.size(); i++) {

            final int pos = i;

            final View view = (View) LayoutInflater.from(getActivity()).inflate(R.layout.text_view, getmTagLayout_usual, false);

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
                    if (addtag==1){
                        Toast.makeText(getActivity(),"只能选择一个",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    addname=list_usual.get(finalI);
                    if (frameLayout.isActivated()) {
                        frameLayout.setBackgroundColor(Color.YELLOW);
                        detal_layout_cang.setBackgroundColor(Color.parseColor("#7597b3"));
                        addtag = 1;
                    } else {
                        frameLayout.setBackgroundColor(0);
                        addtag = 0;
                    }




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


//            text.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//
//
//                    text.setActivated(!text.isActivated()); // true是激活的
//
//                    if (text.isActivated()) {
//                        boolean bResult = doAddText(list.get(pos), false, pos);
//                        text.setActivated(bResult);
//                        //遍历   数据    将图标设置为可见：
//                        for(int j = 0;j< textViews.length;j++){
////                            if(text.equals(textViews[j])){//非当前  textView
////                                icons[j].setVisibility(View.VISIBLE);
////                            }
//                            icons[j].setVisibility(View.VISIBLE);
//                        }
//                    }else{
//                        for(int j = 0;j< textViews.length;j++){
////                            icons[j].setVisibility(View.GONE);
//                        }
//                    }
//
//                    /**
//                     * 遍历  textView  满足   已经被选中     并且不是   当前对象的textView   则置为  不选
//                     */
//                    for(int j = 0;j< textViews.length;j++){
//                        if(!text.equals(textViews[j])){//非当前  textView
//                            textViews[j].setActivated(false); // true是激活的
////                            icons[j].setVisibility(View.GONE);
//                        }
//                    }
//                }
//            });

            getmTagLayout_usual.addView(view);
        }

//        getmTagLayout_usual.addView(inputLabel);
    }




    // 标签索引文本
    protected int idxTextTag(String text) {
        int mTagCnt = mAddTags.size(); // 添加标签的条数
        for (int i = 0; i < mTagCnt; i++) {
            TagItem item = mAddTags.get(i);
            if (text.equals(item.tagText)) {
                return i;
            }
        }
        return -1;
    }

    // 标签添加文本状态
    private boolean doAddText(final String str, boolean bCustom, int idx) {
        int tempIdx = idxTextTag(str);
        if (tempIdx >= 0) {
            TagItem item = mAddTags.get(tempIdx);
            item.tagCustomEdit = false;
            item.idx = tempIdx;
            return true;
        }
        int tagCnt = mAddTags.size(); // 添加标签的条数
        TagItem item = new TagItem();
        item.tagText = str;
        item.tagCustomEdit = bCustom;
        item.idx = idx;
        mAddTags.add(item);
        tagCnt++;
        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }


    private void ChoseImage(String name,ImageView imageView){
        ApplicationInfo appInfo = getActivity().getApplicationInfo();
        int id1 = getResources().getIdentifier(map.get(name), "drawable", appInfo.packageName);
        imageView.setImageResource(id1);


    }

    /**
     * 接收消息
     * @param msg
     */
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventComing(EventMessage<String> msg){
        if(msg.getType() == 1){
            list.add(msg.getData());
            initLayout(list);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}




