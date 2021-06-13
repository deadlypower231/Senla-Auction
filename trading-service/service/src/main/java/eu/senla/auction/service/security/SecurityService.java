package eu.senla.auction.service.security;

import eu.senla.auction.api.services.ISecurityService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
@ComponentScan("eu.senla.auction.rest.config")
public class SecurityService implements ISecurityService {


}
