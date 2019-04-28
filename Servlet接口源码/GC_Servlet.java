

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GC_Servlet
 */
@WebServlet("/GC_Servlet")
public class GC_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GC_Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
		String team_id = request.getParameter("team_id");
		String openid = request.getParameter("openid");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		String date = year + "-" + month + "-"+day;
		Method.execPython("D:\\working\\software_engineering\\UNIT\\Group_Calendar.py ", openid+ " " + team_id + " "+ date);
		String path =  String.format("D:\\working\\software_engineering\\UNIT\\GC_json\\%s.json",openid);
		String data = Method.getJsonString(path);
		System.out.println(data);
        Writer out = response.getWriter();
        out.write(data);
        //out.write(result);
        out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
