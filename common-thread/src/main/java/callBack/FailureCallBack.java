package callBack;

import com.google.common.util.concurrent.FutureCallback;

/**
 * Created by jincong on 16/6/6.
 */
public interface FailureCallBack<V> extends FutureCallback<V> {

    default void onSuccess(V result) {}
}
