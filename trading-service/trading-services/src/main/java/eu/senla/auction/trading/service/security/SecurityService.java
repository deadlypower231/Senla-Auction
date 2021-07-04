package eu.senla.auction.trading.service.security;

import eu.senla.auction.trading.api.services.ISecurityService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityService implements ISecurityService {

    @Override
    @Transactional
    public String findLoggedInUser() {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            Object s  = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (s instanceof DefaultOidcUser){
                return ((DefaultOidcUser) s).getEmail();
            }
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return null;
    }

}
