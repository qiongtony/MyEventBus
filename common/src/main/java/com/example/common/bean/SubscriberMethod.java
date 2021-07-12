package com.example.common.bean;

import androidx.annotation.NonNull;

import com.example.lib.ThreadMode;

import java.lang.reflect.Method;

// 订阅方法
public class SubscriberMethod {
    // 订阅事件的类Class
    private Class<?> mClazz;

    private ThreadMode mThreadMode;

    private Method mMethod;

    private int mPriority;

    private boolean mSticky;

    public SubscriberMethod(Class<?> aClass, Method method, ThreadMode threadMode, int priority, boolean sticky) {
        mClazz = aClass;
        mMethod = method;
        mThreadMode = threadMode;
        mPriority = priority;
        mSticky = sticky;


    }

    public Class<?> getClazz() {
        return mClazz;
    }

    public void setClazz(Class<?> clazz) {
        mClazz = clazz;
    }


    public Method getMethod() {
        return mMethod;
    }

    public ThreadMode getThreadMode() {
        return mThreadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        mThreadMode = threadMode;
    }

    public int getPriority() {
        return mPriority;
    }

    public void setPriority(int priority) {
        mPriority = priority;
    }

    public boolean isSticky() {
        return mSticky;
    }

    public void setSticky(boolean sticky) {
        mSticky = sticky;
    }
}
