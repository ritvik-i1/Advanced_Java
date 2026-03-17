import java.util.*;
import java.util.concurrent.*;

public class FlashSaleInventory {

    private Map<String, Integer> stock = new ConcurrentHashMap<>();
    private Map<String, Queue<Integer>> waiting = new ConcurrentHashMap<>();

    public FlashSaleInventory() {
        stock.put("IPHONE15", 3);
        waiting.put("IPHONE15", new ConcurrentLinkedQueue<>());
    }

    public int checkStock(String product) {
        return stock.getOrDefault(product, 0);
    }

    public synchronized String purchaseItem(String product, int userId) {
        int available = stock.getOrDefault(product, 0);

        if (available > 0) {
            stock.put(product, available - 1);
            return "Success, remaining: " + (available - 1);
        } else {
            waiting.get(product).add(userId);
            return "Added to waiting list: " + waiting.get(product).size();
        }
    }

    public static void main(String[] args) {
        FlashSaleInventory f = new FlashSaleInventory();

        System.out.println(f.purchaseItem("IPHONE15", 1));
        System.out.println(f.purchaseItem("IPHONE15", 2));
        System.out.println(f.purchaseItem("IPHONE15", 3));
        System.out.println(f.purchaseItem("IPHONE15", 4));
    }
}