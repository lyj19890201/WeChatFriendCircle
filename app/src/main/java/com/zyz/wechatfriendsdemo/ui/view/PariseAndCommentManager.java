package com.zyz.wechatfriendsdemo.ui.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zyz.wechatfriendsdemo.R;
import com.zyz.wechatfriendsdemo.common.Constant;


public class PariseAndCommentManager extends LinearLayout implements View.OnClickListener {

    private ImageView parise;
    private ImageView comment;
    private Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }



    public PariseAndCommentManager(Context context) {
        this(context,null);
    }

    public PariseAndCommentManager(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PariseAndCommentManager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.parise_comment,this);
        parise = (ImageView) findViewById(R.id.item_friends_parise);
        comment = (ImageView) findViewById(R.id.item_friends_comment);
        parise.setOnClickListener(this);
        comment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.item_friends_parise:
                sendMessage(Constant.PARISE_CODE,0);
                break;
            case R.id.item_friends_comment:
                sendMessage(Constant.COMMENT_CODE,0);
                break;
        }
    }



    private void sendMessage(int code,int position) {
        Message msg = handler.obtainMessage();
        msg.what=code;
        msg.arg1=position;
        handler.sendMessage(msg);
    }


}
