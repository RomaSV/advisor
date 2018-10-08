package com.springingdream.adviser.controller;

import com.springingdream.adviser.payload.PagedResponse;
import com.springingdream.adviser.payload.ProductResponse;
import com.springingdream.adviser.service.AdviserService;
import com.springingdream.adviser.util.ProductsAPI;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/adviser")
public class AdviserController {

    @Autowired
    private AdviserService adviserService;

    @GetMapping("/recommendations")
    @ApiOperation(value = "Get products similar to one in params.")
    public PagedResponse<ProductResponse> getRecommendations(@RequestParam(value = "userId") int userId,
                                                     @RequestParam(value = "page") int page,
                                                     @RequestParam(value = "size") int size) {
        return adviserService.getGeneralRecommendations(userId, page, size);
    }

    @GetMapping("/similar")
    @ApiOperation(value = "Get products similar to one in params.")
    public PagedResponse<ProductResponse> getSimilar(@RequestParam(value = "prodId") long productId,
                                                     @RequestParam(value = "page") int page,
                                                     @RequestParam(value = "size") int size) {
        return adviserService.getSimilar(ProductsAPI.getProductById(productId), page, size);
    }

    @GetMapping("/related")
    @ApiOperation(value = "Get products related with one in params.")
    public PagedResponse<ProductResponse> getRelated(@RequestParam(value = "prodId") long productId,
                                                     @RequestParam(value = "page") int page,
                                                     @RequestParam(value = "size") int size) {
        return adviserService.getRelated(ProductsAPI.getProductById(productId), page, size);
    }
}
