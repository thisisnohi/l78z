import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Created by nohi on 2018/4/20.
 */
public class TestLambdaStream {

	@Test
	public void testStream() {
		List<Integer> list = new ArrayList<>();
		list.add( 1 );
		list.add( 2 );
		list.add( 3 );

		// 直接打印
		list.forEach( System.out::println );
		System.out.println("=============");
		list.stream().filter( (p) -> p > 2 ).forEach( x -> System.out.println(x) );
		System.out.println("=============");
		// 取值分别操作
		list.forEach( i -> {
			System.out.println( i * 3 );
		} );



}
}
