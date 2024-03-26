package juc.cartitem;

import java.util.concurrent.atomic.AtomicInteger;

class Product {
    private int productId;
    private String productName;
    private double price;
    private AtomicInteger stock;

    public Product(int productId, String productName, double price, int stock) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stock = new AtomicInteger(stock);
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock.get();
    }

    public boolean decreaseStock() {
        return stock.decrementAndGet() >= 0;
    }
}