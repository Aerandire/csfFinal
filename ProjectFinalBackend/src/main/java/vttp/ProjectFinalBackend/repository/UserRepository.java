package vttp.ProjectFinalBackend.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.ProjectFinalBackend.model.User;

@Repository
public class UserRepository {

    public static final String SQL_SELECT_EMAIL = "SELECT * FROM user WHERE email = ?";
    public static final String SQL_SELECT_NAME = "SELECT * FROM user WHERE name = ?";
    public static final String SQL_INSERT_USER =  "INSERT INTO user (email,name,password,role,authorities,userid) values (?,?,?,?,?,?)";

    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private JdbcTemplate template;
    
    public Boolean save(User user){

        int count = template.update(SQL_INSERT_USER,user.getEmail(),user.getName(),user.getPassword(),user.getRole(),user.getAuthorities(),user.getUserId());

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
        LOGGER.error("Rowset:" + rs.getString("email"));
        LOGGER.error("Rowset:" + rs.getString("password"));
        LOGGER.error("Rowset:" + rs.getString("userid"));

        User usr = new User();
        usr.setEmail(email);
        usr.setName(rs.getString("name"));
        usr.setPassword(rs.getString("password"));
        usr.setUserId(rs.getString("userid"));
        return usr;    

    }
    
}
