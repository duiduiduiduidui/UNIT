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
@WebServlet("/WX_proposeServlet")
//用来存储个人信息
public class WX_proposeServlet extends HttpServlet {
	
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
        
       
        System.out.println("openid="+request.getParameter("openid"));
        //转成json数据
        String openid,mission;
        openid = request.getParameter("openid");
        mission = request.getParameter("mission");
        List list = new ArrayList();
        try{
    		Connection cc=DataBaseDemo.getConnection();
    	    if(!cc.isClosed())
    	    System.out.println("Succeeded connecting to the Database!");
    	    Statement statement = cc.createStatement();
    	    String sql1 = "SELECT captain,team FROM MISSION \r\n" + 
    	    		"LEFT JOIN TEAM ON(team.id=mission.team )\r\n" + 
    	    		"WHERE mission.id = '"+mission+"' ";
    	    System.out.println(sql1);
    	    ResultSet rs = statement.executeQuery(sql1);
    	    String cap="",team="";
            while(rs.next()) {
            cap = rs.getString("captain");
            team = rs.getString("team");}
            String state, proposed;
            if(cap.equals(openid)) {
            	state = "1";
            	proposed ="0";
            }
            else {
            	state = "0";
            	proposed = "1";
            }
            String sql2 = "Update mission set state = '"+state+"' , is_proposed ='"+proposed+"' where id ="+mission+";";
            System.out.println(sql2);
            statement.execute(sql2);
            String sql3 = "SELECT nickname,name from mission left join user on(mission.member = user.openid) where id = '"+mission+"'";
            System.out.println(sql3);
            rs = statement.executeQuery(sql3);
            String nickname="",name ="";
            while(rs.next()) {
            nickname = rs.getString("nickname");
            name = rs.getString("name");}
            String sqllog;
            if(state.equals("0")) {
            	sqllog = "Insert into log \r\n" + 
    		    		"(team,description,time) \r\n" + 
    		    		"values('"+team+"','"+nickname+"提交了任务\""+name+"\"','"+TestDate.getdate()+"')";
            	System.out.println(sqllog);
            } 
            else {
            	sqllog ="Insert into log \r\n" + 
    		    		"(team,description,time) \r\n" + 
    		    		"values('"+team+"','"+nickname+"已完成任务\""+name+"\"','"+TestDate.getdate()+"')";
            	System.out.println(sqllog);
            }
            statement.execute(sqllog);
    	    String sql = "SELECT * FROM MISSION \r\n" + 
    	    		"LEFT JOIN USER_TEAM ON(user_team.team=mission.team and user_team.user = mission.member)WHERE member = '" + openid +"' and state = '0' and is_proposed = '0'"
    	    				+ "order by year,month,day";
        	System.out.println(sql);
        	rs = statement.executeQuery(sql);
        	list=convertList(rs,list);
        	sql = "SELECT * FROM MISSION \r\n" + 
        			"LEFT JOIN USER_TEAM ON(user_team.team=mission.team and user_team.user = mission.member)WHERE member = '" + openid +"' and state = '0' and is_proposed = '1'"
        					+ "order by year,month,day";
        	System.out.println(sql);
        	rs = statement.executeQuery(sql);
        	list=convertList(rs,list);
            
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

