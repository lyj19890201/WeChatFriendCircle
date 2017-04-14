package com.zyz.wechatfriendsdemo.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import com.zyz.wechatfriendsdemo.R;
import com.zyz.wechatfriendsdemo.ui.adapter.FriendsAdapter;
import com.zyz.wechatfriendsdemo.ui.bean.FriendsItemBean;
import com.zyz.wechatfriendsdemo.ui.view.EntryFramManager;
import com.zyz.wechatfriendsdemo.ui.view.FriendsListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ListView friends_listView;
    private FriendsListView autoListView;
    private EntryFramManager entryManager;
    private List<FriendsItemBean> list;
    private Context mContext;
    private FriendsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        iniView();
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            FriendsItemBean bean = new FriendsItemBean();
            bean.setName("胡大姐");
            bean.setContent("刘海，你在哪");
            list.add(bean);
        }


    }

    private void iniView() {
        mContext=MainActivity.this;
        friends_listView = (ListView) findViewById(R.id.friends_listview);
        autoListView = (FriendsListView) findViewById(R.id.friends_auto_listview);
        entryManager = (EntryFramManager) findViewById(R.id.friends_entry);
        adapter = new FriendsAdapter(mContext,list,mHandler);
        friends_listView.setAdapter(adapter);
        entryManager.setmHandler(mHandler);
        friends_listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.e("MainActivity--","listView");
                return false;
            }
        });
    }

    @Override
    public void andParise() {
        super.andParise();
        List<FriendsItemBean> data=adapter.getData();
        FriendsItemBean.PraiseBean b= new FriendsItemBean.PraiseBean();
        b.setName("刘海");
        b.setParise(true);
        List<FriendsItemBean.PraiseBean> list =data.get(position).getPraiseBean();
        if(null==list){
            list= new ArrayList<>();
        }
        list.add(b);
        data.get(position).setPraiseBean(list);
        adapter.setData(data);
    }

    @Override
    public void andComment() {
        super.andComment();
        inspectEntryFramIsShow();
    }


    @Override
    public void sendContent() {
        super.sendContent();
        Log.e("MainActivity---",position+"--position");
        List<FriendsItemBean> bean =adapter.getData();
        List<FriendsItemBean.CommentBean> list = bean.get(position).getCommentBean();
        FriendsItemBean.CommentBean  b= new FriendsItemBean.CommentBean();
        if(null==list){
            list= new ArrayList<>();
        }
        if(commentBean==null){
            b.setType("1");
            b.setContent(content);
            b.setName("刘海");

        }else{
            b.setType("2");
            b.setName("胡大姐");
            b.setContent(content);
            share.putString("replyName",commentBean.getName());
        }

        list.add(0,b);
        bean.get(position).setCommentBean(list);
        adapter.setData(bean);
        hidenEntryFram();
        if(commentBean!=null){
            commentBean=null;
        }
    }

    @Override
    public void clickPariseName() {
        super.clickPariseName();
        showToast("点赞人");
    }

    @Override
    public void reply() {
        super.reply();
        inspectEntryFramIsShow();
    }

    @Override
    public void clickCommentName() {
        super.clickCommentName();
        showToast("评论人");
    }

    @Override
    public void clickReplyName() {
        super.clickReplyName();
        Log.e("MainActivity--","被点击");
        showToast("被评论人");
    }

    private void inspectEntryFramIsShow() {
        int a = entryManager.getVisibility();
        if(a== View.GONE){
            entryManager.setVisibility(View.VISIBLE);
            entryManager.getInPut().requestFocus();
            InputMethodManager manager = (InputMethodManager) entryManager.getInPut().getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        }
    }


    private void hidenEntryFram(){
       //entryManager.getInPut().setFocusable(false);
        int a = entryManager.getVisibility();
        if(a==View.VISIBLE){
            entryManager.getInPut().setText("");
            entryManager.setVisibility(View.GONE);
        }

    }

    @Override
    public void upData() {
        super.upData();
        adapter.setData(adapter.getData());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        hidenEntryFram();
        return super.dispatchTouchEvent(ev);
    }
}
