package vttp.batch5.csf.assessment.server.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public boolean existsByUsernameAndPassword(String username, String password) {
        String sql = "SELECT COUNT(*) FROM customers WHERE username = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[] { username, password }, Integer.class);
        return count != null && count > 0;
    }
}


