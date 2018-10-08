package com.springingdream.adviser.service;

import com.springingdream.adviser.model.Product;
import com.springingdream.adviser.model.UserPreferences;
import com.springingdream.adviser.payload.PagedResponse;
import com.springingdream.adviser.payload.ProductResponse;
import com.springingdream.adviser.util.ModelMapper;
import com.springingdream.adviser.util.ProductsAPI;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdviserService {

    public PagedResponse<ProductResponse> getGeneralRecommendations(int userId, int page, int size) {
        List<UserPreferences> preferences = getPreferences();
        List<Long> recommendations = recommend(userId, preferences);

        List<Product> products = ProductsAPI.getProductsByIdIn(recommendations);

        if (products == null) {
            return new PagedResponse<>(Collections.emptyList(), 0, 0, 0, 0, true);
        }

        int totalElements = products.size();
        int totalPages = totalElements / size + 1;
        boolean last = false;

        List<ProductResponse> productResponses = products.stream()
                .map(ModelMapper::mapProductToProductResponse).collect(Collectors.toList());

        if ((page + 1) * size > totalElements) {
            productResponses = productResponses.subList(page * size, totalElements);
            last = true;
        } else {
            productResponses = productResponses.subList(page * size, (page + 1) * size);
        }

        return new PagedResponse<>(productResponses, page, size, totalElements, totalPages, last);

    }

    public PagedResponse<ProductResponse> getSimilar(Product product, Long userId, int page, int size) {
        //TODO collaboration filtering
        return null;
    }

    public PagedResponse<ProductResponse> getSimilar(Product product, int page, int size) {
        //TODO one of content based methods
        return null;
    }

    public PagedResponse<ProductResponse> getRelated(Product product, int page, int size) {
        //TODO one of content based methods
        return null;
    }

    //Collaboration filtering VERY SIMPLE
    //TODO better algorithm
    List<Long> recommend(Integer userId, List<UserPreferences> preferences) {

        UserPreferences userPreferences = preferences.get(userId);

        Map<Long, Double> rank = new HashMap<>();

        for (UserPreferences otherUserPref : preferences) {
            if (!otherUserPref.getOwnerId().equals(userId)) {
                double similarity = calcSimilarity(userPreferences, otherUserPref);

                if (similarity == 0.0) {
                    continue;
                }

                for (Long productId : otherUserPref.getPreferences().keySet()) {
                    if (!userPreferences.contains(productId)) {
                        if (!rank.containsKey(productId)) {
                            rank.put(productId, 0.0);
                        }
                        Double newValue = rank.get(productId) + similarity * otherUserPref.getProductRating(productId);
                        rank.put(productId, newValue);
                    }

                }
            }
        }

        List<Long> result = new ArrayList<>();

        rank.entrySet().stream()
                .sorted(Map.Entry.comparingByValue()).forEach((entry) -> result.add(entry.getKey()));

        Collections.reverse(result);
        return result;
    }

    double calcSimilarity(UserPreferences preferences, UserPreferences otherPreferences) {
        double result = 0.0;

        List<Long> commonProducts = new ArrayList<>();

        for (Long product : preferences.getPreferences().keySet()) {
            for (Long otherProduct : otherPreferences.getPreferences().keySet()) {
                if (product.equals(otherProduct)) {
                    commonProducts.add(product);
                    break;
                }
            }
        }

        for (Long product : commonProducts) {
            result += Math.abs(preferences.getProductRating(product) - otherPreferences.getProductRating(product));
        }

        if (!commonProducts.isEmpty()) {
            result /= commonProducts.size();
        }

        return 1 / result;
    }

    private List<UserPreferences> getPreferences() {
        //TODO
        return null;
    }

}
