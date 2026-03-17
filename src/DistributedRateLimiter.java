import java.util.*;
import java.util.concurrent.*;

class TokenBucket {
    int tokens;
    int maxTokens;
    long lastRefill;
    int refillRate; // tokens per second

    public TokenBucket(int maxTokens, int refillRate) {
        this.maxTokens = maxTokens;
        this.refillRate = refillRate;
        this.tokens = maxTokens;
        this.lastRefill = System.currentTimeMillis();
    }

    synchronized boolean allowRequest() {
        refill();
        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false;
    }

    private void refill() {
        long now = System.currentTimeMillis();
        long seconds = (now - lastRefill) / 1000;
        if (seconds > 0) {
            tokens = Math.min(maxTokens, tokens + (int)(seconds * refillRate));
            lastRefill = now;
        }
    }

    int remaining() { return tokens; }
}

public class DistributedRateLimiter {

    private Map<String, TokenBucket> clients = new ConcurrentHashMap<>();

    public boolean checkRateLimit(String clientId) {
        clients.putIfAbsent(clientId, new TokenBucket(1000, 1));
        return clients.get(clientId).allowRequest();
    }

    public static void main(String[] args) {
        DistributedRateLimiter r = new DistributedRateLimiter();

        System.out.println(r.checkRateLimit("abc123"));
        System.out.println(r.checkRateLimit("abc123"));
    }
}