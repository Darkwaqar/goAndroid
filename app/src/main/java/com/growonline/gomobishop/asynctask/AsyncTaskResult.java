package com.growonline.gomobishop.asynctask;


public class AsyncTaskResult<T> {

    private Exception mException;
    private T mResult;

    public AsyncTaskResult(Exception exception, T result) {
        this.mException = exception;
        this.mResult = result;
    }

    public Exception getException() {
        return mException;
    }

    public T getResult() {
        return mResult;
    }

    public boolean hasException() {
        return mException != null;
    }
}
