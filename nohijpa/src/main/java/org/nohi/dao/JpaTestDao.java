package org.nohi.dao;

import org.nohi.entity.JpaTestEntity;
import org.springframework.data.repository.Repository;

/**
 * Created by nohi on 2018/2/8.
 */
@org.springframework.stereotype.Repository
public interface JpaTestDao  extends Repository<JpaTestEntity, String> {
	// 通过id查找实体
	JpaTestEntity getByProwId(String id);
}
