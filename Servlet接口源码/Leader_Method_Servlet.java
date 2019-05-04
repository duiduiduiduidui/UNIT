

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import JDBCConnector.DataBaseDemo;

/**
 * Servlet implementation class Leader_Method_Servlet
 */
@WebServlet("/Leader_Method_Servlet")
public class Leader_Method_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Leader_Method_Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        try{
		response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        String team_id = request.getParameter("team_id");
        String openid = request.getParameter("openid");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String day = request.getParameter("day");
		String date = year + "-" + month + "-"+day;
    	Connection cc=DataBaseDemo.getConnection();
    	Statement statement = cc.createStatement();
//		lable标识增删改查哪步操作
//		1:增加   2::删除   3:修改 
		String lable = request.getParameter("lable");
		System.out.println(lable);
		if (lable == "1"){
			String mission_name = "哈哈哈";
			String sql = String.format("INSERT INTO MISSION(TEAM,MEMBER,YEAR,MONTH,DAY,NAME) VALUES('%s','%s','%s','%s','%s','%s')",team_id,openid,year,month,day,mission_name);
		    statement.execute(sql);
		    System.out.println("Succeed to insert");
		}
		else if (lable == "2"){
			String mission_id = "12";
			String sql = String.format("DELETE FROM MISSSION WHERE MISSION_ID = '%s", mission_id);
			statement.executeQuery(sql);
			System.out.println("Succeed to delete");
		}
		else if(lable == "3"){
			String mission_id = "12";
			String mission_name = "哈哈哈";
			String sql = String.format("UPDATE MISSION SET MEMBER = '%s', YEAR = '%s',MONTH = '%s',DAY = '%s', NAME = '%s'  WHERE ID = '%s'", openid,year,month,day,mission_name,mission_id);
			statement.executeQuery(sql);
			System.out.println("Succeed to update");
		}
	}
        catch (SQLException e){
        	System.out.println(e);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
