package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findByUsername(String username) {
        User user = null;
        String sql = "select * from tab_user where username = ?";
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (DataAccessException e) {
        }
        return user;
    }

    @Override
    public void save(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) value(?,?,?,?,?,?,?,?,?)";
        template.update(sql, user.getUsername(), user.getPassword(),
                user.getName(), user.getBirthday(), user.getSex(),
                user.getTelephone(), user.getEmail(),
                user.getStatus(), user.getCode());
    }

    @Override
    public User findByCode(String code) {
        String sql = "select * from tab_user where code = ?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {
        }
        return user;
    }

    @Override
    public void updataStatus(User user) {
        String sql = "update tab_user set status='Y' where code = ?";
        template.update(sql,user.getCode());
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        String sql = "select * from tab_user where username = ? and password = ?";
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        } catch (DataAccessException e) {
        }
        return user;
    }
}
