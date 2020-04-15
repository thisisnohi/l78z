package org.nohi.mybatis.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.nohi.mybatis.context.SpringContextUtils;
import org.nohi.mybatis.dao.TMybatisEntityDao;
import org.nohi.mybatis.entity.TMybatisEntity;
import org.nohi.mybatis.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-03-30 20:04
 **/
@RestController
@RequestMapping("/")
@Slf4j
public class TestController {

    @Autowired
    TMybatisEntityDao tMybatisEntityDao;

    @Autowired
    TestService service;

    @RequestMapping(value = "/findAll", method = {RequestMethod.GET,RequestMethod.POST})
    public List<TMybatisEntity> Sel(){
        System.out.println("====11===");
        TMybatisEntity info = new TMybatisEntity();
        return tMybatisEntityDao.selectByExample(info);
    }

    @RequestMapping(value = "/findAll2", method = {RequestMethod.GET,RequestMethod.POST})
    public List<TMybatisEntity> findAll2() {
        TMybatisEntity info = new TMybatisEntity();
        List<TMybatisEntity> lists = tMybatisEntityDao.selectByExample(info);
        System.out.println("第一次查询完成");
        lists =  tMybatisEntityDao.selectByExample(info);
        System.out.println("第二次查询完成");
        return lists;
    }

    @RequestMapping(value = "/findAll3", method = {RequestMethod.GET,RequestMethod.POST})
    public PageInfo<TMybatisEntity> findAll3(int pageNum, int pageSize) {
        // TODO Auto-generated method stub
        TMybatisEntity info = new TMybatisEntity();
        PageHelper.startPage(pageNum, pageSize);
        List<TMybatisEntity> lists =  tMybatisEntityDao.selectByExample(info);
        PageInfo<TMybatisEntity> pageInfo = new PageInfo<TMybatisEntity>(lists);
        System.out.println("JSONObject.toString():" + JSONObject.toJSONString(pageInfo));
        return pageInfo;
    }

    @RequestMapping(value = "/findAllJpa", method = {RequestMethod.GET,RequestMethod.POST})
    public List<TMybatisEntity> findAllJpa(){
        System.out.println("====findAllJpa===");
        List<TMybatisEntity> list = service.findAll();
        System.out.println("第一次查询完成");
        list = service.findAll();
        return list;
    }

    @RequestMapping(value = "/testTa", method = {RequestMethod.GET,RequestMethod.POST})
    public List<TMybatisEntity> testTa() {
        service.testTa();
        return service.findAll();
    }

    /**
     * 测试缓存
     * @return
     */
    @RequestMapping(value = "/testCache", method = {RequestMethod.GET,RequestMethod.POST})
    public List<TMybatisEntity> testCache() {
        SqlSessionFactory factory = SpringContextUtils.getBean(SqlSessionFactory.class);
        System.out.println("一级缓存范围: " + factory.getConfiguration().getLocalCacheScope());
        System.out.println("二级缓存是否被启用: " + factory.getConfiguration().isCacheEnabled());
        TMybatisEntity info = new TMybatisEntity();
        List<TMybatisEntity> lists = tMybatisEntityDao.selectByExample(info);
        System.out.println("第一次查询完成");
        lists =  tMybatisEntityDao.selectByExample(info);
        System.out.println("第二次查询完成");
        service.testCache();
        return lists;
    }
    /**
     * 测试缓存
     * @return
     */
    @RequestMapping(value = "/testCache2", method = {RequestMethod.GET,RequestMethod.POST})
    public List<TMybatisEntity> testCache2() {
        SqlSessionFactory factory = SpringContextUtils.getBean(SqlSessionFactory.class);
        System.out.println("一级缓存范围: " + factory.getConfiguration().getLocalCacheScope());
        System.out.println("二级缓存是否被启用: " + factory.getConfiguration().isCacheEnabled());
        service.testCache();
        return null;
    }

}
