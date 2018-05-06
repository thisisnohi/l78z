import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.nohi.dao.JpaTestDao;
import org.nohi.entity.JpaTestEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by nohi on 2018/2/8.
 */
public class TestConfig {
	private static ApplicationContext ctx ;
	static {
		// 通过静态代码块的方式,让程序加载spring的配置文件
		ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	}
	@Test
	public void testConfig(){
		DataSource dataSouce = (DataSource) ctx.getBean("dataSource");
		System.out.println("数据源："+ dataSouce);
		try {
			System.out.println("连接："+ dataSouce.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCrud(){
		DataSource dataSouce = (DataSource) ctx.getBean("dataSource");
		JpaTestDao dao = (JpaTestDao) ctx.getBean( JpaTestDao.class);

		JpaTestEntity info = dao.getByProwId( "11" );
		System.out.println(info);
		if (null != info) {
			System.out.println( info.getInsuraceCode());
		}
	}
}
