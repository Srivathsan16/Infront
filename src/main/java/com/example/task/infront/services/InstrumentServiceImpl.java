package com.example.task.infront.services;

import com.example.task.infront.exception.CustomErrors;
import com.example.task.infront.exception.CustomException;
import com.example.task.infront.model.Events;
import com.example.task.infront.model.InstrumentModel;
import com.example.task.infront.util.RequestValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InstrumentServiceImpl implements InstrumentService {

    private static final Logger LOG = LogManager.getLogger(InstrumentServiceImpl.class);

    private static final String FROM_DATE = "fromDate";
    private static final String TO_DATE = "toDate";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Value("${host.url}")
    private String hostUrl;

    @Autowired
    private RequestValidator validator;

    @Autowired
    private RestTemplate restTemplate;


    public ResponseEntity<?> getInstrumentDetailsWithFilter(String isin, Map<String, String> queryParams) throws CustomException {
        String fromDate = queryParams.get(FROM_DATE);
        String toDate = queryParams.get(TO_DATE);
        validator.validateQueryParams(queryParams);
        ResponseEntity<List<InstrumentModel>> responseEntity =
                restTemplate.exchange(hostUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        });
        LOG.info("Fetched the data from finanstilsynet API {}", hostUrl);
        if (responseEntity.getBody() == null) {
            return new ResponseEntity<>(new CustomException(CustomErrors.NO_DATA_FOUND), HttpStatus.NOT_FOUND);
        }
        List<InstrumentModel> res = responseEntity.getBody();
        LOG.info("Result is {}", res);
        return filterInstruments(isin, res, fromDate, toDate);
    }

    private ResponseEntity<InstrumentModel> filterInstruments(String isin, List<InstrumentModel> res, String fromDate, String toDate) {
        InstrumentModel instrumentResponse = new InstrumentModel();
        LocalDate startDate = LocalDate.parse(fromDate, formatter);
        LocalDate endDate = LocalDate.parse(toDate, formatter);
        LOG.info("Filtering based on date provided in request");
        for (InstrumentModel instrument : res) {
            if (instrument.getIsin().equals(isin)) {
                List<Events> events = instrument.getEvents().stream()
                        .filter(event -> event.getDate().equals(startDate)
                                || (event.getDate().isAfter(startDate) && event.getDate().isBefore(endDate))
                                || event.getDate().equals(endDate))
                        .collect(Collectors.toList());
                instrumentResponse.setEvents(events);
                instrumentResponse.setIsin(instrument.getIsin());
                instrumentResponse.setIssuerName(instrument.getIssuerName());
            }
        }
        if (instrumentResponse.getIsin() == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(instrumentResponse, HttpStatus.OK);
    }
}
