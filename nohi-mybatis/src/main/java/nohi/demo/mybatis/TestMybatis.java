package nohi.demo.mybatis;

import nohi.demo.mybatis.dao.BlogMapper;
import nohi.demo.mybatis.entity.Blog;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-04-11 11:54
 **/
public class TestMybatis {

    /**
     * 手动使用mybatis
     */
    @Test
    public void test1() {
        String url = "jdbc:mysql://127.0.0.1:3306/nohi?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC";
        String username = "root";
        String password = "nohi1234";
        DataSource dataSource = new DriverManagerDataSource(url, username, password);

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
//        configuration.setLogImpl(Log4J.class);
        configuration.addMapper(BlogMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            Blog blog = mapper.selectBlog(1);
            System.out.println("blog:" + blog);
            blog = mapper.selectBlog(2);
            System.out.println("blog:" + blog);
            blog = mapper.selectBlog(1);
            System.out.println("blog:" + blog);
        }
    }
}
