package _05_stream;

import org.junit.Test;

import java.util.Random;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-03-25 13:04
 **/
public class _01_Stram {

    @Test
    public void testStream(){
        Random random = new Random();
        random.ints().forEach(System.out::println);
    }
}
