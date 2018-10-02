package com.springingdream.adviser.model;

import java.util.HashMap;
import java.util.Map;

public class Product {

    private Long id;
    private String name;
    private String vendor;
    private String description;

    private Map<String, String> params;

    private Category category;

    public Product(Long id, String name, String vendor, String description) {
        this.id = id;
        this.name = name;
        this.vendor = vendor;
        this.description = description;
        this.params = new HashMap<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void addParam(String param, String value) {
        this.params.put(param, value);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
