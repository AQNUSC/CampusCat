package com.catboy.campuscat.taskqueue.taskImpl;

import com.catboy.campuscat.data.model.Account;
import com.catboy.campuscat.data.repository.AqnuAuthRepository;
import com.catboy.campuscat.taskqueue.BaseTask;
import com.catboy.campuscat.taskqueue.TaskManager;
import com.catboy.campuscat.taskqueue.TaskResult;
import com.catboy.campuscat.util.enums.LoginErrorCode;

import io.reactivex.rxjava3.core.Observable;
import lombok.Setter;
import timber.log.Timber;

/**
 * 用户验证任务
 * <p>
 *     该任务用于验证用户的账户信息，
 * </p>
 */
public class UserVerifyTask extends BaseTask<LoginErrorCode> {
    private final static String TASK_ID = "UserVerifyTask";
    private final static boolean HIGH_PRIORITY = true;
    private final AqnuAuthRepository authRepository;

    // 账户信息
    // 执行任务前必须设置
    @Setter
    private Account account;

    /**
     * 构造函数
     * <p>
     *     初始化任务ID和优先级，并获取认证仓库实例。
     * </p>
     */
    protected UserVerifyTask() {
        super(TASK_ID, HIGH_PRIORITY);

        // 获取认证仓库实例
        this.authRepository = AqnuAuthRepository.getInstance();
    }

    @Override
    public Observable<TaskResult<LoginErrorCode>> execute() {
        Timber.d("UserVerifyTask execute() called");

        return Observable.defer(() -> {
            Timber.d("Starting user verification for account: %s", account.getUsername());

            return authRepository.verify(account)
                    .map(loginResponse -> {
                        Timber.d("Received response from authRepository");

                        if (loginResponse.isSuccess()) {
                            Timber.d("Verify User Success: Response\n%s", loginResponse.toString());
                            TaskResult<LoginErrorCode> success = TaskResult.success(LoginErrorCode.NONE);
                            setLiveData(success);
                            return success;
                        } else {
                            LoginErrorCode errorCode;
                            try {
                                errorCode = LoginErrorCode.valueOf(loginResponse.getData().getCode());
                            } catch (Exception e) {
                                errorCode = LoginErrorCode.UNKNOWN;
                            }

                            Timber.d("Verify User Failure: Response\n%s", loginResponse.toString());
                            TaskResult<LoginErrorCode> failure = TaskResult.failure(
                                    loginResponse.getData().getData(),
                                    errorCode
                            );
                            setLiveData(failure);
                            return failure;
                        }
                    })
                    .onErrorReturn(throwable -> {
                        Timber.e(throwable, "Verify User Error");
                        TaskResult<LoginErrorCode> error = TaskResult.failure(throwable);
                        setLiveData(error);
                        return error;
                    })
                    .toObservable();
        });
    }


    public static void create(Account account) {
        if (account == null) {
            Timber.e("Account is null, cannot create UserVerifyTask");
            return;
        }

        Timber.d("Creating UserVerifyTask for user: %s", account.getUsername());

        UserVerifyTask task = new UserVerifyTask();
        task.setAccount(account);

        Timber.d("Enqueueing UserVerifyTask");
        TaskManager.getInstance().enqueue(task);
    }
}
