package com.example.myeventbus;

import android.os.Handler;
import android.os.Looper;

import com.example.common.bean.SubscriberMethod;
import com.example.common.bean.Subscribertion;
import com.example.lib.Subscribe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {

    private volatile static EventBus instance;


    private Map<Class<?>, List<Subscribertion>> mTypeMap = new HashMap<>();
    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    private EventBus() {

    }

    public static EventBus getInstance() {
        if (instance == null) {
            synchronized (EventBus.class) {
                if (instance == null) {
                    instance = new EventBus();
                }
            }
        }
        return instance;
    }

    public void register(Object target) {
        Method[] methods = target.getClass().getMethods();
        for (Method method : methods) {
            Subscribe annotation = method.getAnnotation(Subscribe.class);
            if (annotation == null) {
                continue;
            }
            if (method.getGenericParameterTypes().length != 1) {
                throw new IllegalArgumentException("parameter must be 1!");
            }
            Class<?> parameterClass = method.getParameterTypes()[0];
            SubscriberMethod subscriberMethod = new SubscriberMethod(
                    parameterClass,
                    method,
                    annotation.mode(),
                    annotation.priority(),
                    annotation.sticky()
            );
            Subscribertion subscribertion = new Subscribertion(target, subscriberMethod);
            List<Subscribertion> subscribertions = mTypeMap.get(parameterClass);
            if (subscribertions == null) {
                subscribertions = new CopyOnWriteArrayList<>();
            }
            subscribertions.add(subscribertion);
            mTypeMap.put(parameterClass, subscribertions);
        }
    }

    public void post(Object event) {
        List<Subscribertion> subscribertions = mTypeMap.get(event.getClass());
        if (subscribertions == null || subscribertions.isEmpty()) {
            return;
        }
        for (Subscribertion subscribertion : subscribertions) {
            switch (subscribertion.getSubscriberMethod().getThreadMode()) {
                case MAIN: {
                    if (Looper.getMainLooper() == Looper.myLooper()) {
                        postSingleEvent(event, subscribertion);
                    } else {
                        mMainHandler.post(() -> postSingleEvent(event, subscribertion));
                    }
                    break;
                }

                case POSTING: {
                    postSingleEvent(event, subscribertion);
                    break;
                }
                case BACKGROUND: {

                    break;
                }
            }

        }
    }

    private void postSingleEvent(Object event, Subscribertion subscribertion) {
        try {
            subscribertion.getSubscriberMethod().getMethod().invoke(subscribertion.getObject(), event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void unregister(Object target) {
        Method[] methods = target.getClass().getMethods();
        for (Method method : methods) {
            Subscribe annotation = method.getAnnotation(Subscribe.class);
            if (annotation == null) {
                continue;
            }
            Class<?> parameterClass = method.getParameterTypes()[0];
            List<Subscribertion> subscribertions = mTypeMap.get(parameterClass);
            if (subscribertions == null) {
                continue;
            }
            Iterator<Subscribertion> iterator = subscribertions.iterator();
            while (iterator.hasNext()) {
                Subscribertion subscribertion = iterator.next();
                if (subscribertion.getSubscriberMethod().getClazz() == parameterClass) {
                    iterator.remove();
                }
            }
        }
    }
}
