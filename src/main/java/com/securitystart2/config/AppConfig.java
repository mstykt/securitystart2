package com.securitystart2.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Application Context Configuration
 * <p>
 * Created by mesut on 27.8.2016.
 */
@Configuration
@ComponentScan(value = "com.securitystart2")
@EnableWebMvc
@Import({WebResolver.class})
public class AppConfig {
}
