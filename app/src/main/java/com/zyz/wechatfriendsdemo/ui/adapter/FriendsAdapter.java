package com.zyz.wechatfriendsdemo.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zyz.wechatfriendsdemo.R;
import com.zyz.wechatfriendsdemo.ui.bean.FriendsItemBean;
import com.zyz.wechatfriendsdemo.ui.view.FriendsItemView;

import java.util.List;

/**
 * @类名称：l
 * @作者：郑亚卓
 * @时间：2017/4/11
 * @功能：l
 */

public class FriendsAdapter extends BaseAdapter {
    private Context mContext;
    private List<FriendsItemBean> list;
    private Handler handler;

    public FriendsAdapter(Context mContext, List<FriendsItemBean> list, Handler mHandler) {
        this.mContext = mContext;
        this.list = list;
        this.handler=mHandler;
    }

    @Override
    public int getCount() {
        return list.size()+1;
    }

    @Override
    public FriendsItemBean getItem(int i) {
        return i!=0? list.get(i-1):null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(position==0){
            view= LayoutInflater.from(mContext).inflate(R.layout.item_friends_one,null,true);
        }else{
            view=LayoutInflater.from(mContext).inflate(R.layout.item_friends,null,true);
            holder= (ViewHolder) view.getTag();
            if(holder==null){
                holder= new ViewHolder(view);
                view.setTag(holder);
            }

            FriendsItemBean bean = list.get(position-1);
            holder.itemView.setHandler(handler);
            holder.itemView.setData(bean,position-1);

            if(null!= bean.getPraiseBean()&&bean.getPraiseBean().size()>0){
                holder.itemView.setPariseData(bean.getPraiseBean(),position-1);
            }

            if(null!=bean.getCommentBean()&&bean.getCommentBean().size()>0){
                holder.itemView.setCommentData(bean.getCommentBean(),position-1);
            }

        }

        return view;
    }

    class ViewHolder{
       private FriendsItemView itemView;


        private ViewHolder(View v){
           itemView= (FriendsItemView) v.findViewById(R.id.item_friends);

        }
    }

    public List<FriendsItemBean> getData(){
        return list;
    }

    public void setData(List<FriendsItemBean> l){
        this.list=l;
        notifyDataSetChanged();
    }


}
