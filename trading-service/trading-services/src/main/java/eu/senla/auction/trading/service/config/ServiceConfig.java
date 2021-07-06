package eu.senla.auction.trading.service.config;

import eu.senla.auction.trading.api.utils.IScheduledTask;
import eu.senla.auction.trading.service.logger.ServiceLogg;
import eu.senla.auction.trading.service.utils.ScheduledTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan({"eu.senla.auction.trading.service.security", "eu.senla.auction.trading.service.services",
        "eu.senla.auction.trading.api.repository", "eu.senla.auction.trading.service.logger"})
public class ServiceConfig {

    @Bean
    public ServiceLogg serviceLogg(){
        return new ServiceLogg();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
