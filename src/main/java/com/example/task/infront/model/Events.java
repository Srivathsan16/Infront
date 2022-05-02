package com.example.task.infront.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Events {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private double shortPercent;
    private String shares;
    private List<ActivePositions> activePositions;
}
