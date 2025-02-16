package com.example.geo.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class City {
    private final String name;
    private final List<String> altNames;
    private final String admin1;
    private final String country;
    private final Double latitude;
    private final Double longitude;
}
