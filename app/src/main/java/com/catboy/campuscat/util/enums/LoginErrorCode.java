package com.catboy.campuscat.util.enums;

/**
 * LoginErrorCode
 * <p>
 *     枚举类，定义了登录过程中可能出现的错误代码。
 * </p>
 */
public enum LoginErrorCode {
    /** 学校统一登录返回错误码 */
    PASSERROR,
    NOUSER,
    USERLOCK,
    NOREGISTER,

    /** 自定义错误码 */
    UNKNOWN,
    NONE // 无错误
}
