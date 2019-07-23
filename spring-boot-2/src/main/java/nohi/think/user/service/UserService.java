package nohi.think.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nohi.think.user.model.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by nohi on 2018/10/16.
 */
@Service
public class UserService {

  public static List<User> tmpList = new ArrayList<>(  );

  static {

    User user = null;

    for (int i = 0 ; i < 4 ; i ++) {
	  user = new User();
	  user.setId( "ID_" + i );
	  user.setName( "Name_" + i );
	  user.setBirthday( new Date() );
	  user.setEmail( i + "@qq.com" );
	  user.setPhone( "1341234567" + i );
	  tmpList.add( user );
	}


  }

  /**
   * 保存或更新。
   * 如果传入的user没有id属性，由于username是unique的，在重复的情况下有可能报错，
   * 这时找到以保存的user记录用传入的user更新它。
   */
  public Mono<User> save(User user) {
	tmpList.add( user );
	return Mono.just( user );
  }

  public Mono<Long> deleteByUsername(String username) {
	return Mono.just( 1L );
  }

  public Mono<User> findByUsername(String username) {
	return Mono.just( tmpList.get( 0 ) );
  }

  public Flux<User> findAll() {
	return Mono.just(tmpList).flatMapMany(Flux::fromIterable);
  }
}
