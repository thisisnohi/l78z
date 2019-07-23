package nohi.think.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by nohi on 2018/7/29.
 */
@WebServlet(urlPatterns = "/test2")
public class TestServlet2 extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost( req, resp );
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType( "application/json" );
		resp.setCharacterEncoding( "UTF-8" );
		PrintWriter pw = resp.getWriter();
		pw.write( "执行TestServlet2..." );

		pw.close();
	}
}
