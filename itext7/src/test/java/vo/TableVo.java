package vo;

import lombok.Data;

import java.util.List;

/**
 * @author NOHI
 * @program: itext7
 * @description:
 * @create 2020-12-11 09:42
 **/
@Data
public class TableVo {
    private String tableName;
    private String tableComment;

    private List<ColumnVo> columns;
}
