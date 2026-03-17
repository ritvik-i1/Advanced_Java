import java.util.*;

public class UsernameChecker {

    private Map<String, Integer> users = new HashMap<>();
    private Map<String, Integer> frequency = new HashMap<>();

    public UsernameChecker() {
        users.put("john_doe", 1);
        users.put("admin", 2);
    }

    public boolean checkAvailability(String username) {
        frequency.put(username, frequency.getOrDefault(username, 0) + 1);
        return !users.containsKey(username);
    }

    public List<String> suggestAlternatives(String username) {
        List<String> res = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            String s = username + i;
            if (!users.containsKey(s)) res.add(s);
        }

        if (username.contains("_")) {
            String alt = username.replace("_", ".");
            if (!users.containsKey(alt)) res.add(alt);
        }

        return res;
    }

    public String getMostAttempted() {
        String ans = "";
        int max = 0;

        for (var e : frequency.entrySet()) {
            if (e.getValue() > max) {
                max = e.getValue();
                ans = e.getKey();
            }
        }
        return ans + " (" + max + ")";
    }

    public static void main(String[] args) {
        UsernameChecker u = new UsernameChecker();

        System.out.println(u.checkAvailability("john_doe"));
        System.out.println(u.checkAvailability("jane"));
        System.out.println(u.suggestAlternatives("john_doe"));

        u.checkAvailability("admin");
        u.checkAvailability("admin");

        System.out.println(u.getMostAttempted());
    }
}