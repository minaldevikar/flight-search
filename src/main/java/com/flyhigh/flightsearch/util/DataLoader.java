package com.flyhigh.flightsearch.util;

/**
 * Created by Admin on 8/21/2022.
 */

import com.flyhigh.flightsearch.entity.Flights;
import com.flyhigh.flightsearch.repository.FlightSearchRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.Stream;

@Profile("development")
@Component
@Transactional
public class DataLoader {
    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private static final String flightsFile = "/data/Flights.csv";

    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    CommandLineRunner loadFlights(FlightSearchRepo flightRepo) {
        return (args) -> {
            loadFromCsv(resourceLoader, flightsFile,
                    v -> new Flights(v[0], v[1], v[2], v[3], v[4], Integer.parseInt(v[5])),
                    flightRepo);
        };
    }

    public static void loadFromCsv(ResourceLoader resourceLoader, String sourceCsvFile,
                                   Function<String[], Object> objectMapper, CrudRepository repo) throws IOException {
        logger.debug("++++++++++++++ Loading " + sourceCsvFile + " ..........");

        Resource resource = resourceLoader.getResource("classpath:" + sourceCsvFile);

        try (Stream<String> stream = Files.lines(Paths.get(resource.getFile().getAbsolutePath()))) {
            stream.forEach(line -> {
                logger.debug("++++++++++++++" + line);
                try {
                    String[] values = line.split(",");
                    Object entity = objectMapper.apply(values);
                    repo.save(entity);
                } catch (Exception e) {
                    throw e;
                }
            });

        } catch (IOException e) {
            throw e;
        }
        logger.debug("++++++++++++++ Loading " + sourceCsvFile + " DONE !");

    }
}
