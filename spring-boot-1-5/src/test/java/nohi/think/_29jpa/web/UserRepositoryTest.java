package nohi.think._29jpa.web;

import nohi.think._29jpa.entity.TUserEntity;
import nohi.think._29jpa.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by nohi on 2018/5/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;


	@Test
	public void testService(){
		Iterable<TUserEntity> list =  userRepository.findAll();
		System.out.println(list);
		assertThat(list).isNotEmpty();

		assertThat( list.iterator().next().getName() ).isNotEmpty();
	}
}