package eu.senla.auction.trading.rest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"eu.senla.auction.trading.service.config", "eu.senla.auction.trading.rest"})
public class RestConfig implements WebMvcConfigurer {
}
