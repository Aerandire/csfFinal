package vttp.ProjectFinalBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp.ProjectFinalBackend.exception.EmailExistException;
import vttp.ProjectFinalBackend.exception.ExceptionHandling;
import vttp.ProjectFinalBackend.model.CustomUserDetails;
import vttp.ProjectFinalBackend.model.User;
import vttp.ProjectFinalBackend.services.UserService;
import vttp.ProjectFinalBackend.util.JWTTokenProvider;
import vttp.ProjectFinalBackend.util.SecurityConstant;

@RestController
@RequestMapping(path = {"/", "/user"}, produces = MediaType.APPLICATION_JSON_VALUE)
public class HomeController extends ExceptionHandling{

    @Autowired
    private UserService uSvc;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JWTTokenProvider jwjTokProv;
    
    @GetMapping("/home")
    public String showUser() throws EmailExistException {
        return "Hello";
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        
        authenticate(user.getEmail(), user.getPassword());
        User loginUser = uSvc.findUserByEmail(user.getEmail());
        CustomUserDetails cudUser = new CustomUserDetails(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(cudUser);
        return new ResponseEntity<>(loginUser, jwtHeader, HttpStatus.OK);

    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws EmailExistException {
        User regUser = uSvc.register(user.getName(), user.getEmail());
        return new ResponseEntity<>(regUser, HttpStatus.OK);
    }

    private HttpHeaders getJwtHeader(CustomUserDetails user) {
        HttpHeaders headers = new HttpHeaders();
        //headers.add(SecurityConstant.JWT_TOKEN_HEADER, jwjTokProv.generateJwtToken(user));
        headers.add(SecurityConstant.JWT_TOKEN_HEADER, jwjTokProv.generateJwtToken(user));
        return headers;
    }

    private void authenticate(String email, String password) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
