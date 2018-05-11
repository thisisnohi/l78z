package nohi.think;

import nohi.think._29jpa.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMock {
	@MockBean
	private UserRepository remoteService;

	@Test
	public void exampleTest() {
//		given(this.remoteService.findAll()).willReturn("mock");
//		String reverse = reverser.reverseSomeCall();
//		assertThat(reverse).isEqualTo("kcom");
	}

}
