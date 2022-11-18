package vttp.ProjectFinalBackend.services;

import vttp.ProjectFinalBackend.exception.EmailExistException;
import vttp.ProjectFinalBackend.model.User;

public interface UserService {

    User register(String name, String email) throws EmailExistException;

    User findUserByEmail(String email);

    
    
}
