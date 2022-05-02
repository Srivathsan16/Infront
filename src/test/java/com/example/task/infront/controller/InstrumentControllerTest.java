package com.example.task.infront.controller;

import com.example.task.infront.model.ActivePositions;
import com.example.task.infront.model.Events;
import com.example.task.infront.model.InstrumentModel;
import com.example.task.infront.services.InstrumentServiceImpl;
import com.example.task.infront.util.RequestValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InstrumentControllerTest {

    @InjectMocks
    private InstrumentController instrumentController;

    private MockMvc mockMvc;

    @Mock
    private InstrumentServiceImpl instrumentService;

    @Mock
    private RequestValidator requestValidator;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(instrumentController).build();
    }

    @Test
    public void test_getInstrumentDetails() throws Exception {
        String uri = "/instruments/BMG9156?fromDate=2022-02-03&toDate=2022-03-22";
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());

        verify(instrumentService, times(1)).getInstrumentDetailsWithFilter(anyString(), anyMap());
    }

    @Test
    public void test_verifyInstrumentDetails() throws Exception {
        String uri = "/instruments/BMG9156?fromDate=2022-02-03&toDate=2022-03-22";
        InstrumentModel instrumentModel = new InstrumentModel();
        instrumentModel.setIsin("BMG9156");
        instrumentModel.setIssuerName("Test");

        List<Events> eventsList = new ArrayList<>();
        Events events = new Events();
        events.setDate(LocalDate.of(2022, 02, 25));
        events.setShortPercent(1.11);
        eventsList.add(events);

        List<ActivePositions> activePositionsList = new ArrayList<>();
        ActivePositions activePositions = new ActivePositions();
        activePositions.setDate(LocalDate.of(2022, 03, 25));
        activePositions.setShortPercent(2.3);
        activePositions.setPositionHolder("Test234");
        activePositionsList.add(activePositions);

        events.setActivePositions(activePositionsList);

        instrumentModel.setEvents(eventsList);

        doNothing().when(requestValidator).validateQueryParams(anyMap());
        ResponseEntity responseEntity = new ResponseEntity(instrumentModel, HttpStatus.OK);
        when(instrumentService.getInstrumentDetailsWithFilter(anyString(), anyMap())).thenReturn(responseEntity);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains(instrumentModel.getIssuerName()));
    }
}

