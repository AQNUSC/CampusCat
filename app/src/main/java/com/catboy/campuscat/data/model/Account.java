package com.catboy.campuscat.data.model;

import com.catboy.campuscat.util.AuthUtil;

import lombok.Data;

/**
 * Account
 * <p>
 *     用于封装用户账户信息的类，包括用户名和密码。
 * </p>
 */
@Data
public class Account {
    private String username;
    private String password;

    /**
     * 私有构造函数，使用加密后的密码初始化账户信息
     *
     * @param username 用户名
     * @param password 密码（明文）
     */
    private Account(String username, String password) {
        this.username = username;
        this.password = AuthUtil.encryptPassword(password);
    }

    /**
     * 静态工厂方法，创建一个新的账户实例
     *
     * @param username 用户名
     * @param password 密码（明文）
     * @return 新的账户实例
     */
    public static Account of(String username, String password) {
        return new Account(username, password);
    }
}
