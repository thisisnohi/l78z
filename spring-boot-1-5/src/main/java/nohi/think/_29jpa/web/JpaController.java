package nohi.think._29jpa.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import nohi.think._29jpa.entity.TUserEntity;
import nohi.think._29jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by nohi on 2018/5/6.
 */
@RestController
@RequestMapping("/jpa")
@Api(value = "JPA测试类")
public class JpaController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "getAll",method = RequestMethod.GET)
	@ApiOperation(value="获取用户列表", notes="...")
	public Object getAll(){
		return userRepository.findAll();
	}

	@ApiOperation(value="根据名称查询", notes="根据名称查询")
	@RequestMapping(value = "getAll/{name}",method = RequestMethod.GET)
	public Object getAll(@PathVariable("name") String name){
		return userRepository.findAllByName( name );
	}


	@ApiOperation(value="根据名称查询", notes="根据名称查询")
	@ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "TUserEntity")
	@RequestMapping(value = "save",method = RequestMethod.POST)
	public Object save(@RequestBody TUserEntity user){
		return userRepository.save( user );
	}
}
