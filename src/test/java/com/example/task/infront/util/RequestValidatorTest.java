package com.example.task.infront.util;

import com.example.task.infront.exception.CustomErrors;
import com.example.task.infront.exception.CustomException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RequestValidatorTest {

    private RequestValidator validator;

    @Before
    public void setUp() {
        validator = new RequestValidator();
    }

    @Test
    public void validateQueryParamShouldThrowErrorWhenFromDateIsNotPresentInProperFormat() {
        final String incorrect_date_format = "20222-43-33";

        Map<String, String> queryParams = Map.ofEntries(entry("fromDate", incorrect_date_format), entry("toDate", LocalDate.now().toString()));

        CustomException customException = assertThrows(CustomException.class, () -> {
            validator.validateQueryParams(queryParams);
        });

        assertEquals(CustomErrors.INVALID_FROM_DATE_FORMAT.getErrorMessage(), customException.getErrorMessage());
    }

    @Test
    public void validateQueryParamShouldThrowErrorWhenToDateIsNotPresentInProperFormat() {
        final String incorrect_date_format = "20222-43-33";

        Map<String, String> queryParams = Map.ofEntries(
                entry("fromDate", LocalDate.now().minusDays(3).toString()),
                entry("toDate", incorrect_date_format));

        CustomException customException = assertThrows(CustomException.class, () -> {
            validator.validateQueryParams(queryParams);
        });

        assertEquals(CustomErrors.INVALID_TO_DATE_FORMAT.getErrorMessage(), customException.getErrorMessage());
    }

    @Test
    public void validateQueryParamShouldThrowErrorWhenFromDateIsNotPresentButToDateIsPresent() {

        Map<String, String> queryParams = Map.ofEntries(entry("toDate", LocalDate.now().toString()));

        CustomException customException = assertThrows(CustomException.class, () -> {
            validator.validateQueryParams(queryParams);
        });

        assertEquals(CustomErrors.FROM_DATE_QUERY_PARAM_REQUIRED.getErrorMessage(), customException.getErrorMessage());
    }

    @Test
    public void validateQueryParamShouldNotThrowErrorWhenFromDateIsPresentButToDateIsNot() throws CustomException {

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("fromDate", LocalDate.now().toString());
        validator.validateQueryParams(queryParams);
    }

    @Test
    public void validateQueryParamShouldThrowErrorWhenToDateIsInFuture() {

        final String date_in_future = LocalDate.now().plusDays(10).toString();

        Map<String, String> queryParams = Map.ofEntries(
                entry("fromDate", LocalDate.now().toString()),
                entry("toDate", date_in_future));

        CustomException customException = assertThrows(CustomException.class, () -> {
            validator.validateQueryParams(queryParams);
        });

        assertEquals(CustomErrors.TO_DATE_QUERY_PARAM_IS_IN_FUTURE.getErrorMessage(), customException.getErrorMessage());
    }

    @Test
    public void validateQueryParamShouldThrowErrorWhenFromDateIsInFuture() {

        final String date_in_future = LocalDate.now().plusDays(10).toString();

        Map<String, String> queryParams = Map.ofEntries(entry("fromDate", date_in_future));

        CustomException customException = assertThrows(CustomException.class, () -> {
            validator.validateQueryParams(queryParams);
        });

        assertEquals(CustomErrors.FROM_DATE_QUERY_PARAM_IS_IN_FUTURE.getErrorMessage(), customException.getErrorMessage());
    }

    @Test
    public void validateQueryParamShouldThrowErrorWhenFromDateIsGreaterThanToDate() {

        final String FROM_DATE = LocalDate.now().toString();
        final String TO_DATE = LocalDate.parse(FROM_DATE).minusDays(10).toString();

        Map<String, String> queryParams = Map.ofEntries(
                entry("fromDate", FROM_DATE),
                entry("toDate", TO_DATE));

        CustomException customException = assertThrows(CustomException.class, () -> {
            validator.validateQueryParams(queryParams);
        });

        assertEquals(CustomErrors.TO_DATE_SHOULD_BE_GREATER_THAN_FROM_DATE.getErrorMessage(), customException.getErrorMessage());
    }

    @Test
    public void validateQueryParamShouldNotThrowErrorForSuccessCase() throws CustomException {

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("fromDate", LocalDate.now().minusDays(5).toString());
        queryParams.put("toDate", LocalDate.now().toString());
        validator.validateQueryParams(queryParams);
    }


}

