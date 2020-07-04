package nohi.demo.web.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-04-18 13:20
 **/
@RestController("/hello")
public class HelloController {

    @RequestMapping("abc")
    public String h1(){
        System.out.println("1.do");
        return "hello....";
    }
}
