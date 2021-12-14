package com.experiment.hexagonal.core.api.transaction;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Collections.singletonList;

public class Result<T> {
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

    public Result<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (is(ResultType.OK)) {
            return this;
        } else {
            return predicate.test(data) ? this : Result.of(resultType, errors);
        }
    }

    public <U> Result<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        return is(ResultType.OK) ? Result.of(mapper.apply(data)) : Result.of(resultType, errors);
    }

    public <U> Result<U> flatMap(Function<? super T, ? extends Result<? extends U>> mapper) {
        Objects.requireNonNull(mapper);
        if(is(ResultType.OK)) {
            Result<? extends U> result = mapper.apply(data);
            if(result.is(ResultType.OK)) {
                return Result.of(result.get());
            } else {
                return Result.of(result.getResultType(), result.getErrors());
            }
        } else {
            return Result.of(resultType, errors);
        }
    }

    public T orElse(T value) {
        if(is(ResultType.OK)) {
            return data;
        } else {
            return value;
        }
    }

    public T orElseGet(Supplier<T> supplier) {
        if(is(ResultType.OK)) {
            return data;
        } else {
            return supplier.get();
        }
    }

    public T get() {
        if(is(ResultType.OK)) {
            return data;
        } else {
            throw new IllegalStateException("Result is not success");
        }
    }

    public Collection<String> getErrors() {
        if(is(ResultType.OK)) {
            throw new IllegalStateException("Result is not error");
        } else {
            return errors;
        }
    }

    public ResultType getResultType() {
        return resultType;
    }

    public boolean is(ResultType expect) {
        return this.resultType == expect;
    }
}
