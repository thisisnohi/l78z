package nohi.demo.mybatis.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-04-11 12:02
 **/
@Data
public class Blog implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    private String content;

    /**
     * 生日
     */
    private Date createdate;

    /**
     * 你猜
     */
    private String author;
}
