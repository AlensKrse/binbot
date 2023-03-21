package com.binance.crypto.bot;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@EntityScan("com.binance.crypto.bot")
@SpringBootApplication
public class BinanceCryptoBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(BinanceCryptoBotApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
