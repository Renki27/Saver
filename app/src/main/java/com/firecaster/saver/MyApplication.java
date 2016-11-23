package com.firecaster.saver;


import android.app.Application;
import android.util.Log;

import java.io.File;

public class MyApplication extends Application {

    private static MyApplication instance;

    public int inter, rentin, electicity, water ;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
                inter = 1;
                rentin = 1;
                electicity = 1;
                water = 1;
    }

    public static MyApplication getInstance() {
        return instance;
    }


    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "File /data/data/APP_PACKAGE/" + s + " DELETED");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

    public void setElecticity(int electicity) {
        this.electicity = electicity;
    }

    public void setRentin(int rentin) {
        this.rentin = rentin;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public void setInter(int inter) {
        this.inter = inter;
    }

    public int getElecticity() {
        return electicity;
    }

    public int getRentin() {
        return rentin;
    }

    public int getInter() {
        return inter;
    }

    public int getWater() {
        return water;
    }
}
