package com.springingdream.adviser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rating {
    private Long id;

    private Long uid;
    private Long pid;

    private Integer rating;

    private Date timestamp;
}
