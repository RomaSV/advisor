package com.springingdream.adviser.util;

import com.springingdream.adviser.model.Product;
import com.springingdream.adviser.payload.UserProduct;

public class ModelMapper {

    public static UserProduct mapProductToProductResponse(UserProduct product) {
        UserProduct response = new UserProduct();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        return response;
    }
}
