import java.util.*;

public class PlagiarismDetector {

    int N = 3;

    Set<String> extract(String text) {
        Set<String> set = new HashSet<>();
        String[] w = text.split(" ");

        for (int i = 0; i <= w.length - N; i++) {
            set.add(w[i] + " " + w[i+1] + " " + w[i+2]);
        }
        return set;
    }

    double similarity(String t1, String t2) {
        Set<String> s1 = extract(t1);
        Set<String> s2 = extract(t2);

        int match = 0;
        for (String g : s1) {
            if (s2.contains(g)) match++;
        }

        return (match * 100.0) / s1.size();
    }

    public static void main(String[] args) {
        PlagiarismDetector p = new PlagiarismDetector();

        String a = "this is a sample text for testing plagiarism detection";
        String b = "this is a sample text for plagiarism detection system";

        System.out.println("Similarity: " + p.similarity(a, b) + "%");
    }
}