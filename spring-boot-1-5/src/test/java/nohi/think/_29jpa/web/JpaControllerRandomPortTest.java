package nohi.think._29jpa.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *  controller测试，随机端口
 * Created by nohi on 2018/5/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JpaControllerRandomPortTest {
	@LocalServerPort
	private int port;

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
	public void testController() throws Exception {
		String json="{\"name\":\"nohi\",\"age\":33}";
		mvc.perform( MockMvcRequestBuilders.post("/jpa/save")
				.accept( MediaType.APPLICATION_JSON_UTF8)
				.contentType( MediaType.APPLICATION_JSON_UTF8 )
				.content(json.getBytes()) //传json参数
				.session(session)
		).andDo( MockMvcResultHandlers.print())
				.andExpect( MockMvcResultMatchers.status().isOk())


	;

//		mvc.perform( MockMvcRequestBuilders.get("/jpa/getAll/nohi")
//				.accept( MediaType.APPLICATION_JSON_UTF8)
//				.contentType( MediaType.APPLICATION_JSON_UTF8 )
//				.session(session)
//		).andDo( MockMvcResultHandlers.print())
//				.andExpect( MockMvcResultMatchers.status().isOk())
//				.andExpect( MockMvcResultMatchers.jsonPath( "$.name" ).value( "nohi" ))
//
//				;
	}

}