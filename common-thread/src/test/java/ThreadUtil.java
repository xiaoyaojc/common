import callBack.FailureCallBack;
import com.google.common.util.concurrent.FutureCallback;
import callBack.SuccessCallBack;
import task.TaskFunction;
import task.TaskRequest;
import thread.ThreadPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jincong on 16/3/20.
 */
public class ThreadUtil {
    public static void main(String[] args) {
        testCallBack();
    }

    public static void testCallBack() {
        String s1 = "wdwdwdwdwsss";
        String s2 = "sdwwdwdw";
        FutureCallback callback = (SuccessCallBack) o -> System.out.println("success");
        FutureCallback failureCallback = (FailureCallBack)throwable -> System.out.println("failure");

        Long times = 1L;
        List<TaskFunction> taskFunctionList = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            taskFunctionList.add(() -> s1.length());
            taskFunctionList.add(() -> s2.equals("222"));
            taskFunctionList.add(() -> new Test().getValue("dwdww"));
        }
        TaskRequest taskRequest = new TaskRequest(taskFunctionList,false,failureCallback);
        ThreadPool threadPool = new ThreadPool();
        threadPool.initialize();
        List result = threadPool.execute(taskRequest);
        Long start = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            System.out.println(result.get(i * 3));
            System.out.println(result.get(i * 3 + 1));
            System.out.println(result.get(i * 3 + 2));
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public static void test(){
    }
}
