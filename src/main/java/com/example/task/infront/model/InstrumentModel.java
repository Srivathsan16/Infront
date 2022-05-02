package com.example.task.infront.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class InstrumentModel implements Serializable {
    private String isin;
    private String issuerName;
    private List<Events> events;

}
