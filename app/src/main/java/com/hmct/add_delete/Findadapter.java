package com.hmct.add_delete;

/**
 * Created by wangyajie on 2018/9/13.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



import java.util.List;



/**
 * Created by wangyajie on 2018/9/13.
 */

public class Findadapter extends RecyclerView.Adapter<Findadapter.MyViewHolder> {

    private List<FindBean> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private List<Integer> heightList;//装产出的随机数
    //数据


    public Findadapter(Context context, List<FindBean> datas){
        this. mContext=context;
        this. mDatas=datas;

//        //记录为每个控件产生的随机高度,避免滑回到顶部出现空白
//        heightList = new ArrayList<>();
//        for (int i = 0; i < datas.size(); i++) {
//            int height = new Random().nextInt(200) + 100;//[100,300)的随机数
//            heightList.add(height);
//        }

        inflater=LayoutInflater.from(mContext);
    }

    //监听
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    @Override
    public int getItemCount() {
        return mDatas.size();

    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

//        holder.tv.setText( mDatas.get(position));

        FindBean it = mDatas.get(position);

        //设置imageView的图片
//        Picasso.with(mContext)
//                .load(it.getUrl())
//                .resize(300,200)
//                .memoryPolicy(NO_CACHE, NO_STORE)
//                .into(holder.iv_item_img);
//        Log.e("推荐",it.getUrl());
//            mholder.iv_item_img.setImageBitmap(imgResizer.itdecodeSampledBitmapFromResource(getResources(),it.getImg()));
        //设置textView的文字
//        holder.tv_item_desc.setText(it.getDishname());
//        holder.description.setText(it.getDescription());
//        holder.hard_level.setText(it.getHard_level());
//        holder.cooking_time.setText(it.getCooking_time());
//        holder.taste.setText(it.getTaste());
        holder.tv_item_desc.setText(it.getUrl());
        holder.iv_item_img.setBackgroundResource(R.mipmap.caipu);


        //由于需要实现瀑布流的效果,所以就需要动态的改变控件的高度了
//        ViewGroup.LayoutParams params = holder.linear.getLayoutParams();
//        params.height=heightList.get(position);
//        holder.linear.setLayoutParams(params);

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.recipe_recyclerview,parent, false);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    class MyViewHolder extends ViewHolder{

        //        TextView tv;
        ImageView iv_item_img;
        TextView tv_item_desc,description,hard_level,cooking_time,taste;


        LinearLayout linear;

        public MyViewHolder(View view) {
            super(view);
//            tv=(TextView) view.findViewById(R.id.id_num);
            iv_item_img = (ImageView) itemView.findViewById(R.id.iv_item_img);
            tv_item_desc = (TextView)itemView.findViewById(R.id.tv_item_desc);
//            description = (TextView)itemView.findViewById(R.id.text_description);
//            hard_level = (TextView)itemView.findViewById(R.id.text_level);
//            cooking_time = (TextView)itemView.findViewById(R.id.text_time);
//
//            taste = (TextView)itemView.findViewById(R.id.text_taste);
//            linear=itemView.findViewById(R.id.linear);
        }

    }

    public void addData( int position) {
//        mDatas.add(position, new ItemBean(R.drawable.step0, "南小鸟1"));
        notifyItemInserted(position);
        notifyItemRangeChanged(position, mDatas.size());
    }

    public void removeData( int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDatas.size());
    }
}
