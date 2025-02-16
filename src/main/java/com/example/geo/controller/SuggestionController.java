package com.example.geo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.geo.model.Suggestion;
import com.example.geo.service.SuggestionService;

@RestController
public class SuggestionController {

    private final SuggestionService suggestionService;

    SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping("/suggestions")
    public List<Suggestion> getSuggestions(
            @RequestParam String q,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude) {
        if (latitude == null || longitude == null) {
            return suggestionService.getSuggestions(q);
        }
        return suggestionService.getSuggestions(q, latitude, longitude);
    }
}
