package vttp.ProjectFinalBackend.services.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.ProjectFinalBackend.exception.EmailExistException;
import vttp.ProjectFinalBackend.model.CustomUserDetails;
import vttp.ProjectFinalBackend.model.User;
import vttp.ProjectFinalBackend.repository.UserRepository;
import vttp.ProjectFinalBackend.services.UserService;
import vttp.ProjectFinalBackend.util.Role;

@Service
@Transactional
@Qualifier("UserDetailsService")
public class UserServiceImpl implements UserDetailsService, UserService {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private UserRepository uRepo;
    //private BCryptPasswordEncoder passwordEncoder;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public UserServiceImpl(UserRepository uRepo){
        this.uRepo = uRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = uRepo.findByEmail(username);
        if(user == null){
            LOGGER.error("User not found by username" + username);
            throw new UsernameNotFoundException("User not found by username" + username);
        } else{
            CustomUserDetails userDetails = new CustomUserDetails(user);
            LOGGER.info("Returning found user by username: " + username);
            return userDetails;
        }
        
    }

    @Override
    public User register(String name, String email) throws EmailExistException {
        validateEmail(email);
        
        User user = new User();
        user.setUserId(generateUserId());
        String password = generatePassword();
        String encodedPassword = encodePassword(password);
        user.setPassword(encodedPassword);
        user.setEmail(email);
        user.setName(name);
        user.setRole(Role.ROLE_USER.name());
        user.setAuthorities(Role.ROLE_USER.getAuthorities());
        uRepo.save(user);
        LOGGER.info("New user password: " + password);

        return user;
    }

    private String encodePassword(String password) {
        return passwordEncoder().encode(password);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String generateUserId() {
        return RandomStringUtils.randomNumeric(10);
    }

    private User validateEmail(String email) throws EmailExistException{
        User currentUser = findUserByEmail(email);
        if(currentUser != null){
            throw new EmailExistException("EMAIL ALREADY EXISTS");
        }
        else{
            return currentUser;
        }        
    }

    @Override
    public User findUserByEmail(String email) {
        return uRepo.findByEmail(email);
    }


    
}
