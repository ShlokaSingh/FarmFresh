package com.example.farmfresh.user.model;

public class CartModel {
    private String productName;

    private String imageUrl;

    private String quantity;

    private String discount;

    private String price;

//    public CartModel(String name, String price, String quantity, String discount, String imageUrl) {
//        this.productName = name;
//        this.price = price;
//        this.quantity = quantity;
//        this.discount = discount;
//        this.imageUrl = imageUrl;
//    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
