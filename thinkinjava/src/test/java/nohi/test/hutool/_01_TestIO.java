package nohi.test.hutool;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import org.junit.Test;

import java.io.File;

/**
 * @author NOHI
 * 2021-04-14 13:27
 **/
public class _01_TestIO {

    @Test
    public void testFileType() {
        File file = FileUtil.file("/Users/nohi/work/workspaces-nohi/nohithink/README.MD");
        file = FileUtil.file("/Users/nohi/Downloads/movie/悍城/悍城07.mp4");
        String type = FileTypeUtil.getType(file);
        //输出 jpg则说明确实为jpg文件
        Console.log(type);
    }
}
