import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2019-12-05 19:39
 **/
public class TestDate {

    @Test
    public void dateFromString() throws ParseException {
        String dateStr = "2019-12-05";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateStr);
        System.out.println(date);
    }

    @Test
    public void testDate(){
        LocalDate ld = LocalDate.now();

        System.out.println(ld.toString());
    }
}
