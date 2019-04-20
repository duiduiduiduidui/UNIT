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
@WebServlet("/WX_signedupServlet")
public class WX_signedupServlet extends HttpServlet {
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
        //转成json数据
        String openid;
        String SourceI="";
        openid = request.getParameter("openid");
        try{
    		Connection cc=DataBaseDemo.getConnection();
    	    if(!cc.isClosed())
    	    System.out.println("Succeeded connecting to the Database!");
    	    Statement statement = cc.createStatement();
    	    String sql = "SELECT Count(*) FROM USER WHERE openid = '" + openid +"'";
        	System.out.println(sql);
        	ResultSet rs = statement.executeQuery(sql);
            rs.next();
            int ct = rs.getInt("Count(*)");
            System.out.println(ct);
            String sql2;
            if(ct == 0){
            	System.out.println("不存在");
            	SourceI = "yes";
            }
            else{
            	System.out.println("该用户已注册过");
            	SourceI = "no";
            }
            
    	}
    	catch(SQLException e){
    		System.out.println(e);
    	}
//        Map<String, Object> result = new HashMap<String, Object>();
//        //JsonParser parse =new JsonParser();//创建json解析器
//		//JsonObject json=(JsonObject) parse.parse(new FileReader("C:\\Users\\Administrator\\Desktop\\iTrip\\data.json"));  //创建jsonObject对象
//       
//        result.put("msg", SourceI);
//        //使用Gson类需要导入gson-2.8.0.jar
//        
//        
//        String json1 = new Gson().toJson(result);
        String json1 = SourceI;
        Writer out = response.getWriter();
        System.out.println(SourceI);
        out.write(json1);
        out.flush();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

