package com.esgi.pa.bootstrap.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.esgi.pa.domain.services")
public class ServiceConfig {
}
