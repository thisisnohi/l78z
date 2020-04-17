package nohi.demo;

import nohi.demo.web.servlet.HelloServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * @author NOHI
 * @program: spring
 * @description:
 * @create 2020-04-11 19:53
 **/
public class Testtomcat {

    public static void main(String[] args) throws LifecycleException {

        System.out.println("====");

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8088);
        tomcat.setHostname("127.0.0.1");
        tomcat.setBaseDir("."); // tomcat 信息保存在项目下


        Context context = tomcat.addWebapp("/boot", "/Users/nohi/tmp/html");
        HelloServlet helloServlet = new HelloServlet();
        tomcat.addServlet("/boot", "index", helloServlet);
        context.addServletMappingDecoded("/index.do", "index");

        tomcat.start();
        tomcat.getServer().await();
    }
}
