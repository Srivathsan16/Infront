package com.example.task.infront.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ActivePositions {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private double shortPercent;
    private String shares;
    private String positionHolder;

}
