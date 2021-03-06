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
@WebServlet("/WX_getmyteamServlet")
//用来存储个人信息
public class WX_getmyteamServlet extends HttpServlet {
	
	private static List convertList(ResultSet rs,String openid) throws SQLException{
		List list = new ArrayList();
		ResultSetMetaData md = rs.getMetaData();//获取键名
		int columnCount = md.getColumnCount();//获取行的数量
		while (rs.next()) {
		Map rowData = new HashMap();//声明Map
		for (int i = 1; i <= columnCount; i++) {
		rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
		String columnname = md.getColumnName(i);
		String teamtype_smalll;
		if(columnname.matches("teamtype_small")) {
			if(rs.getObject(i).toString().equals("数学建模")) {
				teamtype_smalll = "1";
			}
			else if(rs.getObject(i).toString().equals("计算机大赛")) {
				teamtype_smalll = "2";
			}
			else if(rs.getObject(i).toString().equals("商业分析")) {
				teamtype_smalll = "3";
			}
			else if(rs.getObject(i).toString().equals("大创竞赛")) {
				teamtype_smalll = "4";
			}
			else if(rs.getObject(i).toString().equals("约饭")) {
				teamtype_smalll = "5";
			}
			else if(rs.getObject(i).toString().equals("约球")) {
				teamtype_smalll = "6";
			}
			else {;teamtype_smalll = "0";}
			
			rowData.put("teamtype_smalll", teamtype_smalll);
		}
		}
		if(rs.getObject("captain").toString().equals(openid)) {
			rowData.put("is_captain",1);
		}
		else {
			rowData.put("is_captain",0);
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
        
       
        System.out.println("openid="+request.getParameter("openid"));
        //转成json数据
        String openid;
        openid = request.getParameter("openid");
        List list = new ArrayList();
        try{
    		Connection cc=DataBaseDemo.getConnection();
    	    if(!cc.isClosed())
    	    System.out.println("Succeeded connecting to the Database!");
    	    Statement statement = cc.createStatement();
    	    
    	    
		    String sql = "SELECT DISTINCT id,name,photourl,teamtype,teamtype_small,captain,number_max,number_now,description,year,month,day FROM TEAM RIGHT OUTER JOIN USER_TEAM ON(team.captain= user_team.user ) \r\n" + 
	    			"LEFT outer JOIN USER on(user_team.user=user.openid) where state='0' and user ='"+openid+"'";
	        System.out.println(sql);
	    	ResultSet rs = statement.executeQuery(sql);
	    	list=convertList(rs,openid);
    	    
        }
    	catch(SQLException e){
    		System.out.println(e);
    	}
        //Map<String, Object> result = new HashMap<String, Object>();
        //JsonParser parse =new JsonParser();//创建json解析器
		//JsonObject json=(JsonObject) parse.parse(new FileReader("C:\\Users\\Administrator\\Desktop\\iTrip\\data.json"));  //创建jsonObject对象
        
        //result.put("msg", "用户信息存储成功");
        //使用Gson类需要导入gson-2.8.0.jar
       
        //String json1 = new Gson().toJson(result);
        Gson gson = new Gson();
		String json2 = gson.toJson(list);
        Writer out = response.getWriter();
        
        out.write(json2);
        out.flush();
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

