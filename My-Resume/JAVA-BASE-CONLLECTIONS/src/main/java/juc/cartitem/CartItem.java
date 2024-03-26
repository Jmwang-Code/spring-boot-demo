package juc.cartitem;

import java.util.concurrent.atomic.AtomicInteger;

class CartItem {
    private Product product;
    private AtomicInteger quantity;  // 使用原子类型

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = new AtomicInteger(quantity);
    }

    // 获取商品数量
    public int getQuantity() {
        return quantity.get();
    }

    // 增加商品数量
    public void increaseQuantity(int amount) {
        quantity.addAndGet(amount);
    }

    // 减少商品数量
    public boolean decreaseQuantity(int amount) {
        int current = quantity.get();
        return quantity.compareAndSet(current, current - amount);
    }
}