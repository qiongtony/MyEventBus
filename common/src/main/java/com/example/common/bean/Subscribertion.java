package com.example.common.bean;

public class Subscribertion {

    // 订阅的实例对象
    private Object mObject;
    private SubscriberMethod mSubscriberMethod;

    public Subscribertion(Object object, SubscriberMethod subscriberMethod) {
        mObject = object;
        mSubscriberMethod = subscriberMethod;
    }

    public Object getObject() {
        return mObject;
    }

    public void setObject(Object object) {
        mObject = object;
    }

    public SubscriberMethod getSubscriberMethod() {
        return mSubscriberMethod;
    }

    public void setSubscriberMethod(SubscriberMethod subscriberMethod) {
        mSubscriberMethod = subscriberMethod;
    }
}
