package com.springingdream.adviser.controller;

import com.springingdream.adviser.model.Rating;
import com.springingdream.adviser.service.AdviserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adviser")
public class AdviserController {
    private final AdviserService adviserService;

    @Autowired
    public AdviserController(AdviserService adviserService) {
        this.adviserService = adviserService;
    }

    @GetMapping("/product/{pid}")
    @ApiOperation(value = "Get products similar to one in params.")
    public List<Long> getProductRecommendations(@PathVariable Long pid) {
        return adviserService.getProductRecommendations(pid);
    }

    @GetMapping("/personal/{uid}")
    @ApiOperation(value = "Get personal recommendations.")
    public List<Long> getUserRecommendations(@PathVariable Long uid) {
        return adviserService.getUserRecommendations(uid);
    }

    @PostMapping(path = "/update")
    public void update(@RequestBody Rating rating) {
        adviserService.update(rating);
    }

    @GetMapping("/similar")
    @ApiOperation(value = "Get products similar to one in params. NOT IMPLEMENTED YET")
    public List<Long> getSimilar(@RequestParam(value = "prodId") long productId) {
        throw new NotImplementedException();
    }

    @GetMapping("/related")
    @ApiOperation(value = "Get products related with one in params. NOT IMPLEMENTED YET")
    public List<Long> getRelated(@RequestParam(value = "prodId") long productId) {
        throw new NotImplementedException();
    }
}
