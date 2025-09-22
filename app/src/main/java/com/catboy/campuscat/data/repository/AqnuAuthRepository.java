package com.catboy.campuscat.data.repository;

import com.catboy.campuscat.data.api.AqnuAuthApi;
import com.catboy.campuscat.data.model.Account;
import com.catboy.campuscat.data.model.request.LoginRequest;
import com.catboy.campuscat.data.model.response.LoginResponse;
import com.catboy.campuscat.util.constants.AqnuAuthEndpoints;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * AqnuAuthRepository
 * <p>
 *     用于处理用户认证相关的数据操作。
 * </p>
 */
public class AqnuAuthRepository {

    private final AqnuAuthApi authApi;

    /** 单例实例 */
    private static AqnuAuthRepository instance;

    /**
     * 获取单例实例
     *
     * @return AqnuAuthRepository 实例
     */
    public synchronized static AqnuAuthRepository getInstance() {
        if (instance == null) {
            instance = new AqnuAuthRepository();
        }
        return instance;
    }

    /**
     * 私有构造函数，防止外部实例化
     */
    private AqnuAuthRepository() {
        // 初始化 AqnuAuthApi
        this.authApi = new Retrofit.Builder()
                .baseUrl(AqnuAuthEndpoints.BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AqnuAuthApi.class);
    }

    /**
     * 验证用户账户信息，获取TGT和Ticket
     *
     * @param account 用户账户信息
     * @return 包含登录响应的 Single 对象
     */
    public Single<LoginResponse> verify(Account account) {
        // 构建请求参数
        LoginRequest request = LoginRequest.of(account);

        return authApi.verify(request.toMap());
    }
}
