package com.xm.lib.event;

import android.text.TextUtils;

import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.xm.lib.manager.LogPrint;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Android Studio.
 *
 * @author Jerry
 * Date: 2020/10/31 14:02
 * @description:
 */
public class LifeDataBus {

    private final int size = 16;
    private static LifeDataBus lifeDataBus;
    private Map<LifecycleOwner, Observer> dataEvents;
    private Map<String, MutableLiveData> events;

    public Map<String, LifecycleOwner> unRegisterKeys;
    public Map<String, Integer> unRegisterType;
    private boolean isUnregister = false;

    public synchronized static LifeDataBus getDataBus() {
        if (null == lifeDataBus) {
            synchronized (LifeDataBus.class) {
                if (null == lifeDataBus) {
                    lifeDataBus = new LifeDataBus();
                }
            }
        }
        return lifeDataBus;
    }

    /**
     * 是否接收未调用register方法前的最后一次数据变化
     *
     * @param isUnregister
     */
    public void unregisterBeforeInfo(boolean isUnregister) {
        this.isUnregister = isUnregister;
    }

    /**
     * 注册监听
     * 根据LifecycleOwner的生命周期期进行监听数据变化
     *
     * @param key
     * @param owner
     * @param observer
     * @param <T>
     */
    public <T> void register(String key, LifecycleOwner owner, Observer<T> observer) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        if (null == dataEvents) {
            dataEvents = new HashMap<>(size);
        }
        dataEvents.put(owner, observer);
        if (null == events || events.isEmpty() || !events.containsKey(key)) {
            saveUnregisterKey(key, owner, 0);
            return;
        }
        MutableLiveData liveData = events.get(key);
        liveData.observe(owner, observer);

        if (isUnregister) {
            unregisterBeforeInfo(liveData, observer);
        }
    }

    /**
     * 通知发送
     *
     * @param key
     * @param t
     * @param clz
     * @param <T>
     */
    public <T> void sendData(String key, T t, Class<T> clz) {
        if (TextUtils.isEmpty(key) || null == events || 0 == events.size() || !events.containsKey(key)) {
            return;
        }
        MutableLiveData<T> liveData = events.get(key);
        liveData.setValue(t);
    }

    /**
     * 子线程发送通知
     *
     * @param key
     * @param t
     * @param clz
     * @param <T>
     */
    public <T> void postData(String key, T t, Class<T> clz) {
        if (TextUtils.isEmpty(key) || null == events || 0 == events.size() || !events.containsKey(key)) {
            return;
        }
        MutableLiveData<T> liveData = events.get(key);
        liveData.postValue(t);
    }


    private void saveUnregisterKey(String key, LifecycleOwner owner, int type) {
        if (null == unRegisterKeys) {
            unRegisterKeys = new HashMap<>(size);
        }
        if (null == unRegisterType) {
            unRegisterType = new HashMap<>(size);
        }
        unRegisterType.put(key, type);
        unRegisterKeys.put(key, owner);
        LogPrint.e("save key ->" + key);
    }

    /**
     * 注册监听
     * 不根据LifecycleOwner的生命周期期监听，一直监听数据变化
     *
     * @param key
     * @param owner
     * @param observer
     * @param <T>
     */
    public <T> void registerForever(String key, LifecycleOwner owner, Observer<T> observer) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        if (null == dataEvents) {
            dataEvents = new HashMap<>(size);
        }
        dataEvents.put(owner, observer);

        if (null == events || events.isEmpty() || !events.containsKey(key)) {
            saveUnregisterKey(key, owner, 1);
            return;
        }

        MutableLiveData liveData = events.get(key);
        liveData.observeForever(observer);
        if (isUnregister) {
            unregisterBeforeInfo(liveData, observer);
        }
    }


    /**
     * 使用hook技术，把observerWrapper的mLastVersion版本设置成当前LiveData对象的mVersion值；
     * 实现是否监听没有调用register方法前MutableLiveData的最后一次数据变化回调，
     *
     * @param mutableLiveData
     * @param observer
     */
    private void unregisterBeforeInfo(MutableLiveData mutableLiveData, Observer observer) {
        try {
            Field mObserversField = LiveData.class.getDeclaredField("mObservers");
            Field mVersionField = LiveData.class.getDeclaredField("mVersion");
            mObserversField.setAccessible(true);
            Object mObservers = mObserversField.get(mutableLiveData);
            mVersionField.setAccessible(true);
            Object mVersion = mVersionField.get(mutableLiveData);
            Class<?> mObserversClass = mObservers.getClass();
            Method get = mObserversClass.getDeclaredMethod("get", Object.class);
            get.setAccessible(true);
            Object invoke = get.invoke(mObservers, observer);
            Object observerWrapper = null;
            if (null != invoke && invoke instanceof Map.Entry) {
                observerWrapper = ((Map.Entry) invoke).getValue();
            }
            if (null == observerWrapper) {
                LogPrint.e("observerWrapper -> null");
                return;
            }
            Class<?> superclass = observerWrapper.getClass().getSuperclass();
            Field mLastVersion = superclass.getDeclaredField("mLastVersion");
            mLastVersion.setAccessible(true);
            mLastVersion.set(observerWrapper, mVersion);
        } catch (Exception e) {
            LogPrint.e("error -> " + e.getLocalizedMessage());
            e.printStackTrace();
        }

    }

    /**
     * 设置需要推送的值（对象类型）
     *
     * @param key
     * @param liveData
     * @param clz
     * @param <T>
     */
    public <T> void set(String key, MutableLiveData<T> liveData, Class<T> clz) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        if (null == events) {
            events = new HashMap<>(size);
        }
        events.put(key, liveData);
        checkRegister(key);
        LogPrint.e("set key ->" + key);
    }

    private void checkRegister(String key) {
        if (null == unRegisterKeys || unRegisterKeys.isEmpty() || !unRegisterKeys.containsKey(key)) {
            return;
        }
        LifecycleOwner owner = unRegisterKeys.get(key);
        int type = unRegisterType.get(key);
        if (dataEvents.containsKey(owner)) {
            Observer observer = dataEvents.get(owner);
            if (1 == type) {
                registerForever(key, owner, observer);
            } else {
                register(key, owner, observer);
            }
            unRegisterKeys.remove(key);
            unRegisterType.remove(key);
        }
        LogPrint.e("checkRegister key ->" + key);
    }

    /**
     * 设置需要推送的值（数组类型）
     *
     * @param key
     * @param liveData
     * @param clz
     * @param <T>
     */
    public <T> void setArray(String key, MutableLiveData<List<T>> liveData, Class<T> clz) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        if (null == events) {
            events = new HashMap<>(size);
        }
        events.put(key, liveData);
        checkRegister(key);
    }

    /**
     * 取消注册监听
     *
     * @param key
     * @param owner
     */
    public void unregister(String key, LifecycleOwner owner) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        if (null != events && events.containsKey(key)) {
            MutableLiveData data = events.get(key);
            data.removeObservers(owner);
            if (null != dataEvents && dataEvents.containsKey(key)) {
                Observer observer = dataEvents.get(owner);
                data.removeObserver(observer);
            }
        }
    }

    /**
     * 移除data
     *
     * @param key
     */
    public void remove(String key) {
        if (null != events && events.containsKey(key)) {
            events.remove(key);
        }
        if (null != dataEvents && dataEvents.containsKey(key)) {
            dataEvents.remove(key);
        }
    }
}
