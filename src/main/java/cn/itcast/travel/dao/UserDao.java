package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    //查询用户是否存在
    public abstract User findByUsername (String username);
    //保存数据
    public abstract void save(User user);

    User findByCode(String code);

    void updataStatus(User user);

    User findByUsernameAndPassword(String username, String password);
}
