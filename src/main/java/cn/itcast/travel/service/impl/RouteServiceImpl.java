package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    RouteDao routeDao = new RouteDaoImpl();
    RouteImgDao routeImgDao = new RouteImgDaoImpl();
    SellerDao sellerDao = new SellerDaoImpl();
    FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        PageBean<Route> pb = new PageBean<Route>();
        //当前页码
        pb.setCurrentPage(currentPage);
        //每页显示条数
        pb.setPageSize(pageSize);

        //总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);
        //当前页数据
        int start = (currentPage -1 )*pageSize;
        List<Route> list = routeDao.findByPage(cid,start,pageSize,rname);
        pb.setList(list);
        //总页数
        pb.setTotalPage(totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1);
        return pb;
    }

    @Override
    public Route findOne(int rid) {
        Route route = routeDao.findOne(rid);
        List<RouteImg> routeImgList = routeImgDao.findByRid(rid);
        route.setRouteImgList(routeImgList);
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);
        int count = favoriteDao.findCountByRid(rid);
        route.setCount(count);
        return route;
    }
}
