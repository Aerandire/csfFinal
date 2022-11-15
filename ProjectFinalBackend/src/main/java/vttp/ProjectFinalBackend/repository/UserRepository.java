package vttp.ProjectFinalBackend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import vttp.ProjectFinalBackend.model.User;

public class UserRepository {

    public static final String SQL_SELECT_EMAIL = "SELECT * FROM user WHERE email = ?";
    
    @Autowired
    private JdbcTemplate template;
    
    public Boolean save(User user){

        

        return true;

    }

    public User findByEmail(String email){

        final SqlRowSet rs = template.queryForRowSet(SQL_SELECT_EMAIL, email);

        if(!rs.next())
            return null;

        User usr = new User();
        usr.setEmail(email);
        usr.setName(rs.getString("name"));
        usr.setPassword(rs.getString("password"));
        usr.setId(rs.getLong("id"));
        return usr;    

    }
    
}
