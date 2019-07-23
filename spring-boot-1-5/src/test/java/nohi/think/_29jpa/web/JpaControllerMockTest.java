package nohi.think._29jpa.web;

import java.util.ArrayList;
import java.util.List;

import nohi.think._29jpa.entity.TUserEntity;
import nohi.think._29jpa.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Created by nohi on 2018/5/9.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(JpaController.class)
public class JpaControllerMockTest {
 	@Autowired
	private MockMvc mvc;

 	@MockBean
	private UserRepository userRepository;

 	@Before
 	public void before(){
		List<TUserEntity> list = new ArrayList<TUserEntity>(  );
		TUserEntity tmp = new TUserEntity();
		tmp.setAge( 11 );
		tmp.setAddress( "111111111" );
		tmp.setName( "测试mock" );
		tmp.setUid( -1 );
		list.add( tmp );
		Mockito.when(userRepository.findAllByName( "nohi" )).thenReturn( list );
	}

	@Test
	public void testController() throws Exception {
		String json="{\"name\":\"nohi\",\"age\":33}";
		mvc.perform( MockMvcRequestBuilders.post("/jpa/save")
				.accept( MediaType.APPLICATION_JSON_UTF8)
				.contentType( MediaType.APPLICATION_JSON_UTF8 )
				.content(json.getBytes()) //传json参数
		).andDo( MockMvcResultHandlers.print())
				.andExpect( MockMvcResultMatchers.status().isOk())


	;
		System.out.println("=================================");

		mvc.perform( MockMvcRequestBuilders.get("/jpa/getAll/nohi")
				.accept( MediaType.APPLICATION_JSON_UTF8)
				.contentType( MediaType.APPLICATION_JSON_UTF8 )
		).andDo( MockMvcResultHandlers.print())
				.andExpect( MockMvcResultMatchers.status().isOk())
//				.andExpect( MockMvcResultMatchers.jsonPath( "$.name" ).value( "nohi" ))

				;
	}

}