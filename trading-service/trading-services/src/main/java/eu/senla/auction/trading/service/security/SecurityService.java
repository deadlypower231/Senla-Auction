package eu.senla.auction.trading.service.security;

import eu.senla.auction.trading.api.services.ISecurityService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@ComponentScan("eu.senla.auction.trading.rest.config")
public class SecurityService implements ISecurityService {

    @Override
    @Transactional
    public String findLoggedInUser() {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return null;
    }

}
