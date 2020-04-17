package nohi.demo.jdk14;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-04-17 12:46
 **/
record Persion(String name, String lastName){
    public Persion(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public static void main(String[] args){
        System.out.println("====");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        String str = sdf.format(new Date());
        System.out.println("str:" + str);
    }
}

