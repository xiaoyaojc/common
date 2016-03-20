package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jincong on 16/3/20.
 */
public class ThreadUtil {
    public static void main(String[] args) {
        String s1="wdwdwdwdw";
        String s2="sdwwdwdw";
        List<TaskFunction> taskFunctionList=new ArrayList<>();
        taskFunctionList.add(()->s1.length());
        taskFunctionList.add(() -> s2.length());
        TaskRequest taskRequest=new TaskRequest(taskFunctionList);
        ThreadPool threadPool=new ThreadPool();
        threadPool.initialize();
        Map result=threadPool.executeQuery(taskRequest);
        System.out.println(result.get(taskFunctionList.get(0)));
        System.out.println(result.get(taskFunctionList.get(1)));

    }
}
