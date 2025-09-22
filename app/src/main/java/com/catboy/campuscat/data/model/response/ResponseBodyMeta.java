package com.catboy.campuscat.data.model.response;

/**
 * Meta
 * <p>
 *     用于封装响应的元信息，包括请求是否成功、状态码和消息。
 * </p>
 */
public class ResponseBodyMeta {
    private boolean success;
    private int statusCode;
    private String message;
}
