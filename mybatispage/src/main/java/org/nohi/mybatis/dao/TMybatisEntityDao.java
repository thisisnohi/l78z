package org.nohi.mybatis.dao;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.nohi.mybatis.entity.TMybatisEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@CacheNamespace
public interface TMybatisEntityDao {
    int deleteByPrimaryKey(Integer tId);

    int insert(TMybatisEntity record);

    int insertSelective(TMybatisEntity record);

    TMybatisEntity selectByPrimaryKey(Integer tId);

    List<TMybatisEntity> selectByExample(TMybatisEntity info);
}
