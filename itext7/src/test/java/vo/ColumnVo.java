package vo;

import lombok.Data;

/**
 * @author NOHI
 * @program: itext7
 * @description:
 * @create 2020-12-11 09:43
 **/
@Data
public class ColumnVo {
    private String columnName;
    private String columnType;
    private String columnKey;
    private String isNullable;
    private String columnComment;
}
