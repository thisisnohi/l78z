import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Created by nohi on 2018/4/20.
 */
public class TestLambda {

	@Test
	public void test1() {
		List<Integer> list = new ArrayList<>();
		list.add( 1 );
		list.add( 2 );
		list.add( 3 );

		// 直接打印
		list.forEach( System.out::println );

		// 取值分别操作
		list.forEach( i -> {
			System.out.println( i * 3 );
		} );

	}

	@Test
	public void test2(){
		new Thread( () -> System.out.println("In Java8!") ).start();

		List<String> list = new ArrayList<>(  );
		list.add( "1" );
		list.add( "4" );
		list.add( "2" );
		list.add( "3" );
		System.out.println(list);
		list.sort( (a,b) -> a.compareTo( b ));

		System.out.println(list);
	}

	public void t(TestFunctionalInterface ti){
		ti.test( "a" , "b" );
	}

	@Test
	public void testFunctionalInterface(){
		t ( ( a, b ) -> System.out.println( a + "......"  + b ) ) ;

		t ( ImplFunctionalInterface :: test) ;
	}
}
