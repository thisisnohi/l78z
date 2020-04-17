package nohi.demo.web;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.Test;
import nohi.demo.web.servlet.HelloServlet;

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

//        Testtomcat m = new Testtomcat();
//        m.testtomcat();
    }

    @Test
    public void testtomcat() {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8088);
        try {
            tomcat.start();

//            Context context =  tomcat.addWebapp("/tomcat", "/Users/nohi/tmp/html");
//
//            HelloServlet helloServlet = new HelloServlet();
//            tomcat.addServlet("/tomcat", "helloServlet", helloServlet);
//
//            context.addServletMappingDecoded("/helloServlet.do", "helloServlet");

            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
