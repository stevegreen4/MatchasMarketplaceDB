package com.matchasmarketplace.model;

import java.math.BigDecimal;

public class Item {

    private int itemId;
    private String name;
    private String description;
    private String imgUrl;
    private BigDecimal price;

    public Item() {
    }

    public Item(int itemId, String name, String description, String size, BigDecimal price) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.imgUrl = size;
        this.price = price;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    //@todo
    @Override
    public String toString() {
        return "";
    }
}
