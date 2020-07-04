package nohi.demo.web.web;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import nohi.demo.web.dto.TestReq;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
