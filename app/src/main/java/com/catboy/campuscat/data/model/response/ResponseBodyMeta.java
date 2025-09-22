package com.catboy.campuscat.data.model.response;

import lombok.Data;

/**
 * Meta
 * <p>
 *     用于封装响应的元信息，包括请求是否成功、状态码和消息。
 * </p>
 */
@Data
public class ResponseBodyMeta {
    private boolean success;
    private int statusCode;
    private String message;
}
