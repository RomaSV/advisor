package com.springingdream.adviser.model;

import lombok.Data;

import java.util.Map;

@Data
public class Rater {
    private Long uid;
    private Map<Long, Double> ratings;
}
