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
@WebServlet("/WX_assessServlet")
//用来存储个人信息
public class WX_assessServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置请求编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        /* 设置响应头允许ajax跨域访问 */
        response.setHeader("Access-Control-Allow-Origin", "*");
        /* 星号表示所有的异域请求都可以接受， */
        response.setHeader("Access-Control-Allow-Method" + "s", "GET,POST");
        //获取微信小程序get的参数值并打印
        

        //转成json数据
        String openid_all,score1_all,score2_all,score3_all;
        openid_all = request.getParameter("openid_all");
        score1_all = request.getParameter("score1_all");
        score2_all = request.getParameter("score2_all");
        score3_all = request.getParameter("score3_all");
        String openid,score1,score2,score3;
        String[] openidarray = openid_all.split(" ");
        String[] score1array = score1_all.split(" ");
        String[] score2array = score2_all.split(" ");
        String[] score3array = score3_all.split(" ");
        
    	try{
    		Connection cc=DataBaseDemo.getConnection();
    	    if(!cc.isClosed())
    	    System.out.println("Succeeded connecting to the Database!");
    	    Statement statement = cc.createStatement();
    	    for(int i = 0; i < openidarray.length ; i++) {
		    	String sql1 = "SELECT * FROM USER where openid='+"+openidarray[i]+"')";
		    	System.out.println(sql1);
		        ResultSet rs = statement.executeQuery(sql1);
		        rs.next();
		        double oscore1 = rs.getDouble("tech");
		        double oscore2 = rs.getDouble("team_sprit");
		        double oscore3 = rs.getDouble("time_keeping");
		        score1 = score1array[i];
		        score2 = score2array[i];
		        score3 = score3array[i];
		        oscore1 = 0.9*oscore1+0.1*Double.parseDouble(score1);
		        oscore2 = 0.9*oscore2+0.1*Double.parseDouble(score2);
		        oscore3 = 0.9*oscore3+0.1*Double.parseDouble(score3);
		        String sql2 = "update user set tech ='"+oscore1+"' and team_sprit = '"+oscore2 +"' and time_keeping = '"+
		        oscore3+"' where openid = '"+openidarray[i]+"'";
		        statement.execute(sql2);
    	    }
    	}
    	catch(SQLException e){
    		System.out.println(e);
    	}
       
        
        Map<String, Object> result = new HashMap<String, Object>();
        //JsonParser parse =new JsonParser();//创建json解析器
		//JsonObject json=(JsonObject) parse.parse(new FileReader("C:\\Users\\Administrator\\Desktop\\iTrip\\data.json"));  //创建jsonObject对象
        
        result.put("msg", "评分修改成功");
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

