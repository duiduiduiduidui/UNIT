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
import java.util.Date; 
import java.util.Calendar; 

import java.text.SimpleDateFormat; 
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
@WebServlet("/WX_storeteaminfoServlet")
//用来存储个人信息
public class WX_storeteaminfoServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置请求编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        /* 设置响应头允许ajax跨域访问 */
        response.setHeader("Access-Control-Allow-Origin", "*");
        /* 星号表示所有的异域请求都可以接受， */
        response.setHeader("Access-Control-Allow-Method"
        		+ "s", "GET,POST");
        //获取微信小程序get的参数值并打印
        Date now = new Date(); 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式


        String hehe = dateFormat.format( now ); 
        System.out.println(hehe); 

        System.out.println("captain="+request.getParameter("captain"));
        System.out.println("name="+request.getParameter("name"));
        System.out.println("teamtype="+request.getParameter("teamtype"));
        System.out.println("teamtype_small="+request.getParameter("teamtype_small"));
        System.out.println("number_max="+request.getParameter("number_max"));
        System.out.println("description="+request.getParameter("description"));
        System.out.println("year="+request.getParameter("year"));
        System.out.println("month="+request.getParameter("month"));
        System.out.println("day="+request.getParameter("day"));
        //转成json数据
        String name, teamtype, teamtype_small,captain, number_max, description, date, year, month, day;
        name = request.getParameter("name");
        teamtype = request.getParameter("teamtype");
        teamtype_small = request.getParameter("teamtype_small");  
        captain = request.getParameter("captain");
        number_max = request.getParameter("number_max");
        description = request.getParameter("description");
        date = request.getParameter("date");
        year = date.split("-")[0];
        month = date.split("-")[1];
        day = date.split("-")[2];
        String state = "0";
        String is_full = "0";
        Team t = new Team(name,teamtype,teamtype_small,captain,Integer.parseInt(number_max),1,state,description,year,month,day,is_full);
        t.toString();
        List<Team> lists= new ArrayList<Team>();
        lists.add(t);
        Gson gson = new Gson();
		String json1 = gson.toJson(t);
		System.out.println(json1);
		String json2 = gson.toJson(lists);
		System.out.println(json2);
		//打印结果
        try{
    		Connection cc=DataBaseDemo.getConnection();
    	    if(!cc.isClosed())
    	    System.out.println("Succeeded connecting to the Database!");
    	    Statement statement = cc.createStatement();
    	    String sql = "SELECT Count(*) FROM TEAM WHERE captain = '" + captain +"' and name = '"+name+"'";
        	System.out.println(sql);
        	ResultSet rs = statement.executeQuery(sql);
            rs.next();
            int ct = rs.getInt("Count(*)");
            System.out.println(ct);
            String sql2,sql3;
            if(ct == 0){
            	System.out.println("不存在");
            	sql2 = "Insert Into team values('"+t.getid()+"','"+name+"','"+teamtype+"','"+teamtype_small+"','"+captain+"','"+number_max+"',1,0,'"+description+"','"+year+"','"+month+"','"+day+"',0)";
            	System.out.println(sql2);
    		    statement.execute(sql2);
    		    sql3 = "Insert Into user_team values('"+captain+"','"+t.getid()+"','1','0')";
    		    System.out.println("Succeed to insert");
    		    statement.execute(sql3);
    		    sql3 = "Insert into log \r\n" + 
    		    		"(team,description,time) \r\n" + 
    		    		"values('"+t.getid()+"','队伍已建立！','"+TestDate.getdate()+"')";
    		    System.out.println("Succeed to insert");
    		    statement.execute(sql3);
    		    
            }
            else{
            	System.out.println("存在,该用户创立过同名team，不应该再次创立");
//            	sql2 = "Update chibusi_daily set keshu='"+keshu+"' WHERE USERNAME='"+username+"' AND DATE='"+date+"' AND meal='"+meal+"'and food='"+food+"'";
//            	System.out.println(sql2);
//    		    statement.execute(sql2);
//    		    System.out.println("Succeed to update");
            }
            
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
        
        Writer out = response.getWriter();
        
        out.write(json1);
        out.flush();
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

