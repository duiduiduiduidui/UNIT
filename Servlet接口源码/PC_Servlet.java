
import java.io.Writer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class PC_Servlet
 */
@WebServlet("/PC_Servlet")
public class PC_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PC_Servlet() {
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
		String openid = request.getParameter("openid");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		String date = year + "-" + month + "-"+day;
		Method.execPython("D:\\working\\software_engineering\\UNIT\\Personal_Calendar.py",openid+" "+date+" 2");
		String path =  String.format("D:\\working\\software_engineering\\UNIT\\PC_json\\%s_initial.json",openid);
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
