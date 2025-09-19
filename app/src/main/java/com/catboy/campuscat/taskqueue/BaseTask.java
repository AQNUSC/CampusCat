package com.catboy.campuscat.taskqueue;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import lombok.Getter;

/**
 * BaseTask
 * <p>
 *     任务队列中任务的基类，提供了任务的基本属性和LiveData支持。
 * </p>
 * @param <T> 任务结果的数据类型
 */
public abstract class BaseTask<T> implements Task<T> {

    @Getter
    private final String taskId;
    @Getter
    private final TaskPriority priority;
    @Getter
    private final boolean highPriority;
    @Getter
    private final int maxRetryCount;
    private final MutableLiveData<TaskResult<T>> _liveData = new MutableLiveData<>();
    @Getter
    private final LiveData<TaskResult<T>> liveData = _liveData;

    // 默认参数
    private static final TaskPriority DEFAULT_TASK_PRIORITY = TaskPriority.MEDIUM;
    private static final int DEFAULT_RETRY_COUNT = 3;
    private static final boolean DEFAULT_HIGH_PRIORITY = false;

    /** 构造函数 */
    protected BaseTask(String taskId) {
        this.taskId = taskId;
        this.priority = DEFAULT_TASK_PRIORITY;
        this.highPriority = DEFAULT_HIGH_PRIORITY;
        this.maxRetryCount = DEFAULT_RETRY_COUNT;
    }

    protected BaseTask(String taskId, TaskPriority priority) {
        this.taskId = taskId;
        this.priority = priority;
        this.highPriority = DEFAULT_HIGH_PRIORITY;
        this.maxRetryCount = DEFAULT_RETRY_COUNT;
    }

    protected BaseTask(String taskId, TaskPriority priority, boolean highPriority) {
        this.taskId = taskId;
        this.priority = highPriority ? TaskPriority.HIGH : DEFAULT_TASK_PRIORITY;
        this.highPriority = highPriority;
        this.maxRetryCount = DEFAULT_RETRY_COUNT;
    }

    protected BaseTask(String taskId, TaskPriority priority, int maxRetryCount) {
        this.taskId = taskId;
        this.priority = priority;
        this.highPriority = DEFAULT_HIGH_PRIORITY;
        this.maxRetryCount = maxRetryCount;
    }

    protected BaseTask(String taskId, TaskPriority priority, boolean highPriority, int maxRetryCount) {
        this.taskId = taskId;
        this.priority = priority;
        this.highPriority = highPriority;
        this.maxRetryCount = maxRetryCount;
    }

    @Override
    public LiveData<TaskResult<T>> getLivedata() {
        return liveData;
    }
}
