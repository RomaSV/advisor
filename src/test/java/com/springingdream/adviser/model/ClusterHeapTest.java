package com.springingdream.adviser.model;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ClusterHeapTest {

    @Test
    void addRater() {
        ClusterHeap ch = new ClusterHeap(Collections.emptyList());
        ch.update(1L, 1L, 4.0);
        ch.update(1L, 2L, 4.0);
        ch.update(1L, 4L, 5.0);

        ch.update(2L, 2L, 2.0);
        ch.update(2L, 3L, 5.0);
        ch.update(2L, 4L, 2.0);

        System.out.println(ch.getClusters());
    }

    @Test
    void getUserCluster() {
    }

    @Test
    void update() {
    }
}