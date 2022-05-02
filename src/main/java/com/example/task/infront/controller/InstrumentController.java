package com.example.task.infront.controller;

import com.example.task.infront.exception.CustomException;
import com.example.task.infront.services.InstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class InstrumentController {

    @Autowired
    private InstrumentService service;

    @GetMapping(value = "/instruments/{isin}")
    public ResponseEntity<?> getInstrumentDetails(@PathVariable("isin") String isin,
                                                  @RequestParam Map<String, String> queryParams)
                                                  throws CustomException {
        return service.getInstrumentDetailsWithFilter(isin, queryParams);
    }
}
