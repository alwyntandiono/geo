package com.example.geo.config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.geo.model.City;
import com.example.geo.util.tsv.CityTsvLoader;

@Configuration
public class AppConfig {

    private final CityTsvLoader cityTsvLoader;

    public AppConfig(CityTsvLoader cityDatasetLoader) {
        this.cityTsvLoader = cityDatasetLoader;
    }

    @Bean
    List<City> cityData() throws UnsupportedEncodingException, IOException {
        return cityTsvLoader.load();
    }
}
