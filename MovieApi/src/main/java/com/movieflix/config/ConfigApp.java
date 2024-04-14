package com.movieflix.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigApp {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
