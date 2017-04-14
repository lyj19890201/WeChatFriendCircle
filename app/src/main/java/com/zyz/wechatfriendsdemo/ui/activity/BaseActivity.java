package com.zyz.wechatfriendsdemo.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.zyz.wechatfriendsdemo.api.NetRetryListener;
import com.zyz.wechatfriendsdemo.common.Constant;
import com.zyz.wechatfriendsdemo.ui.bean.FriendsItemBean;
import com.zyz.wechatfriendsdemo.ui.view.FriendsItemView;
import com.zyz.wechatfriendsdemo.utils.SharedPreferencesUtil;


public class BaseActivity extends Activity implements NetRetryListener,View.OnClickListener,FriendsItemView.GetPositionListener {

    protected Handler mHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            checkBean(msg.what,msg.obj);
        }
    };


    protected FriendsItemBean bean;
    protected FriendsItemBean.CommentBean commentBean;
    protected FriendsItemBean.PraiseBean praiseBean;
    protected int position;
    protected String content;
    protected SharedPreferencesUtil share;
    public static FriendsItemView.GetPositionListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        share=SharedPreferencesUtil.getInstance(BaseActivity.this);
        listener=this;
    }

    private void checkBean(int what, Object obj) {
        if(what==Constant.PARISE_CODE||what==Constant.COMMENT_CODE){
            bean =  (FriendsItemBean)obj;
            switch (what)
            {
                case Constant.PARISE_CODE:
                    andParise();
                    break;
                case Constant.COMMENT_CODE:
                    andComment();
                    break;
            }
        }

        if(what==Constant.COMMENT_NAME_CODE||what== Constant.REPLY_NAME_CODE||what==Constant.REPLY_CODE){
            commentBean= (FriendsItemBean.CommentBean) obj;
            switch (what)
            {
                case Constant.COMMENT_NAME_CODE:
                    clickCommentName();
                    break;

                case Constant.REPLY_NAME_CODE:
                    clickReplyName();
                    break;

                case Constant.REPLY_CODE:
                    reply();
                    break;
            }
        }

        if(what==Constant.PARISENAME_CODE){
            praiseBean= (FriendsItemBean.PraiseBean) obj;
            clickPariseName();
        }

        if(what==Constant.SEND_CODE){
            content= (String) obj;
            sendContent();
        }

    }


    @Override
    public void retry() {

    }

    @Override
    public void error() {

    }


    @Override
    public void onClick(View view) {

    }

    public void andComment() {

    }

    public void andParise() {
    }

    public void clickCommentName() {

    }

    public void clickReplyName(){

    }

    public void sendContent() {

    }

    public void clickPariseName() {

    }


    public void reply(){

    }

    public void showToast(String content){
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getPosition(int position) {
        this.position=position;
    }

    @Override
    public void upData() {

    }

    public static FriendsItemView.GetPositionListener getListener(){
        return listener;
    }
}
