package com.catboy.campuscat;

import android.app.Application;

import com.catboy.campuscat.taskqueue.TaskManager;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;
import timber.log.Timber;

/**
 * CampusCatApp类是应用程序的入口点，继承自Application类。
 * 使用@HiltAndroidApp注解启用Hilt依赖注入框架。
 */
@HiltAndroidApp
public class CampusCatApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Debug 模式下开启Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
