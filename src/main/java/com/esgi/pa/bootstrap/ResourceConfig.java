package com.esgi.pa.bootstrap;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.esgi.pa.api.resources")
public class ResourceConfig {
}