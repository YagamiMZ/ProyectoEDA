package Algoritmos;

public class JaroWinkler {

    private static double jaro(String s1, String s2) {
        if (s1 == null) s1 = "";
        if (s2 == null) s2 = "";

        s1 = s1.toLowerCase().trim();
        s2 = s2.toLowerCase().trim();

        int len1 = s1.length();
        int len2 = s2.length();

        if (len1 == 0 && len2 == 0) return 1.0;
        if (len1 == 0 || len2 == 0) return 0.0;

        int matchDistance = Math.max(len1, len2) / 2 - 1;
        if (matchDistance < 0) matchDistance = 0;

        boolean[] s1Matches = new boolean[len1];
        boolean[] s2Matches = new boolean[len2];

        int matches = 0;
        int transpositions = 0;

        for (int i = 0; i < len1; i++) {
            int start = Math.max(0, i - matchDistance);
            int end = Math.min(i + matchDistance + 1, len2);

            for (int j = start; j < end; j++) {
                if (s2Matches[j]) continue;
                if (s1.charAt(i) != s2.charAt(j)) continue;

                s1Matches[i] = true;
                s2Matches[j] = true;
                matches++;
                break;
            }
        }

        if (matches == 0) return 0.0;

        int k = 0;
        for (int i = 0; i < len1; i++) {
            if (!s1Matches[i]) continue;
            while (!s2Matches[k]) k++;
            if (s1.charAt(i) != s2.charAt(k)) transpositions++;
            k++;
        }

        double m = matches;
        return ((m / len1) + (m / len2) + ((m - transpositions / 2.0) / m)) / 3.0;
    }

    public static double getJaroWinkler(String s1, String s2) {
        double jaroScore = jaro(s1, s2);

        if (s1 == null) s1 = "";
        if (s2 == null) s2 = "";

        s1 = s1.toLowerCase().trim();
        s2 = s2.toLowerCase().trim();

        int prefix = 0;
        int maxPrefix = 4;
        int limit = Math.min(Math.min(s1.length(), s2.length()), maxPrefix);

        for (int i = 0; i < limit; i++) {
            if (s1.charAt(i) == s2.charAt(i)) prefix++;
            else break;
        }

        double prefixScale = 0.1;
        return jaroScore + (prefix * prefixScale * (1 - jaroScore));
    }
}
