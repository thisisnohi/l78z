package nohi.think.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * https://blog.csdn.net/get_set/article/details/79480233
 * Created by nohi on 2018/10/16.
 */
@RestController
public class HelloController {

  @GetMapping(value = {"","hello"})
  public String index() {
	return "Welcome to reactive world ~...";
  }

  @GetMapping(value = {"webflux"})
  public Mono<String> webflux() {
	return Mono.just("Welcome to webflux world ~");
  }
}
