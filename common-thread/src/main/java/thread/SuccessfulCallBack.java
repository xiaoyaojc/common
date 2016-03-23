package thread;

import com.google.common.util.concurrent.FutureCallback;

/**
 * Created by jincong on 16/3/23.
 */
public interface SuccessfulCallBack<V> extends FutureCallback<V>{

    default void onFailure(Throwable throwable) {}
}
