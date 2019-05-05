package WX_Servlet;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import JDBCConnector.DataBaseDemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;  
import java.util.ArrayList;
import java.util.List;

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
@WebServlet("/WX_applyServlet")
public class WX_applyServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置请求编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        /* 设置响应头允许ajax跨域访问 */
        response.setHeader("Access-Control-Allow-Origin", "*");
        /* 星号表示所有的异域请求都可以接受， */
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        //获取微信小程序get的参数值并打印
        
        
        System.out.println("openid="+request.getParameter("openid"));
        System.out.println("team="+request.getParameter("team"));
        System.out.println("info="+request.getParameter("info"));
        //转成json数据
        String openid, team,info;
        openid = request.getParameter("openid");
        team = request.getParameter("team");
        info = request.getParameter("info");
        try{
    		Connection cc=DataBaseDemo.getConnection();
    	    if(!cc.isClosed())
    	    System.out.println("Succeeded connecting to the Database!");
    	    Statement statement = cc.createStatement();
    	    String sql = "SELECT Count(*) FROM APPLICATION WHERE openid = '" + openid +"' and team='"+team+"'";
        	System.out.println(sql);
        	ResultSet rs = statement.executeQuery(sql);
            rs.next();
            int ct = rs.getInt("Count(*)");
            System.out.println(ct);
            String sql2;
            if(ct <= 3){
            	System.out.println("申请加入该队伍不超过3次");
            	sql2 = "Insert Into application values('"+openid +"','"+team+"','"+info+"'";
            	System.out.println(sql2);
    		    statement.execute(sql2);
    		    System.out.println("Succeed to insert");
            }
            else{
            	System.out.println("存在,该用户已申请过3次，不应该继续申请");
//            	sql2 = "Update chibusi_daily set keshu='"+keshu+"' WHERE USERNAME='"+username+"' AND DATE='"+date+"' AND meal='"+meal+"'and food='"+food+"'";
//            	System.out.println(sql2);
//    		    statement.execute(sql2);
//    		    System.out.println("Succeed to update");
            }
            
    	}
    	catch(SQLException e){
    		System.out.println(e);
    	}
        Map<String, Object> result = new HashMap<String, Object>();
        //JsonParser parse =new JsonParser();//创建json解析器
		//JsonObject json=(JsonObject) parse.parse(new FileReader("C:\\Users\\Administrator\\Desktop\\iTrip\\data.json"));  //创建jsonObject对象
        
        result.put("msg", "用户信息存储成功");
        //使用Gson类需要导入gson-2.8.0.jar
        
        
        String json1 = new Gson().toJson(result);
        
        Writer out = response.getWriter();
        
        out.write(json1);
        out.flush();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

