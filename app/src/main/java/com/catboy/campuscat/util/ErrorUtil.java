package com.catboy.campuscat.util;

import android.content.Context;

import com.catboy.campuscat.R;
import com.catboy.campuscat.data.model.response.LoginResponse;
import com.catboy.campuscat.util.enums.LoginErrorCode;

/**
 * ErrorUtil
 * <p>
 *     工具类，提供获取登录错误信息的方法。
 * </p>
 */
public class ErrorUtil {

    /**
     * 获取登录错误信息
     *
     * @param context  上下文对象，用于获取资源
     * @param response 登录响应对象，包含错误代码和数据
     * @return 对应的错误信息字符串
     */
    public static String getErrorMessage(Context context, LoginResponse response) {
        // 获取程序上下文
        Context appContext = context.getApplicationContext();

        // 获取错误代码
        LoginErrorCode errorCode = null;
        try {
            errorCode = LoginErrorCode.valueOf(response.getData().getCode());
        } catch (Exception e) {
            errorCode = LoginErrorCode.UNKNOWN;
        }

        // 根据错误代码返回对应的错误信息
        switch (errorCode) {
            case NOUSER:
                return appContext.getString(R.string.login_error_no_user);
            case PASSERROR:
                String errorMaxTimes = response.getData().getData().split(",")[0];
                String errorNumber = response.getData().getData().split(",")[1];
                if (errorNumber.equals(errorMaxTimes)) {
                    return appContext.getString(R.string.login_error_password_incorrect_max);
                } else {
                    return appContext.getString(R.string.login_error_password_incorrect, errorNumber, errorMaxTimes);
                }
            case USERLOCK:
                String errorMessage = response.getData().getData();
                String unlockTime = errorMessage.substring(11);
                return appContext.getString(R.string.login_error_user_locked, unlockTime);
            case NOREGISTER:
                return appContext.getString(R.string.login_error_no_register);
            default:
                return appContext.getString(R.string.login_error_unknown);
        }
    }
}
