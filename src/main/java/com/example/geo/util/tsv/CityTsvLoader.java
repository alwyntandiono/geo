package com.example.geo.util.tsv;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.example.geo.model.City;

@Component
public class CityTsvLoader {

    public List<City> load() throws UnsupportedEncodingException, IOException {
        Resource resource = new ClassPathResource("cities_canada-usa.tsv");
        Reader reader = new InputStreamReader(resource.getInputStream(), "UTF-8");
        return StreamSupport.stream(parseTsv(reader).spliterator(), false)
                .map(CityTsvLoader::parseRecord)
                .toList();
    }

    private static City parseRecord(CSVRecord record) {
        String name = record.get("name").strip();
        List<String> altNames = Arrays.stream(record.get("alt_name").split(","))
                .map(String::strip)
                .collect(Collectors.toList());
        Double latitude = Double.parseDouble(record.get("lat"));
        Double longitude = Double.parseDouble(record.get("long"));
        String country = record.get("country");
        String admin1 = record.get("admin1");
        return City.builder()
                .name(name)
                .altNames(altNames)
                .admin1(admin1)
                .country(country)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    private Iterable<CSVRecord> parseTsv(Reader reader) throws IOException {
        return CSVFormat.TDF.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setQuote(null)
                .get()
                .parse(reader);
    }
}
