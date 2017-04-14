package com.zyz.wechatfriendsdemo.ui.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyz.wechatfriendsdemo.R;
import com.zyz.wechatfriendsdemo.common.Constant;


public class EntryFramManager extends LinearLayout implements View.OnClickListener {

    private EditText inPut;
    private TextView send;

    private Handler mHandler;

    public EntryFramManager(Context context) {
        this(context,null);
    }

    public EntryFramManager(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EntryFramManager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.bottom_entry_fram,this);
        inPut = (EditText)findViewById(R.id.friends_input);
        send = (TextView)findViewById(R.id.friends_send);
        send.setOnClickListener(this);
    }

    public void setmHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public EditText getInPut(){
        return  inPut;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.friends_send:
                String content=inPut.getText().toString().trim();
                sendMessage(Constant.SEND_CODE,content);
                break;
        }
    }

    private void sendMessage(int type,String content) {
        Message msg = mHandler.obtainMessage();
        msg.obj=content;
        msg.what=type;
        mHandler.sendMessage(msg);
    }
}
