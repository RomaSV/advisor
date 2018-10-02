package com.springingdream.adviser.service;

import com.springingdream.adviser.model.Product;
import com.springingdream.adviser.payload.PagedResponse;
import com.springingdream.adviser.payload.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public class AdviserService {

    public PagedResponse<ProductResponse> getSimilar(Product product, int page, int size) {
        //TODO
        return null;
    }

    public PagedResponse<ProductResponse> getRelated(Product product, int page, int size) {
        //TODO
        return null;
    }

}
