package com.example.geo.util.string;

public class StringSimilarity {

    /**
     * Calculate the similarity of two strings using Jaro-Winkler similarity
     *
     * Reference: https://en.wikipedia.org/wiki/Jaro%E2%80%93Winkler_distance
     *
     * @return the similarity value, scaled from 0 to 1 (higher value indicates
     *         greater similarity)
     */
    public static Double calculate(String s1, String s2) {
        return jaroWinklerSimilarity(s1.toLowerCase(), s2.toLowerCase());
    }

    private static double jaroWinklerSimilarity(String s1, String s2) {
        double jaroSimilarity = jaroSimilarity(s1, s2);
        int prefixLength = 0;
        int maxPrefixLength = 5;
        maxPrefixLength = Math.min(maxPrefixLength, s1.length());
        maxPrefixLength = Math.min(maxPrefixLength, s2.length());
        for (int i = 0; i < maxPrefixLength; i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                prefixLength++;
            } else {
                break;
            }
        }
        double scalingFactor = 0.2;
        return jaroSimilarity + (prefixLength * scalingFactor * (1 - jaroSimilarity));
    }

    private static double jaroSimilarity(String s1, String s2) {
        if (s1.equals(s2)) {
            return 1.0;
        }
        if (s1.isEmpty() || s2.isEmpty()) {
            return 0.0;
        }
        int matchDistance = Math.max(s1.length(), s2.length()) / 2 - 1;
        boolean[] s1Matches = new boolean[s1.length()];
        boolean[] s2Matches = new boolean[s2.length()];
        int matches = 0;
        int transpositions = 0;
        for (int i = 0; i < s1.length(); i++) {
            int start = Math.max(0, i - matchDistance);
            int end = Math.min(s2.length() - 1, i + matchDistance);
            for (int j = start; j <= end; j++) {
                if (s2Matches[j]) {
                    continue;
                }
                if (s1.charAt(i) != s2.charAt(j)) {
                    continue;
                }
                s1Matches[i] = true;
                s2Matches[j] = true;
                matches++;
                break;
            }
        }
        if (matches == 0) {
            return 0.0;
        }
        int k = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (!s1Matches[i]) {
                continue;
            }
            while (!s2Matches[k]) {
                k++;
            }
            if (s1.charAt(i) != s2.charAt(k)) {
                transpositions++;
            }
            k++;
        }
        return ((matches / (double) s1.length()) +
                (matches / (double) s2.length()) +
                ((matches - transpositions / 2.0) / matches)) / 3.0;
    }
}
