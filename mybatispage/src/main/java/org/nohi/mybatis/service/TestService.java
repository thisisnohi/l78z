package org.nohi.mybatis.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.nohi.mybatis.dao.TMybatisEntityDao;
import org.nohi.mybatis.dao.TMybatisRepository;
import org.nohi.mybatis.entity.TMybatisEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-04-01 12:12
 **/
@Service
@Slf4j
public class TestService {
    @Autowired
    TMybatisRepository tMybatisRepository;

    @Autowired
    TMybatisEntityDao tMybatisEntityDao;


    public List<TMybatisEntity> findAll(){
        return tMybatisRepository.findAll();
    }


    @Transactional
    public void testTa(){
        // JPA保存数据
        TMybatisEntity info = tMybatisRepository.getOne(1);
        log.info("1:{}", JSONObject.toJSONString(info, true));
        String tName = LocalDateTime.now().toString();
        log.info("tname:{}", tName);
        info.setTName(tName);
        tMybatisRepository.save(info);

        tMybatisRepository.flush();

        // mybatis查询数据
        info = tMybatisEntityDao.selectByPrimaryKey(1);
        log.info("2:{}", JSONObject.toJSONString(info, true));
    }

    @Transactional
    public void testCache(){
        TMybatisEntity info = new TMybatisEntity();
        List<TMybatisEntity> lists = tMybatisEntityDao.selectByExample(info);
        System.out.println("第一次查询完成");
        lists =  tMybatisEntityDao.selectByExample(info);
        System.out.println("第二次查询完成");
    }
}
