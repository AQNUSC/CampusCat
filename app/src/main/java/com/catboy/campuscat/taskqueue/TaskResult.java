package com.catboy.campuscat.taskqueue;

import androidx.annotation.NonNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TaskResult
 * <p>
 *     用于表示任务执行结果的类，包含任务状态、消息、数据和错误信息。
 * </p>
 *
 * @param <T> 任务执行结果的数据类型
 */
public class TaskResult<T> {

    /**
     * 任务状态枚举
     * <p>
     *     定义了任务的不同状态。
     *     LOADING: 任务正在加载中。
     *     RUNNING: 任务正在运行中。
     *     SUCCESS: 任务已成功完成。
     *     FAILURE: 任务执行失败。
     * </p>
     */
    public enum Status {
        LOADING,
        RUNNING,
        SUCCESS,
        FAILURE
    }

    @Getter
    private final Status status;
    @Getter
    private final String message;
    @Getter
    private final T data;
    @Getter
    private final Throwable error;

    /**
     * 私有构造函数，防止外部直接实例化
     *
     * @param status 任务状态
     * @param message 任务消息
     * @param data 任务数据
     * @param error 任务错误
     */
    private TaskResult(Status status, String message, T data, Throwable error) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.error = error;
    }

    /** 状态判断方法 **/
    public boolean isSuccess() {
        return this.status == Status.SUCCESS;
    }

    public boolean isLoading() {
        return this.status == Status.LOADING;
    }

    public boolean isRunning() {
        return this.status == Status.RUNNING;
    }

    public boolean isFailure() {
        return this.status == Status.FAILURE;
    }

    /**   工厂方法   **/
    public static <T> TaskResult<T> loading() {
        return new TaskResult<T>(Status.LOADING, null, null, null);
    }

    public static <T> TaskResult<T> running() {
        return new TaskResult<T>(Status.RUNNING, null, null, null);
    }

    public static <T> TaskResult<T> running(T data) {
        return new TaskResult<T>(Status.RUNNING, null, data, null);
    }

    public static <T> TaskResult<T> success(T data) {
        return new TaskResult<T>(Status.SUCCESS, null, data, null);
    }

    public static <T> TaskResult<T> failure(Throwable error) {
        return new TaskResult<T>(Status.FAILURE, null, null, error);
    }

    public static <T> TaskResult<T> failure(T data, Throwable error) {
        return new TaskResult<T>(Status.FAILURE, null, data, error);
    }

    public static <T> TaskResult<T> failure(String message, Throwable error) {
        return new TaskResult<T>(Status.FAILURE, message, null, error);
    }

    public static <T> TaskResult<T> failure(String message, T data) {
        return new TaskResult<T>(Status.FAILURE, message, data, null);
    }

    public static <T> TaskResult<T> failure(String message, T data, Throwable error) {
        return new TaskResult<T>(Status.FAILURE, message, data, error);
    }

    @NonNull
    @Override
    public String toString() {
        return "TaskResult: \n"
                + "Status: " + status + "\n"
                + "Message: " + message + "\n"
                + "Data: " + data + "\n"
                + "Error: " + (error != null ? error.getMessage() : "null");
    }
}
