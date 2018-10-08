package com.springingdream.adviser.util;

import com.springingdream.adviser.model.Product;
import com.springingdream.adviser.payload.ProductResponse;

public class ModelMapper {

    public static ProductResponse mapProductToProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        return response;
    }
}
