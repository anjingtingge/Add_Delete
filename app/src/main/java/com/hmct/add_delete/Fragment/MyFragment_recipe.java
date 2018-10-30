package com.hmct.add_delete.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hmct.add_delete.FindBean;
import com.hmct.add_delete.Findadapter;
import com.hmct.add_delete.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyajie on 2018/10/30.
 */

public class MyFragment_recipe extends Fragment {
    private Findadapter findAdapter;
    private RecyclerView recyclerView_find;
    public List<FindBean> findBeen;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe,container,false);
        recyclerView_find = (RecyclerView)view.findViewById(R.id.recyclerView_list );
//        fragment_layout = view.findViewById(R.id.fragment_flowlayout );

        findBeen = new ArrayList<FindBean>();

        for(int i=0;i<9;i++)
        {
            findBeen.add(new FindBean("苹果派"));

//        recoBeen.add(new RecoBean(qwrecommed[i*2],qwrecommed[i*2+1]));
        }


        findAdapter= new Findadapter(getActivity(),findBeen);
        //普通
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        //网格
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);

        //设置布局管理器
        recyclerView_find.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recyclerView_find.setAdapter( findAdapter);

        //设置增加或删除条目的动画
        recyclerView_find.setItemAnimator(new DefaultItemAnimator());



        return view;
    }
}
