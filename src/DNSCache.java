import java.util.*;

class DNSEntry {
    String ip;
    long expiry;

    DNSEntry(String ip, int ttl) {
        this.ip = ip;
        this.expiry = System.currentTimeMillis() + ttl * 1000;
    }

    boolean expired() {
        return System.currentTimeMillis() > expiry;
    }
}

public class DNSCache {

    private Map<String, DNSEntry> cache = new HashMap<>();
    private int hit = 0, miss = 0;

    public String resolve(String domain) {
        DNSEntry e = cache.get(domain);

        if (e != null && !e.expired()) {
            hit++;
            return "HIT → " + e.ip;
        }

        String ip = "192.168." + new Random().nextInt(255);
        cache.put(domain, new DNSEntry(ip, 5));

        miss++;
        return "MISS → " + ip;
    }

    public void stats() {
        System.out.println("Hit: " + hit + ", Miss: " + miss);
    }

    public static void main(String[] args) {
        DNSCache d = new DNSCache();

        System.out.println(d.resolve("google.com"));
        System.out.println(d.resolve("google.com"));

        d.stats();
    }
}