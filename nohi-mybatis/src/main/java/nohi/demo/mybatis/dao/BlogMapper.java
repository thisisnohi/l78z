package nohi.demo.mybatis.dao;

import nohi.demo.mybatis.entity.Blog;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-04-11 12:00
 **/
//@CacheNamespace
public interface BlogMapper {
    @Select("SELECT * FROM blog WHERE id = #{id}")
    Blog selectBlog(int id);
}
