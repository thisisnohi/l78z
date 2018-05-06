package nohi.think._29jpa.repository;

import java.util.List;

import nohi.think._29jpa.entity.TUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * Created by nohi on 2018/5/6.
 */
@Component
public interface UserRepository extends CrudRepository<TUserEntity, Integer> {

//	List<TUserEntity> findAll();

	List<TUserEntity> findAllByName(String name);
}
