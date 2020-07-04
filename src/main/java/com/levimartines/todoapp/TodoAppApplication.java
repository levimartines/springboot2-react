package com.levimartines.todoapp;

import com.levimartines.todoapp.service.DbService;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class TodoAppApplication implements CommandLineRunner {

    @Autowired
    private DbService dbService;

    public static void main(String[] args) {
        SpringApplication.run(TodoAppApplication.class, args);
    }


    @Override
    public void run(String... args) {
        log.info("##### TODO LIST APP -- INICIADO #####");
        dbService.instantiateTestDatabase();
    }

    @PreDestroy
    public void preDestroy() {
        log.warn("##### TODO LIST APP -- ENCERRANDO #####");
    }

}
