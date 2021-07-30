package com.xm.lib.manager;

import androidx.lifecycle.LifecycleOwner;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Juzhengyuan
 *
 * @Author: Jerry.
 * @Date: 2020/11/25 10
 * @Desc:
 */
public class LifecycleManager {

    private static LifecycleManager manager;
    private WeakReference<LifecycleOwner> mOwnerWr = null;
    private Map<String, WeakReference<LifecycleOwner>> mOwnerWrs = new HashMap<>();

    public static LifecycleManager getManager() {
        if (null == manager) {
            manager = new LifecycleManager();
        }
        return manager;
    }

    public void putOwner(String key, LifecycleOwner mOwner) {
        WeakReference<LifecycleOwner> reference = new WeakReference<>(mOwner);
        mOwnerWr = reference;
        mOwnerWrs.put(key, reference);
    }

    public LifecycleOwner getOwner() {
        if (null != mOwnerWr) {
            return mOwnerWr.get();
        }
        return null;
    }

    public LifecycleOwner getOwner(String key){
        if (null != mOwnerWrs && mOwnerWrs.size() > 0 && mOwnerWrs.containsKey(key)) {
            return mOwnerWrs.get(key).get();
        }
        return null == mOwnerWr ? null : mOwnerWr.get();
    }

    public void remove(String key){
        if (null != mOwnerWrs && mOwnerWrs.size() > 0 && mOwnerWrs.containsKey(key)) {
            mOwnerWrs.remove(key);
        }
    }

    public synchronized void clear() {
        if (null != mOwnerWr) {
            mOwnerWr.clear();
            mOwnerWr = null;
        }
    }

}
