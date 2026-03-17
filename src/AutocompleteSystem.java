import java.util.*;

public class AutocompleteSystem {

    private Map<String, Integer> frequency = new HashMap<>();

    public void updateFrequency(String query) {
        frequency.put(query, frequency.getOrDefault(query, 0) + 1);
    }

    public List<String> search(String prefix) {

        PriorityQueue<Map.Entry<String,Integer>> pq =
                new PriorityQueue<>((a,b)->b.getValue()-a.getValue());

        for (var e : frequency.entrySet()) {
            if (e.getKey().startsWith(prefix))
                pq.add(e);
        }

        List<String> result = new ArrayList<>();
        for(int i=0;i<10 && !pq.isEmpty();i++)
            result.add(pq.poll().getKey());

        return result;
    }

    public static void main(String[] args) {

        AutocompleteSystem a = new AutocompleteSystem();

        a.updateFrequency("java tutorial");
        a.updateFrequency("javascript");
        a.updateFrequency("java download");

        System.out.println(a.search("jav"));
    }
}