package com.catboy.campuscat.data.api;

import com.catboy.campuscat.util.constants.AqnuAuthEndpoints;
import com.catboy.campuscat.data.model.response.LoginResponse;

import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * AqnuAuthApi
 * <p>
 *     用于处理用户认证相关的API接口。
 * </p>
 */
public interface AqnuAuthApi {
    /**
     * 获取用户的TGT和Ticket
     *
     * @param params 请求参数
     * @return 登录响应
     */
    @FormUrlEncoded
    @POST(AqnuAuthEndpoints.GET_TICKETS_ENDPOINT)
    Single<LoginResponse> verify(@FieldMap Map<String, String> params);
}
