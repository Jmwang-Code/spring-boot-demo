package juc.cartitem;

import java.util.concurrent.ConcurrentHashMap;

public class ShoppingCartService  {
    // 使用 ConcurrentHashMap 存储每个用户的购物车信息
    private ConcurrentHashMap<User, ConcurrentHashMap<Product, CartItem>> shoppingCarts;

    // 构造函数，初始化 shoppingCarts
    public ShoppingCartService() {
        this.shoppingCarts = new ConcurrentHashMap<>();
    }

    // 添加商品到购物车
    public void addItemToCart(User user, Product product, int quantity) {
        // 使用商品对象作为锁对象，确保对商品的操作是线程安全的
        synchronized (product) {
            // 检查商品库存是否足够
            if (product.decreaseStock()) {
                // 将商品添加到用户购物车中，并更新商品数量
                shoppingCarts.computeIfAbsent(user, k -> new ConcurrentHashMap<>())
                        .compute(product, (k, v) -> v == null ? new CartItem(product, quantity) : v)
                        .increaseQuantity(quantity);
                System.out.println("User " + user.getUserId() + " added " + quantity + " " + product.getProductName() +
                        " to cart. Remaining stock: " + product.getStock());
            } else {
                System.out.println("Failed to add " + product.getProductName() + " to cart. Insufficient stock.");
            }
        }
    }

    // 从购物车中移除商品
    public void removeItemFromCart(User user, Product product) {
        // 使用商品对象作为锁对象，确保对商品的操作是线程安全的
        synchronized (product) {
            // 获取用户的购物车信息
            ConcurrentHashMap<Product, CartItem> userCart = shoppingCarts.get(user);
            if (userCart != null) {
                // 从购物车中移除指定商品
                userCart.remove(product);
            }
        }
    }

    // 获取用户的购物车信息
    public ConcurrentHashMap<Product, CartItem> getUserCart(User user) {
        return shoppingCarts.get(user);
    }

    // 主方法，用于模拟多个用户同时添加商品到购物车
    public static void main(String[] args) {
        // 创建购物车服务对象
        ShoppingCartService shoppingCartService = new ShoppingCartService();

        // 创建用户对象
        User user1 = new User(1, "Alice");
        User user2 = new User(2, "Bob");

        // 创建商品对象
        Product product1 = new Product(101, "Laptop", 999.99, 5);
        Product product2 = new Product(102, "Smartphone", 599.99, 10);

        // 创建并启动线程，模拟用户添加商品到购物车的操作
        Thread user1Thread = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                shoppingCartService.addItemToCart(user1, product1, 1);
            }
        });

        Thread user2Thread = new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                shoppingCartService.addItemToCart(user2, product2, 1);
            }
        });

        user1Thread.start();
        user2Thread.start();
    }
}
