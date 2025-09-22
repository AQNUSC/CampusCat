package com.catboy.campuscat.data.model.response;

import lombok.Data;

/**
 * 登录响应
 * <p>
 *     用于封装登录请求的响应数据，包括请求的元信息、数据以及登录成功时返回的TGT和ticket。
 * </p>
 */
@Data
public class LoginResponse {
    // 请求元信息和数据
    private ResponseBodyMeta meta;
    private ResponseBodyData data;

    // TGT和ticket
    // 登录成功时返回
    private String tgt;
    private String ticket;

    // 判断登录是否成功
    public boolean isSuccess() {
        return tgt != null && ticket != null &&
                !tgt.isEmpty() && !ticket.isEmpty();
    }
}
