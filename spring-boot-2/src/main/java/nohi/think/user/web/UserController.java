package nohi.think.user.web;

import java.time.Duration;

import nohi.think.user.model.User;
import nohi.think.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * Created by nohi on 2018/10/16.
 */
@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("")
  public Mono<User> save(User user) {
	return this.userService.save( user );
  }

  @DeleteMapping("/{username}")
  public Mono<Long> deleteByUsername(@PathVariable String username) {
	return this.userService.deleteByUsername( username );
  }

  @GetMapping("/{username}")
  public Mono<User> findByUsername(@PathVariable String username) {
	return this.userService.findByUsername( username );
  }

  @GetMapping(value = "", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<User> findAll() {
	return this.userService.findAll().delayElements( Duration.ofSeconds(5));
  }
}
