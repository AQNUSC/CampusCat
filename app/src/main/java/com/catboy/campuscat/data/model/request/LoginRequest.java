package com.catboy.campuscat.data.model.request;

import androidx.annotation.NonNull;

import com.catboy.campuscat.data.model.Account;

import java.util.Map;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

/**
 * LoginRequest
 * <p>
 *     用于封装登录请求所需的数据。
 * </p>
 */
@Data
public class LoginRequest {
    // 登录请求所需的字段
    private String username;
    private String password;
    private String service;

    // 以下字段在请求中是必须的，但实际值为空即可
    @Getter(AccessLevel.NONE)
    private final String loginType = "";
    @Getter(AccessLevel.NONE)
    private final String id = "";
    @Getter(AccessLevel.NONE)
    private final String code = "";

    /**
     * 私有构造函数，防止外部直接实例化
     *
     * @param username 用户名
     * @param password 密码
     * @param service 服务
     */
    private LoginRequest(String username, String password, String service) {
        this.username = username;
        this.password = password;
        this.service = service;
    }

    // 将 LoginRequest 对象转换为 Map 以便发送请求
    public Map<String, String> toMap() {
        Map<String, String> map = new java.util.HashMap<>();
        map.put("username", username);
        map.put("password", password);
        map.put("service", service);
        map.put("loginType", loginType);
        map.put("id", id);
        map.put("code", code);
        return map;
    }

    /** 静态工厂方法 **/
    public static LoginRequest of(@NonNull Account account) {
        return new LoginRequest(
                account.getUsername(),
                account.getPassword(),
                ""
        );
    }

    public static LoginRequest of(@NonNull Account account, String service) {
        return new LoginRequest(
                account.getUsername(),
                account.getPassword(),
                service
        );
    }
}
