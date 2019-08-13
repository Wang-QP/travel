package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        List<Category> cs = null;
        if (categorys == null || categorys.size() == 0) {
            System.out.println("从数据库查询...");
            cs = categoryDao.findAll();
            for (Category c : cs) {
                jedis.zadd("category",c.getCid(),c.getCname());
            }
        } else {
            cs = new ArrayList<Category>();
            for (Tuple category : categorys) {
                Category c = new Category();
                c.setCid((int) category.getScore());
                c.setCname(category.getElement());
                cs.add(c);
            }
        }
        return cs;
    }
}
