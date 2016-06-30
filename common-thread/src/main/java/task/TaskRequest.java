package task;


import com.google.common.util.concurrent.FutureCallback;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jincong on 16/3/14.
 */
public class TaskRequest implements Serializable{

    /**
     * 任务数
     */
    private int taskCount;

    /**
     * 任务列表
     */
    private List<TaskFunction> taskList;

    /**
     * 回调函数
     */
    private FutureCallback callback;

    /**
     * 失败策略，是否忽略error
     */
    private boolean ingoreError;

    public TaskRequest(List<TaskFunction> taskList){
        this.taskList = taskList;
        this.taskCount = taskList.size();
        this.ingoreError=true;
    }

    public TaskRequest(List<TaskFunction> taskList,FutureCallback callback){
        this.taskList = taskList;
        this.taskCount = taskList.size();
        this.callback=callback;
        this.ingoreError=true;
    }

    public TaskRequest(List<TaskFunction> taskList,Boolean ingoreError){
        this.taskList = taskList;
        this.taskCount = taskList.size();
        this.ingoreError=ingoreError;
    }

    public TaskRequest(List<TaskFunction> taskList,Boolean ingoreError,FutureCallback callback){
        this.taskList = taskList;
        this.taskCount = taskList.size();
        this.ingoreError=ingoreError;
        this.callback=callback;
    }

    public int getTaskCount() {
        return taskCount;
    }


    public List<TaskFunction> getTaskList() {
        return taskList;
    }

    public FutureCallback getCallback() {
        return callback;
    }

    public Boolean getIngoreError() {
        return ingoreError;
    }
}