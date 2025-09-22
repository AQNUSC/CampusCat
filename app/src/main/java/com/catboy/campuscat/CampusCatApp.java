package com.catboy.campuscat;

import android.app.Application;

import com.catboy.campuscat.taskqueue.TaskManager;

import timber.log.Timber;

/**
 * CampusCatApp类是应用程序的入口点，继承自Application类。
 * 采用单例模式初始化TaskManager，无需依赖注入框架
 */
public class CampusCatApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Debug 模式下开启Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        // 初始化TaskManager单例
        TaskManager.initialize();
    }
}
