package com.catboy.campuscat;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

/**
 * CampusCatApp类是应用程序的入口点，继承自Application类。
 * 使用@HiltAndroidApp注解启用Hilt依赖注入框架。
 */
@HiltAndroidApp
public class CampusCatApp extends Application {
}
