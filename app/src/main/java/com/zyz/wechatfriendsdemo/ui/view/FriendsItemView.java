package com.zyz.wechatfriendsdemo.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyz.wechatfriendsdemo.R;
import com.zyz.wechatfriendsdemo.common.Constant;
import com.zyz.wechatfriendsdemo.ui.activity.BaseActivity;
import com.zyz.wechatfriendsdemo.ui.bean.FriendsItemBean;

import java.util.List;

/**
 * @类名称：l
 * @作者：郑亚卓
 * @时间：2017/4/12
 * @功能：l
 */

public class FriendsItemView extends RelativeLayout {

    private ImageView userIcon;
    private TextView userName;
    private TextView resourceContent;
    private RecyclerView resourceImages;
    private ImageView itemParise;
    private ImageView itemComment;
    private TextView pariseNames;
    private Context mContext;
    private CommentList commentList;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private Handler handler;

    public FriendsItemView(Context context) {
        this(context, null);
    }

    public FriendsItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FriendsItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.item_friends_two, this);
        userIcon = (ImageView) findViewById(R.id.item_friends_user_icon);
        userName = (TextView) findViewById(R.id.item_friends_user_name);
        resourceContent = (TextView) findViewById(R.id.item_friends_resource_content);
        resourceImages = (RecyclerView) findViewById(R.id.item_friends_resource_images);
        itemParise = (ImageView) findViewById(R.id.item_friends_parise);
        itemComment = (ImageView) findViewById(R.id.item_friends_comment);
        pariseNames = (TextView) findViewById(R.id.item_parise_name);
        commentList = (CommentList) findViewById(R.id.friend_item_lv);
    }

    public void setData(FriendsItemBean bean, int position) {
        userName.setText(bean.getName());
        resourceContent.setText(bean.getContent());
        userIcon.setOnClickListener(new MyListener(position, bean));
        userName.setOnClickListener(new MyListener(position, bean));
        itemParise.setOnClickListener(new MyListener(position, bean));
        itemComment.setOnClickListener(new MyListener(position, bean));
    }

    public void setCommentData(List<FriendsItemBean.CommentBean> commentBean, int i) {
        commentList.setHandler(handler);
        commentList.setmData(commentBean, i, mContext);
    }

    public void setPariseData(List<FriendsItemBean.PraiseBean> praiseBean, int i) {
        int start = 0;
        int size = praiseBean.size();
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        for (int j = 0; j < size; j++) {
            FriendsItemBean.PraiseBean b =praiseBean.get(j);
            String name=b.getName();
            if(b.isParise()){itemParise.setImageResource(R.mipmap.collectionclick_on_the);}
            if (j != size - 1) {
                ssb.append(name + "," + "");
            } else {
                ssb.append(name);
            }
            ssb.setSpan(new TextViewSpan(name,i,b), start, start + name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = ssb.length();
        }
        pariseNames.setText(ssb);
        pariseNames.setMovementMethod(LinkMovementMethod.getInstance());
    }


    class MyListener implements View.OnClickListener {
        private int position;
        private FriendsItemBean bean;

        public MyListener(int position, FriendsItemBean b) {
            this.position = position;
            this.bean = b;
        }

        @Override
        public void onClick(View v) {
            GetPositionListener listener= BaseActivity.getListener();
            switch (v.getId()) {
                case R.id.item_friends_parise:
                    itemParise.setImageResource(R.mipmap.collectionclick_on_the);
                    sendMessage(bean, position, Constant.PARISE_CODE);
                    listener.getPosition(position);
                    break;
                case R.id.item_friends_comment:
                    sendMessage(bean, position, Constant.COMMENT_CODE);
                    listener.getPosition(position);
                    break;

            }
        }
    }


    private void sendMessage(FriendsItemBean bean, int position, int type) {
        Message msg = handler.obtainMessage();
        msg.obj = bean;
        msg.what = type;
        msg.arg1 = position;
        handler.sendMessage(msg);
    }


    private void sendMessage(FriendsItemBean.PraiseBean bean, int position, int type) {
        Message msg = handler.obtainMessage();
        msg.obj = bean;
        msg.what = type;
        handler.sendMessage(msg);
    }


    class TextViewSpan extends ClickableSpan {
        private String userName;
        private FriendsItemBean.PraiseBean bean;
        private int p;
        public TextViewSpan(String userid,int position,FriendsItemBean.PraiseBean b) {
            this.userName = userid;
            this.p=position;
            this.bean=b;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Color.BLUE);
        }

        @Override
        public void onClick(View widget) {
            sendMessage(bean,p,Constant.PARISENAME_CODE);
        }
    }


   public  interface  GetPositionListener{
       void getPosition(int position);
       void upData();
   }


}
