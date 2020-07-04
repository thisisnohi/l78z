package nohi.demo.web.web;

import com.alibaba.fastjson.JSONObject;
import nohi.demo.web.dto.TestReq;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-04-18 13:20
 **/
@Controller
public class IndexController {

    @RequestMapping(value = {"/index"})
    @ResponseBody
    public String h1(){
        System.out.println("1.do");
        return "hello....";
    }

    @RequestMapping(value = {"/map"})
    @ResponseBody
    public Map<String,String> map(){
        Map<String, String> map = new HashMap<>();
        map.put("1", "1111");
        map.put("abc", "这是中文");
        return map;
    }

    @RequestMapping("/test")
    @ResponseBody
    public Map<String, String> test(@RequestBody TestReq req){
        System.out.println("请求内容:" + JSONObject.toJSONString(req));
        Map<String, String> map = new HashMap<>();
        map.put("11", "111111");
        map.put("a", "333");
        map.put("b", "中文");
        return map;
    }
}
