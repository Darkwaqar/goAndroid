package com.growonline.gomobishop.asynctask;

public interface AsyncTaskResultListener<T> {
    void response(AsyncTaskResult<T> response);
}
