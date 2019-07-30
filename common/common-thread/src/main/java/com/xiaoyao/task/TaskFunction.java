package com.xiaoyao.task;

/**
 * @author jincong
 * @date 16/3/14
 */
public interface TaskFunction<T> {
    /**
     * 执行函数
     *
     * @return T
     */
    T apply();

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param object Object
     * @return boolean
     */
    @Override
    boolean equals(Object object);
}
