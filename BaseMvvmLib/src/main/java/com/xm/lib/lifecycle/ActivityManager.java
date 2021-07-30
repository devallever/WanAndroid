package com.xm.lib.lifecycle;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * @ClassName ActivityManager
 * @Description TODO
 * @Author Jerry
 * @Date 2020/5/4 15:21
 * @Version 1.0
 */
public class ActivityManager {

    private Stack<Activity> actsStack = new Stack<>();
    private WeakReference<Activity> weakReference;

    public static ActivityManager getInstance(){
        return Holder.manager;
    }

    public void clean() {
        if (null != actsStack) {
            actsStack.clear();
        }
    }

    private static class Holder{
        private static ActivityManager manager = new ActivityManager();
    }

    public void put(Activity act){
        weakReference = new WeakReference<>(act);
        int search = actsStack.search(act);
        if (search == -1) {
            actsStack.push(act);
        }
    }

    public int size(){
        return null == actsStack ? 0 : actsStack.size();
    }

    public void remove(Activity act){
        if (null != actsStack && 0 < actsStack.search(act)) {
            actsStack.remove(act);
        }
    }

    public void setCurrent(Activity act){
        weakReference = new WeakReference<>(act);
    }

    public Activity getCurrent(){
        if (null != weakReference) {
            return weakReference.get();
        }
        return null;
    }

    public Activity getTop(){
        if (null != actsStack) {
            Activity pop = actsStack.pop();
            return pop;
        }
        return null;
    }

    public void closeTop(){
        if (null != actsStack) {
            Activity pop = actsStack.pop();
            if (null != pop) {
                pop.finish();
            }
            remove(pop);
        }
    }

    public void closeAll(){
        if (null != actsStack) {
            while (!actsStack.empty()) {
                Activity pop = actsStack.pop();
                if (null != pop) {
                    pop.finish();
                }
            }
            clean();
        }
    }

    public void closeOther(String saveName){
        if (null != actsStack) {
            while (!actsStack.empty()) {
                Activity pop = actsStack.pop();
                if (null != pop
                        && !pop.getClass().getSimpleName().equals(saveName)) {
                    pop.finish();
                }
            }
        }
    }
}
