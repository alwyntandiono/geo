package com.example.geo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.springframework.stereotype.Service;

import com.example.geo.model.City;
import com.example.geo.model.Suggestion;

@Service
public class SuggestionService {

    private final List<City> cities;
    private static final int LIMIT = 25;

    SuggestionService(List<City> cities) {
        this.cities = cities;
    }

    public List<Suggestion> getSuggestions(String q) {
        PriorityQueue<Suggestion> minHeap = new PriorityQueue<>(
                (a, b) -> Double.compare(a.getScore(), b.getScore()));
        cities.forEach(city -> {
            minHeap.add(Suggestion.createFrom(city, q));
            if (minHeap.size() >= LIMIT) {
                minHeap.poll();
            }
        });
        List<Suggestion> suggestions = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            suggestions.add(minHeap.poll());
        }
        return suggestions.reversed();
    }

    public List<Suggestion> getSuggestions(String q, Double latitude, Double longitude) {
        PriorityQueue<Suggestion> minHeap = new PriorityQueue<>(
                (a, b) -> Double.compare(a.getScore(), b.getScore()));
        cities.forEach(city -> {
            minHeap.add(Suggestion.createFrom(city, q, latitude, longitude));
            if (minHeap.size() >= LIMIT) {
                minHeap.poll();
            }
        });
        List<Suggestion> suggestions = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            suggestions.add(minHeap.poll());
        }
        return suggestions.reversed();
    }
}
