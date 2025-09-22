package com.catboy.campuscat.data.model.response;

import lombok.Data;

/**
 * Data
 * <p>
 *     用于封装响应数据的类，包含响应代码和具体数据内容。
 * </p>
 */
@Data
public class ResponseBodyData {
    private String code;
    private String data;
}
