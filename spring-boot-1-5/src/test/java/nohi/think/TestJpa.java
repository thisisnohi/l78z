package nohi.think;

import java.util.List;

import nohi.think._29jpa.entity.TUserEntity;
import nohi.think._29jpa.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestJpa {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private UserRepository repository;


	@Test
	public void exampleTest() {
		TUserEntity t = new TUserEntity();
		t.setName( "test" );
		t.setAddress( "aaaaaaa" );
		t.setAge( 11 );
		this.entityManager.persist( t );
		List<TUserEntity> list = this.repository.findAllByName("test");

		assertThat(list).isNotEmpty();
		assertThat(list.get( 0 ).getName()).isSameAs( "test" );
		assertThat(list.get( 0 ).getName()).isSameAs( "test1" );
//		assertThat(user.getVin()).isEqualTo("1234");
	}

}
