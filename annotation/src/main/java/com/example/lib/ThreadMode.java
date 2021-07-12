package com.example.lib;

public enum ThreadMode {
    // 与发布事件处在同线程
    POSTING,
    // 接收事件在主线程
    MAIN,
    // 接收事件在后台线程
    BACKGROUND
}