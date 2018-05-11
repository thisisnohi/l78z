package nohi.think;

import nohi.think.web.HelloContoller;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by nohi on 2018/5/7.
 */
@RunWith( SpringRunner.class )
@WebMvcTest(HelloContoller.class)
public class TestMockMVC {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;
	private MockHttpSession session;


	@Before
	public void setupMockMvc(){
		mvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
		session = new MockHttpSession();
//		User user =new User("root","root");
//		session.setAttribute("user",user); //拦截器那边会判断用户是否登录，所以这里注入一个用户
	}

	@Test
	public void test1() throws Exception {
		this.mvc.perform( MockMvcRequestBuilders.get("/hello/adasdf") ).andDo( MockMvcResultHandlers.print() ).andExpect( MockMvcResultMatchers.status().isOk() );
	}

}
