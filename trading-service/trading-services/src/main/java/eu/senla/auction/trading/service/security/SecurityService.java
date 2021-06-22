package eu.senla.auction.trading.service.security;

import eu.senla.auction.trading.api.services.ISecurityService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
@ComponentScan("eu.senla.auction.trading.rest.config")
public class SecurityService implements ISecurityService {


}
