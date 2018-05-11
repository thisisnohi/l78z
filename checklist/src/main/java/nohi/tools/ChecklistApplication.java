package nohi.tools;

import nohi.tools.checklist.CheckListService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ChecklistApplication {

	public static void main(String[] args) {
		System.out.println("==============启动时中文正常吗?======================");
		ConfigurableApplicationContext ctx = SpringApplication.run(ChecklistApplication.class, args);

		//执行copy服务
		CheckListService service = ctx.getBean( CheckListService.class );
		service.doIt();
	}
}
