import com.google.common.util.concurrent.FutureCallback;
import com.xiaoyao.callback.FailureCallBack;
import com.xiaoyao.callback.SuccessCallBack;
import com.xiaoyao.task.TaskFunction;
import com.xiaoyao.task.TaskRequest;
import com.xiaoyao.thread.ThreadPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jincong on 16/3/20.
 */
public class ThreadUtil {

    public static void main(String[] args) {
        String s1 = "wdwdwdwdwsss";
        String s2 = "sdwwdwdw";
        FutureCallback callback = (SuccessCallBack) o -> System.out.println("success");
        FutureCallback failureCallback = (FailureCallBack)throwable -> System.out.println("failure");

        long times = 1L;
        List<TaskFunction> taskFunctionList = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            taskFunctionList.add(s1::length);
            taskFunctionList.add(() -> false);
        }
        TaskRequest taskRequest = new TaskRequest(taskFunctionList,false,failureCallback);
        ThreadPool threadPool = new ThreadPool();
        threadPool.initialize();
        List result = threadPool.execute(taskRequest);
        long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            System.out.println(result.get(i * 2));
            System.out.println(result.get(i * 2 + 1));
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
