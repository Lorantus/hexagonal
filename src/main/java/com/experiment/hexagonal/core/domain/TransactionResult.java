package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.api.transaction.ResultType;

public class TransactionResult {
    public static <T> Result<T> asSuccess() {
        return Result.of(null);
    }

    public static <T> Result<T> asSuccess(T data) {
        return Result.of(data);
    }

    public static <T> Result<T> asForbidden(String error) {
        return Result.of(ResultType.FORBIDDEN, error);
    }
    
    public static <T> Result<T> asBadRequest(String error) {
        return Result.of(ResultType.BAD_REQUEST, error);
    }    
}
