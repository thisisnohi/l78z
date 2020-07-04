package nohi.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author NOHI
 * @program: nohi-spring-test
 * @description:
 * @create 2020-04-18 12:33
 **/
public class TestLog {
    //获取logger
    private final static Log logger = LogFactory.getLog(TestLog.class);
    @org.junit.Test
    public void test1(){
        System.out.println("111");
        //使用logger输出日志
        logger.trace("TRACE...");
        logger.debug("DEBUG ...");
        logger.info("INFO ...");
        logger.error("ERROR ...");
        logger.warn("WARN...");

    }
}
