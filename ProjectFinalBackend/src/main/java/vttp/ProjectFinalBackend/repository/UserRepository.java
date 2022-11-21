package vttp.ProjectFinalBackend.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.ProjectFinalBackend.model.User;
import vttp.ProjectFinalBackend.util.Role;

@Repository
public class UserRepository {

    public static final String SQL_SELECT_EMAIL = "SELECT * FROM user WHERE email = ?";
    public static final String SQL_SELECT_NAME = "SELECT * FROM user WHERE name = ?";
    public static final String SQL_INSERT_USER =  "INSERT INTO user (email,name,password,userid,role,authorities) values (?,?,?,?,?,?)";

    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private JdbcTemplate template;
    
    public Boolean save(User user){

        int count = template.update(SQL_INSERT_USER,user.getEmail(),user.getName(),user.getPassword(),user.getUserId(),user.getRole(),user.getAuthorities());

        if(count != 1){
            return false;
        }

        return true;
    }

    public User findByName(String name){

        final SqlRowSet rs = template.queryForRowSet(SQL_SELECT_NAME, name);

        if(!rs.next())
            return null;

        User usr = new User();
        usr.setEmail(rs.getString("email"));
        usr.setName(name);
        usr.setPassword(rs.getString("password"));
        usr.setUserId(rs.getString("userid"));
        return usr;    

    }

    public User findByEmail(String email){

        final SqlRowSet rs = template.queryForRowSet(SQL_SELECT_EMAIL, email);

        if(!rs.next())
            return null;
        LOGGER.info("Rowset:" + rs.getString("email"));
        LOGGER.info("Rowset:" + rs.getString("password"));
        LOGGER.info("Rowset:" + rs.getString("userid"));
        LOGGER.info("Rowset:" + rs.getString("role"));


        User usr = new User();
        usr.setEmail(email);
        usr.setName(rs.getString("name"));
        usr.setPassword(rs.getString("password"));
        usr.setUserId(rs.getString("userid"));
        usr.setRole(rs.getString("role"));
        String role = rs.getString("role");
        if(role == null || role.equals(Role.ROLE_USER.name()))
            usr.setAuthorities(Role.ROLE_USER.getAuthorities());
        else if(role.equals(Role.ROLE_ADMIN.name()))
            usr.setAuthorities(Role.ROLE_ADMIN.getAuthorities());
        return usr;    
    }
    
}
