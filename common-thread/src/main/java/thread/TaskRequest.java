package thread;


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

    public TaskRequest(List<TaskFunction> taskList){
        this.taskList = taskList;
        this.taskCount = taskList.size();
    }

    public int getTaskCount() {
        return taskCount;
    }


    public List<TaskFunction> getTaskList() {
        return taskList;
    }

}