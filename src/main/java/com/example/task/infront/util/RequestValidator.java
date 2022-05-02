package com.example.task.infront.util;

import com.example.task.infront.exception.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

import static com.example.task.infront.exception.CustomErrors.*;

@Component
public class RequestValidator {

    public static final String FROM_DATE = "fromDate";
    public static final String TO_DATE = "toDate";
    private static final Logger LOG = LogManager.getLogger(RequestValidator.class);

    public void validateQueryParams(Map<String, String> queryParams) throws CustomException {
        String fromDate = queryParams.get(FROM_DATE);
        String toDate = queryParams.get(TO_DATE);

        if (queryParams.containsKey(FROM_DATE) && !isValidDateFormat(fromDate)) {
            LOG.error("fromDate query param is not in correct format");
            throw new CustomException(INVALID_FROM_DATE_FORMAT);
        }

        if (queryParams.containsKey(TO_DATE) && !isValidDateFormat(toDate)) {
            LOG.error("toDate query param is not in correct format");
            throw new CustomException(INVALID_TO_DATE_FORMAT);
        }

        if (queryParams.containsKey(TO_DATE) && !queryParams.containsKey(FROM_DATE)) {
            LOG.error("fromDate query param is not present, fromDate has to be present");
            throw new CustomException(FROM_DATE_QUERY_PARAM_REQUIRED);
        }

        if (queryParams.containsKey(TO_DATE) && LocalDate.parse(toDate).isAfter(LocalDate.now())) {
            LOG.error("toDate query param cannot be in future");
            throw new CustomException(TO_DATE_QUERY_PARAM_IS_IN_FUTURE);
        }
        if (queryParams.containsKey(FROM_DATE) && LocalDate.parse(fromDate).isAfter(LocalDate.now())) {
            LOG.error("fromDate query param cannot be in future");
            throw new CustomException(FROM_DATE_QUERY_PARAM_IS_IN_FUTURE);
        }
        if (queryParams.containsKey(FROM_DATE) && queryParams.containsKey(TO_DATE)
                && LocalDate.parse(fromDate).isAfter(LocalDate.parse(toDate))) {
            LOG.error("Missing/Invalid required paramater, toDate must be greater than fromDate");
            throw new CustomException(TO_DATE_SHOULD_BE_GREATER_THAN_FROM_DATE);
        }
    }

    private boolean isValidDateFormat(String fromDate) {
        boolean valid;
        try {
            LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            valid = true;
        } catch (DateTimeParseException exception) {
            valid = false;
        }
        return valid;
    }
}
