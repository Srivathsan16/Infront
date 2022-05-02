package com.example.task.infront.services;

import com.example.task.infront.exception.CustomException;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface InstrumentService {
    ResponseEntity<?> getInstrumentDetailsWithFilter(String isin, Map<String, String> queryParams) throws CustomException;
}
