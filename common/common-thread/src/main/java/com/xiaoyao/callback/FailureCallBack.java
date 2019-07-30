package com.xiaoyao.callback;

import com.google.common.util.concurrent.FutureCallback;

/**
 *
 * @author jincong
 * @date 16/6/6
 */
@FunctionalInterface
public interface FailureCallBack<V> extends FutureCallback<V> {

    /**
     * Invoked with the result of the {@code Future} computation when it is successful
     * @param result V
     */
    @Override
    default void onSuccess(V result) {}
}
