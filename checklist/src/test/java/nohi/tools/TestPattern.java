package nohi.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * Created by nohi on 2018/5/10.
 */
public class TestPattern {

	@Test
	public void test1(){
		Pattern p = Pattern.compile("Abc\\$?.*(?<!\\.java)$");
		Matcher m = p.matcher("Abc$1.aa");
		boolean b = m.matches();
		System.out.println(b);

		m = p.matcher("Abctmp.class");
		b = m.matches();
		System.out.println(b);

		System.out.println("==============");

		p = Pattern.compile("Abc\\$?.*(\\.class)$");
		m = p.matcher("Abc$1.class");
		b = m.matches();
		System.out.println(b);

		System.out.println("=====================");
		p = Pattern.compile("^Abc(\\$.*)?(\\.class)$");
		m = p.matcher("Abc.class");
		b = m.matches();
		System.out.println(b);
		m = p.matcher("Abc123123.class");
		b = m.matches();
		System.out.println(b);
		m = p.matcher("Abc$1.class");
		b = m.matches();
		System.out.println(b);

		m = p.matcher("Abcaa$1.class");
		b = m.matches();
		System.out.println(b);

		m = p.matcher("AbcAbc$1.class");
		b = m.matches();
		System.out.println(b);
	}
}
