package com.catboy.campuscat.util;

import android.content.Context;

import com.catboy.campuscat.R;
import com.catboy.campuscat.data.model.response.LoginResponse;
import com.catboy.campuscat.taskqueue.TaskResult;
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
     * @param taskResult 包含登录结果的任务结果对象
     * @return 对应的错误信息字符串
     */
    public static String getErrorMessage(Context context, TaskResult<LoginErrorCode> taskResult) {
        // 获取程序上下文
        Context appContext = context.getApplicationContext();

        // 获取错误代码
        LoginErrorCode errorCode = taskResult.getData();

        // 根据错误代码返回对应的错误信息
        switch (errorCode) {
            case NOUSER:
                return appContext.getString(R.string.login_error_no_user);
            case PASSERROR:
                String errorMaxTimes = taskResult.getMessage().split(",")[0];
                String errorNumber = taskResult.getMessage().split(",")[1];
                if (errorNumber.equals(errorMaxTimes)) {
                    return appContext.getString(R.string.login_error_password_incorrect_max);
                } else {
                    return appContext.getString(R.string.login_error_password_incorrect, errorNumber, errorMaxTimes);
                }
            case USERLOCK:
                String errorMessage = taskResult.getMessage();
                String unlockTime = errorMessage.substring(11);
                return appContext.getString(R.string.login_error_user_locked, unlockTime);
            case NOREGISTER:
                return appContext.getString(R.string.login_error_no_register);
            default:
                return String.format(appContext.getString(R.string.login_error_unknown), taskResult.getError());
        }
    }
}
