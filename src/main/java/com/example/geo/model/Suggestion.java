package com.example.geo.model;

import com.example.geo.util.geo.CoordinateDistance;
import com.example.geo.util.string.StringSimilarity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Suggestion {
    private final String name;
    private final Double latitude;
    private final Double longitude;
    private final Double score;

    public static Suggestion createFrom(City city, String q) {
        String mostSimilarName = city.getName();
        Double mostSimilarScore = StringSimilarity.calculate(city.getName(), q);
        for (String altName : city.getAltNames()) {
            // find the most similar name among all known names of a city
            Double altScore = StringSimilarity.calculate(altName, q);
            if (altScore > mostSimilarScore) {
                mostSimilarName = altName;
                mostSimilarScore = altScore;
            }
        }
        return buildSuggestion(mostSimilarName, city, mostSimilarScore);
    }

    public static Suggestion createFrom(City city, String q, Double latitude, Double longitude) {
        String mostSimilarName = city.getName();
        Double mostSimilarScore = StringSimilarity.calculate(city.getName(), q);
        for (String altName : city.getAltNames()) {
            // Find the most similar name among all known names of a city
            Double altScore = StringSimilarity.calculate(altName, q);
            if (altScore > mostSimilarScore) {
                mostSimilarName = altName;
                mostSimilarScore = altScore;
            }
        }
        final double SCORE_WEIGHT = 0.95;
        Double distance = CoordinateDistance.calculate(city.getLatitude(), city.getLongitude(),
                latitude, longitude);
        // Calculate the score of a suggestion by using weighted mean of name
        // similarity and its distance
        Double finalScore = SCORE_WEIGHT * mostSimilarScore + (1 - SCORE_WEIGHT) * distance;
        return buildSuggestion(mostSimilarName, city, finalScore);
    }

    private static Suggestion buildSuggestion(String name, City city, Double score) {
        return Suggestion.builder()
                .name(getDisplayName(name, city))
                .latitude(city.getLatitude())
                .longitude(city.getLongitude())
                .score(score)
                .build();
    }

    private static String getDisplayName(String mostSimilarName, City city) {
        return new StringBuilder(mostSimilarName)
                .append(", ")
                .append(city.getAdmin1())
                .append(", ")
                .append(city.getCountry() == "US" ? "USA" : "Canada")
                .toString();
    }
}
