package com.xiaoyao.callback;

import com.google.common.util.concurrent.FutureCallback;

/**
 *
 * @author jincong
 * @date 16/3/23
 */
@FunctionalInterface
public interface SuccessCallBack<V> extends FutureCallback<V>{

    /**
     * Invoked when a {@code Future} computation fails or is canceled.
     * @param throwable  Throwable
     */
    @Override
    default void onFailure(Throwable throwable) {}
}
