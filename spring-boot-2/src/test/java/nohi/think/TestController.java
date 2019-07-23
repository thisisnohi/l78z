package nohi.think;

import java.util.concurrent.TimeUnit;

import nohi.think.user.model.User;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Created by nohi on 2018/10/16.
 */
public class TestController {

  /**
   * 创建WebClient对象并指定baseUrl；
	 HTTP GET；
	 异步地获取response信息；
	 将response body解析为字符串；
	 打印出来；
	 由于是异步的，我们将测试线程sleep 1秒确保拿到response，也可以像前边的例子一样用CountDownLatch。
   * @throws InterruptedException
   */
  @Test
  public void webClientTest1() throws InterruptedException {
	WebClient webClient = WebClient.create("http://localhost:8080");   // 1
	Mono<String> resp = webClient
			.get().uri("/hello") // 2
			.retrieve() // 3
			.bodyToMono(String.class);  // 4
	resp.subscribe(System.out::println);    // 5
	TimeUnit.SECONDS.sleep(1);  // 6
  }

  /**
   * 这次我们使用WebClientBuilder来构建WebClient对象；
	 配置请求Header：Content-Type: application/stream+json；
	 获取response信息，返回值为ClientResponse，retrive()可以看做是exchange()方法的“快捷版”；
	 使用flatMap来将ClientResponse映射为Flux；
	 只读地peek每个元素，然后打印出来，它并不是subscribe，所以不会触发流；
   上个例子中sleep的方式有点low，blockLast方法，顾名思义，在收到最后一个元素前会阻塞，响应式业务场景中慎用。
   * @throws InterruptedException
   */
  @Test
  public void webClientTest2() throws InterruptedException {
	WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build(); // 1
	webClient
			.get().uri("/user")
			.accept( MediaType.APPLICATION_STREAM_JSON) // 2
			.exchange() // 3
			.flatMapMany(response -> response.bodyToFlux(User.class))   // 4
			.doOnNext(System.out::println)  // 5
			.blockLast();   // 6
  }

  @Test
  public void webClientTest3() throws InterruptedException {
	WebClient webClient = WebClient.create("http://localhost:8080");
	webClient
			.get().uri("/times")
			.accept(MediaType.TEXT_EVENT_STREAM)    // 1
			.retrieve()
			.bodyToFlux(String.class)
			.log()  // 2
			.take(10)   // 3
			.blockLast();
  }
}
