package com.catboy.campuscat.taskqueue;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.catboy.campuscat.BuildConfig;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

/**
 * 任务管理器
 * <p>
 *     用于管理和调度任务的执行，支持任务优先级、中断和重试机制。
 * </p>
 */
public class TaskManager {
    private Disposable currentTaskDisposable;
    private Task<?> currentTask;
    private final MutableLiveData<LiveData<?>> currentTaskLiveData = new MutableLiveData<>();
    private final MediatorLiveData<TaskResult<?>> taskLogMediatorLiveData = new MediatorLiveData<>();

    // 单例模式
    // 不使用 Hilt 管理单例，为多平台打下基础
    private static TaskManager instance;

    /**
     * 任务优先级枚举(内部类) - 高、中、低
     * <p>
     *     用于定义任务的优先级，优先级高的任务将
     *     优先执行。
     * </p>
     */
    public enum Priority {
        HIGH,
        MEDIUM,
        LOW
    }

    /**
     * 获取任务管理器单例实例
     *
     * @return 任务管理器实例
     */
    public static synchronized TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    /**
     * 初始化任务管理器
     * <p>
     *     该方法在应用启动时调用，确保单例实例被创建。
     * </p>
     */
    public static synchronized void initialize() {
        if (instance == null) {
            instance = new TaskManager();
        }
    }

    /**
     * 任务队列
     * <p>
     *     根据任务优先级对任务进行排序
     * </p>
     */
    private final PriorityBlockingQueue<Task<?>> taskQueue =
            new PriorityBlockingQueue<>(11,
                    Comparator.comparingInt((Task<?> t) ->
                            t.getTaskPriority().ordinal()
                    ).reversed());

    /**
     * 私有构造函数
     */
    private TaskManager() {
        if (BuildConfig.DEBUG) {
            Timber.d("Build version is DEBUG! Start logging current task state.");
            logCurrentTaskState();
        }
    }

    /**
     * 执行任务
     *
     * @param task 任务
     */
    private synchronized void run(Task<?> task) {
        // 保存当前任务
        this.currentTask = task;
        currentTaskLiveData.postValue(task.getLivedata());

        // Debug 信息
        Timber.d("Start %s, waiting task: %d", task.getTaskId(), taskQueue.size());

        // 开始执行任务
        this.currentTaskDisposable = task.execute()
                .retryWhen(errors ->
                        errors.zipWith(Observable.range(1, task.getMaxRetryCount()),
                                        (error, retryCount) -> retryCount)
                                .flatMap(retryCount ->
                                        Observable.timer((long) Math.pow(2, retryCount),
                                                java.util.concurrent.TimeUnit.SECONDS)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {},
                        error -> maybeRunNext(),
                        this::maybeRunNext
                );
    }

    /**
     * 尝试运行下一个任务
     * <p>
     *     如果任务队列中有任务，则取出并执行；否则将当前任务置为空。
     * </p>
     */
    private synchronized void maybeRunNext() {
        Task<?> nextTask = taskQueue.poll();

        if (nextTask != null) {
            run(nextTask);
        } else {
            this.currentTask = null;
        }
    }

    /**
     * 将任务加入队列
     * <p>
     *     如果任务是高优先级任务，则中断当前任务并立即执行该任务；
     *     否则将任务加入队列，并尝试执行下一个任务。
     * </p>
     *
     * @param task 任务
     */
    public synchronized void enqueue(Task<?> task) {
        if (task.isHighPriority()) {
            interruptAndRun(task);
        } else {
            taskQueue.add(task);
            // 当前无任务时尝试执行下一个任务
            if (currentTask == null) {
                maybeRunNext();
            }
        }
    }

    /**
     * 中断当前任务并立即执行高优先级任务
     *
     * @param task 高优先级任务
     */
    private synchronized void interruptAndRun(Task<?> task) {
        // 取消当前任务
        if (currentTaskDisposable != null && !currentTaskDisposable.isDisposed()) {
            currentTaskDisposable.dispose();

            // 当前任务重入队
            if (currentTask != null) {
                taskQueue.add(currentTask);
            }
        }

        // 执行当前高优先级任务
        run(task);
    }

    /**
     * Debug 模式下记录当前任务状态
     * 并同步输出到日志
     */
    private void logCurrentTaskState() {
        // 当前任务状态监听
        currentTaskLiveData.observeForever(task -> {
            if (task != null) {
                Timber.d("Current Task ID: %s", currentTask.getTaskId());
                Timber.d("Task Priority: %s", currentTask.getTaskPriority());

                // 移除旧的监听
                taskLogMediatorLiveData.addSource(currentTask.getLivedata(),
                        taskLogMediatorLiveData::postValue);
            } else {
                Timber.d("No current task.");
            }
        });

        // 输出任务状态
        taskLogMediatorLiveData.observeForever(taskResult -> {
            if (taskResult != null) {
                Timber.d("Task Id: %s\n%s", currentTask.getTaskId(), taskResult.toString());
            }
        });
    }
}
