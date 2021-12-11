package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.api.transaction.ResultType;
import org.assertj.core.api.AbstractAssert;

public class ResultAssert<T> extends AbstractAssert<ResultAssert<T>, Result<T>> {

    private ResultAssert(Result<T> actual) {
        super(actual, ResultAssert.class);
    }

    public static <T> ResultAssert<T>
    assertThat(Result<T> actual) {
        return new ResultAssert<>(actual);
    }

    private void extractedResult(ResultType resultType) {
        isNotNull();
        if (!actual.getResultType().equals(resultType)) {
            failWithMessage("Expected result to be %s but was %s", resultType, actual.getResultType());
        }
    }

    public ResultAssert<T> isSuccess() {
        extractedResult(ResultType.OK);
        return this;
    }

    public ResultAssert<T> isForbidden() {
        extractedResult(ResultType.FORBIDDEN);
        return this;
    }

    public ResultAssert<T> isUnauthorized() {
        extractedResult(ResultType.UNAUTHORIZED);
        return this;
    }

    public ResultAssert<T> isBadRequest() {
        extractedResult(ResultType.BAD_REQUEST);
        return this;
    }

    public ResultAssert<T> isFailed() {
        extractedResult(ResultType.FAILED);
        return this;
    }

    public ResultAssert<T> hasData(T data) {
        isSuccess();
        if ((data == null && actual.getData() != null) || !actual.getData().equals(data)) {
            failWithMessage("Expected result to be Success with data %s but was Success with data %s", data, actual.getData());
        }
        return this;
    }
}