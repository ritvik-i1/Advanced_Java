import java.util.*;

public class AnalyticsSystem {

    private Map<String, Integer> views = new HashMap<>();
    private Map<String, Set<String>> users = new HashMap<>();
    private Map<String, Integer> sources = new HashMap<>();

    public void process(String url, String user, String source) {

        views.put(url, views.getOrDefault(url, 0) + 1);

        users.putIfAbsent(url, new HashSet<>());
        users.get(url).add(user);

        sources.put(source, sources.getOrDefault(source, 0) + 1);
    }

    public void dashboard() {

        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());

        pq.addAll(views.entrySet());

        System.out.println("Top Pages:");
        while (!pq.isEmpty()) {
            var e = pq.poll();
            System.out.println(e.getKey() + " → " + e.getValue());
        }

        System.out.println("\nSources:");
        for (var e : sources.entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue());
        }
    }

    public static void main(String[] args) {
        AnalyticsSystem a = new AnalyticsSystem();

        a.process("/home", "u1", "google");
        a.process("/home", "u2", "facebook");
        a.process("/about", "u1", "direct");

        a.dashboard();
    }
}