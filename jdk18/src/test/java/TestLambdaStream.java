import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * Created by nohi on 2018/4/20.
 */
public class TestLambdaStream {

    @Test
    public void testStream() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        // 直接打印
        list.forEach(System.out::println);
        System.out.println("=============");
        list.stream().filter((p) -> p > 2).forEach(x -> System.out.println(x));
        System.out.println("=============");
        // 取值分别操作
        list.forEach(i -> {
            System.out.println(i * 3);
        });
    }

    @Test
	public void learnStream1() {
		//首先，创建一个1-6乱序的List
		List<Integer> lists = new ArrayList<>();
		lists.add(4);
		lists.add(3);
		lists.add(6);
		lists.add(1);
		lists.add(5);
		lists.add(2);
		//看看List里面的数据是什么样子的先
		System.out.print("List里面的数据:");
		for (Integer elem : lists) System.out.print(elem + " ");// 4 3 6 1 5 2
		System.out.println();

		lists.stream().forEach(System.out::print);

		System.out.println();

		//最小值
		System.out.print("List中最小的值为:");
		Stream<Integer> stream = lists.stream();
		Optional<Integer> min = stream.min(Integer::compareTo);
		if (min.isPresent()) {
			System.out.println(min.get());//1
		}

		// 操作之后不能再操作
		//min = stream.max(Integer::compareTo);
		//System.out.println("max:" + min.get());

		//最大值
		System.out.print("List中最大的值为:");
		lists.stream().max(Integer::compareTo).ifPresent(System.out::println);//6

		//排序
		System.out.print("将List流进行排序:");
		Stream<Integer> sorted = lists.stream().sorted();
		sorted.forEach(elem -> System.out.print(elem + " "));// 1 2 3 4 5 6
		System.out.println();
		//过滤
		System.out.print("过滤List流，只剩下那些大于3的元素:");
		lists.stream()
				.filter(elem -> elem > 3)
				.forEach(elem -> System.out.print(elem + " "));// 4 5 6
		System.out.println();
		//过滤
		System.out.println("过滤List流，只剩下那些大于0并且小于4的元素:\n=====begin=====");
		lists.stream()
				.filter(elem -> elem > 0)
				.filter(elem -> elem < 4)
				.sorted(Integer::compareTo)
				.forEach(System.out::println);// 1 2 3
		System.out.println("=====end=====");
		//经过了前面的这么多流操作，我们再来看看List里面的值有没有发生什么改变
		System.out.print("原List里面的数据:");
		for (Integer elem : lists) System.out.print(elem + " ");// 4 3 6 1 5 2
	}

    @Test
	public void learnStream() {
		List<Integer> lists = new ArrayList<>();
		lists.add(1);
		lists.add(2);
		lists.add(3);
		lists.add(4);
		lists.add(5);
		lists.add(6);
		Optional<Integer> sum = lists.parallelStream().reduce((a, b) -> a + b);//这里把stream()换成了parallelStream（）
		if (sum.isPresent()) System.out.println("list的总和为:" + sum.get());//21

		//<====> lists.stream().reduce((a, b) -> a + b).ifPresent(System.out::println);
		Integer sum2 = lists.stream().reduce(0, (a, b) -> a + b);//21
		System.out.println("list的总和为:" + sum2);

		Optional<Integer> product = lists.stream().reduce((a, b) -> a * b);
		if (product.isPresent()) System.out.println("list的积为:" + product.get());//720

		Integer product2 = lists.parallelStream().reduce(1, (a, b) -> a * b);//这里把stream()换成了parallelStream（）
		System.out.println("list的积为:" + product2);//720
	}

}
