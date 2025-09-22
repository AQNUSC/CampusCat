package com.catboy.campuscat.taskqueue;

import androidx.lifecycle.LiveData;

import io.reactivex.rxjava3.core.Observable;

/**
 * 任务接口
 * <p>
 *     定义了任务的基本属性和执行方法。
 * </p>
 *
 * @param <T> 任务结果类型
 */
public interface Task<T> {
    /**
     * 获取任务唯一标识
     *
     * @return 任务ID
     */
    String getTaskId();

    /**
     * 获取任务优先级
     *
     * @return 任务优先级
     */
    TaskManager.Priority getTaskPriority();

    /**
     * 任务是否为高优先级
     *
     * @return 如果是高优先级任务则返回 true，否则返回 false
     */
    boolean isHighPriority();

    /**
     * 获取任务最大重试次数
     *
     * @return 最大重试次数
     */
    int getMaxRetryCount();

    /**
     * 执行任务
     *
     * @return 任务执行结果的 Observable
     */
    Observable<TaskResult<T>> execute();

    /**
     * 获取任务的 LiveData 对象
     *
     * @return 任务结果的 LiveData
     */
    LiveData<TaskResult<T>> getLivedata();

    /**
     * 设置任务结果到 LiveData
     *
     * @param result 任务执行结果
     */
    void setLiveData(TaskResult<T> result);
}
