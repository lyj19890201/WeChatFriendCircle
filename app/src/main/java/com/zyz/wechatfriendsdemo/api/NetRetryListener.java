package com.zyz.wechatfriendsdemo.api;


public interface NetRetryListener {

    void retry();

    void error();
}
