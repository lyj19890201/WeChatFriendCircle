package com.zyz.wechatfriendsdemo.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyz.wechatfriendsdemo.R;
import com.zyz.wechatfriendsdemo.common.Constant;
import com.zyz.wechatfriendsdemo.ui.activity.BaseActivity;
import com.zyz.wechatfriendsdemo.ui.bean.FriendsItemBean;
import com.zyz.wechatfriendsdemo.utils.SharedPreferencesUtil;

import java.util.List;




public class CommentList extends LinearLayout {
    private List<FriendsItemBean.CommentBean> mData;
    private int position;
    private Handler handler;
    private LayoutInflater layoutInflater;


    public void setmData(List<FriendsItemBean.CommentBean> mData, int p, Context c) {

        this.mData = mData;
        this.position = p;
        if (mData != null) {
            notifyDataSetChanged(c, p);
        }
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }


    public CommentList(Context context) {
        this(context, null);
    }

    public CommentList(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CommentList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void notifyDataSetChanged(Context c, int position) {
        removeAllViews();
        if (mData == null || mData.size() == 0) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < mData.size(); i++) {
            final int index = i;
            View view = getView(index, c, position);
            if (view == null) {
                throw new NullPointerException("listview item layout is null, please check getView()...");
            }

            addView(view, index, layoutParams);
        }
    }

    private View getView(int position, Context context, int p) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(context);
        }
        View convertView = layoutInflater.inflate(R.layout.item_friend_comment, null, false);
        TextView commentTv = (TextView) convertView.findViewById(R.id.item_friend_comment_name);

        FriendsItemBean.CommentBean bean = mData.get(position);
        String commentName = bean.getName();
        String resContent = bean.getContent();
        int a =commentName.length();
        int b = resContent.length();

        if ("1".equals(bean.getType())) {
            SpannableStringBuilder ssb = new SpannableStringBuilder();
            ssb.append(commentName + ": " + resContent);
            ssb.setSpan(new TextViewSpan(bean, Constant.COMMENT_NAME_CODE, p), 0, a, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.setSpan(new TextViewSpan(bean, Constant.REPLY_CODE, p), a, a + b + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            commentTv.setText(ssb);
            commentTv.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            getName(commentTv, bean, p,context);
        }

        return convertView;
    }

    private void getName(TextView commentTv, FriendsItemBean.CommentBean bean, int p,Context c) {
        SharedPreferencesUtil share=SharedPreferencesUtil.getInstance(c);
        String replyName=share.getString("replyName","");
        setReply(commentTv,bean.getName(),replyName,bean,p);
    }


    private void setReply(TextView commentTv, String commentName, String replyName,
                          FriendsItemBean.CommentBean b, int position) {
        String call = "回复";
        String resContent = ": " + b.getContent();

        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(commentName + call + replyName + resContent);

        ssb.setSpan(new TextViewSpan(b, Constant.COMMENT_NAME_CODE, position), 0, commentName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ssb.setSpan(new TextViewSpan(b, 4, position), commentName.length(), commentName.length() + call.length() + 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ssb.setSpan(new TextViewSpan(b, Constant.REPLY_NAME_CODE, position), commentName.length() + call.length(),
                commentName.length() + call.length() + replyName.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ssb.setSpan(new TextViewSpan(b, Constant.REPLY_CODE, position), commentName.length() + call.length() + replyName.length(),
                commentName.length() + call.length() + replyName.length() + resContent.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        commentTv.setText(ssb);
        commentTv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public class TextViewSpan extends ClickableSpan {
        private FriendsItemBean.CommentBean b;
        private int type;
        private int p;

        public TextViewSpan(FriendsItemBean.CommentBean bean, int i, int position) {
            this.b = bean;
            this.type = i;
            this.p = position;
        }

        @Override
        public void onClick(View widget) {
            if(type==Constant.REPLY_CODE){
                FriendsItemView.GetPositionListener listener= BaseActivity.getListener();
                listener.getPosition(p);
            }
            Log.e("MainActivity---",type+"--type");
            sendMessage(b,type,p);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            if (type == Constant.REPLY_CODE || type == 4) {
                ds.setColor(Color.GRAY);
            } else {
                //noinspection deprecation
                ds.setColor(Color.BLUE);
            }

        }
    }

    private void sendMessage(FriendsItemBean.CommentBean b, int type, int p) {
        Message msg = handler.obtainMessage();
        msg.obj=b;
        msg.what=type;
        handler.sendMessage(msg);
    }

}
