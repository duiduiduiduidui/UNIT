package WX_Servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import JDBCConnector.DataBaseDemo;
import BasicClass.Team;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;  
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSetMetaData;
import java.io.FileReader;
import java.util.Map;
import java.util.HashMap;
import java.io.Writer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Servlet implementation class WX_storeuserinfoServlet
 */
@WebServlet("/WX_agreeornotServlet")
//用来存储个人信息
public class WX_agreeornotServlet extends HttpServlet {
	
	private static List convertList(ResultSet rs,List list) throws SQLException{
		ResultSetMetaData md = rs.getMetaData();//获取键名
		int columnCount = md.getColumnCount();//获取行的数量
		while (rs.next()) {
		Map rowData = new HashMap();//声明Map
		for (int i = 1; i <= columnCount; i++) {
		rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
		}
		list.add(rowData);
		}
		return list;
		}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置请求编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        /* 设置响应头允许ajax跨域访问 */
        response.setHeader("Access-Control-Allow-Origin", "*");
        /* 星号表示所有的异域请求都可以接受， */
        response.setHeader("Access-Control-Allow-Method" + "s", "GET,POST");
        //获取微信小程序get的参数值并打印
        
       
        System.out.println("teamid="+request.getParameter("teamid"));
        //转成json数据
        String teamid,agree,app_openid;
        teamid = request.getParameter("teamid");
        agree = request.getParameter("agree");
        app_openid = request.getParameter("app_openid");
        String mes = "";
        List list = new ArrayList();
        
        	try{
        		Connection cc=DataBaseDemo.getConnection();
        	    if(!cc.isClosed())
        	    System.out.println("Succeeded connecting to the Database!");
        	    Statement statement = cc.createStatement();
        	    if(agree.equals("true")) {
	        	    String sql1 = "DELETE from application where user = '"+app_openid+"' and team ='"+teamid+"'";
	        	    statement.execute(sql1);
	        	    System.out.println(sql1);
	                String sql2 = "SELECT * from team,user where id ='"+teamid+"' and openid = '"+app_openid+"'";
	                System.out.println(sql2);
	                ResultSet rs = statement.executeQuery(sql2);
	                rs.next();
	        	    int num_now = rs.getInt("number_now");
	        	    int num_max = rs.getInt("number_max");
	        	    String nickname = rs.getString("nickname");
	        	    if(num_now < num_max) {
	        	    	num_now++;
	        	    	String sql3 = "Update team set number_now ="+num_now+" where id = '"+teamid+"'";
	        	    	statement.execute(sql3);
	        	    	System.out.println(sql3);
	        	    	String sql5 = "Insert into log \r\n" + 
	        		    		"(team,description,time) \r\n" + 
	        		    		"values('"+teamid+"','"+nickname+"加入了队伍！','"+TestDate.getdate()+"')";
	        	    	System.out.println(sql5);
	        	    	statement.execute(sql5);
	        	    	
	        	    	String sql4 = "Insert into user_team values('"+app_openid+"' , '"+teamid+"' , '1', 0)";
	        	    	statement.execute(sql4);
	        	    	System.out.println(sql4);
	        	    	
	        	    	mes = "succeed!";
	        	    }
	        	    else mes ="teamfull";
        	    }
        	    else {
                	mes="disagree";
                	String sql1 = "DELETE from application where user = '"+app_openid+"' and team ='"+teamid+"'";
            	    statement.execute(sql1);
            	    System.out.println(sql1);
                }
                
        	}
        	catch(SQLException e){
        		System.out.println(e);
        	}
        
        
        
        Map<String, Object> result = new HashMap<String, Object>();
        
        result.put("msg", mes);
        //使用Gson类需要导入gson-2.8.0.jar
       
        //String json1 = new Gson().toJson(result);
        Gson gson = new Gson();
		String json2 = gson.toJson(result);
        Writer out = response.getWriter();
        
        out.write(json2);
        out.flush();
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

