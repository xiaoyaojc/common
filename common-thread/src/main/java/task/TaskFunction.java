package task;
/**
 * Created by jincong on 16/3/14.
 */
public interface TaskFunction<T>  {
    T apply();

    @Override
    boolean equals(Object object);
}
