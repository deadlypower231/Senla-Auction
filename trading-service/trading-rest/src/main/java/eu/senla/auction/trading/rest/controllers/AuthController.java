package eu.senla.auction.trading.rest.controllers;


import eu.senla.auction.trading.api.dto.user.CreateUserDto;
import eu.senla.auction.trading.api.dto.user.UserDto;
import eu.senla.auction.trading.api.services.IAuthService;
import eu.senla.auction.trading.api.services.IUserService;
import eu.senla.auction.trading.rest.config.MongoUserDetailsService;
import eu.senla.auction.trading.rest.jwt.JwtRequest;
import eu.senla.auction.trading.rest.jwt.JwtResponse;
import eu.senla.auction.trading.rest.jwt.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class AuthController {

    private final IAuthService authService;
    private final IUserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final MongoUserDetailsService mongoUserDetailsService;
    private final AuthenticationManager authenticationManager;

    public AuthController(IAuthService authService, IUserService userService, JwtTokenUtil jwtTokenUtil,
                           MongoUserDetailsService mongoUserDetailsService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.mongoUserDetailsService = mongoUserDetailsService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/signup")
    public UserDto signup(@RequestBody CreateUserDto createUserDto) {
        return this.userService.saveUser(createUserDto);
    }

    @GetMapping("/success")
    public UserDto getLoginInfo(OAuth2AuthenticationToken authentication) {
        return this.authService.createOrLoginUer(authentication);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = mongoUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
