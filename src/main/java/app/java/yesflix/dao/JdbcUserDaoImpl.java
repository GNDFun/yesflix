package app.java.yesflix.dao;

import app.java.yesflix.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class JdbcUserDaoImpl implements UserDao{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User getByPK(Long id) throws PersistException{
        String sql = "SELECT user_id, name, password, email, registration_datetime FROM users WHERE user_id = ?";
        List<User> userList = jdbcTemplate.query(sql, new UserRawMapper(), id);
        return DataAccessUtils.singleResult(userList);
    }

    @Override
    public User getByEmail(String email) throws PersistException{
        String sql = "SELECT user_id, name, password, email, registration_datetime FROM users WHERE email = ?";
        List<User> userListByEmail = jdbcTemplate.query(sql, new UserRawMapper(), email);
        return DataAccessUtils.singleResult(userListByEmail);
    }

    @Override
    public List<User> getAll() throws PersistException{
        String sql = "SELECT * FROM users";
        List<User> userList = jdbcTemplate.query(sql, new UserRawMapper());
        return userList;
    }

    @Override
    public User create(User user) throws PersistException{
        String sql = "INSERT INTO users " +
                "(name, password, email, registration_datetime) VALUES(?, ?, ?, ?)";
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
                final PreparedStatement ps = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getName());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getEmail());
                ps.setTimestamp(4, Timestamp.valueOf(user.getRegistrationDatetime()));
                return ps;
            }
        };
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);

        Long newId;

        if (keyHolder.getKeys().size() > 1) {
            newId = ((Integer) keyHolder.getKeys().get("user_id")).longValue();
        } else {
            newId = keyHolder.getKey().longValue();
        }
        user.setId(newId);
        return user;
    }

    @Override
    public User update(User user) throws PersistException {
        String sql = "UPDATE users SET name = ?, password = ? WHERE user_id = ?";
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
                final PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, user.getName());
                ps.setString(2, user.getPassword());
                ps.setLong(3, user.getId());
                return ps;
            }
        };
        jdbcTemplate.update(psc);
        return user;
    }

    @Override
    public Integer delete(Long id) throws PersistException{
        return jdbcTemplate.update("DELETE FROM users WHERE user_id = ?", id);
    }

    public class UserRawMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("user_id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setRegistrationDatetime(rs.getTimestamp("registration_datetime").toLocalDateTime());
            return user;
        }
    }
}
