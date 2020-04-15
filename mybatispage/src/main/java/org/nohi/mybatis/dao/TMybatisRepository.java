package org.nohi.mybatis.dao;

import org.nohi.mybatis.entity.TMybatisEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-04-01 12:24
 **/
public interface TMybatisRepository extends JpaRepository<TMybatisEntity,Integer> {
}
