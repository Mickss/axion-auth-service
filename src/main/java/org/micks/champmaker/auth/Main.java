package org.micks.champmaker.auth;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.micks.champmaker.auth.connection.DatabaseConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Slf4j
public class Main {

    @Autowired
    private DatabaseConfigProperties databaseConfigProperties;
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @PostConstruct
    public void init() {
        checkDatabaseConnection();
    }

    private void checkDatabaseConnection() {
        if (databaseConfigProperties.getName() == null) {
            throw new IllegalStateException("Cannot read database configuration");
        } else {
            log.info("Database configuration OK. Using database: {}", databaseConfigProperties.getName());
        }
    }
}
