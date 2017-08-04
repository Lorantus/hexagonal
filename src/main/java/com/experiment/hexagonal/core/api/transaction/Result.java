package com.experiment.hexagonal.core.api.transaction;

import java.util.Collection;

import static java.util.Collections.singletonList;

public class Result<T> {
    public static final Result<?> SUCCESS = Result.of(null);
    
    private final T data;
    private final ResultType resultType;
    private final Collection<String> errors;

    private Result(T data, ResultType resultType, Collection<String> errors) {
        this.data = data;
        this.resultType = resultType;
        this.errors = errors;
    }

    private Result(T data) {
        this(data, ResultType.OK, null);
    }

    private Result(ResultType resultType, Collection<String> errors) {
        this(null, resultType, errors);
    }
    
    public static <T> Result<T> of(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> of(ResultType resultType, Collection<String> errors) {
        return new Result<>(resultType, errors);
    }

    public static <T> Result<T> of(ResultType resultType, String error) {
        return new Result<>(resultType, singletonList(error));
    }

    public T getData() {
        return data;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public Collection<String> getErrors() {
        return errors;
    }

    public boolean is(ResultType expect) {
        return this.resultType == expect;
    }
}
