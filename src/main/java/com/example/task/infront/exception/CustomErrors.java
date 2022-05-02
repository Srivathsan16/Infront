package com.example.task.infront.exception;

import lombok.Getter;

@Getter
public enum CustomErrors {
    NO_DATA_FOUND( "No data found"),
    INVALID_FROM_DATE_FORMAT( "Invalid format for fromDate"),
    INVALID_TO_DATE_FORMAT("Invalid format for toDate"),
    FROM_DATE_QUERY_PARAM_REQUIRED( "FromDate querry param is required"),
    TO_DATE_QUERY_PARAM_IS_IN_FUTURE("ToDate query paramater is in future, it cannot be in future"),
    FROM_DATE_QUERY_PARAM_IS_IN_FUTURE("FromDate query paramater is in future, it cannot be in future"),
    TO_DATE_SHOULD_BE_GREATER_THAN_FROM_DATE("ToDate Should be greater than fromDate");

    private final String errorMessage;


    CustomErrors(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
