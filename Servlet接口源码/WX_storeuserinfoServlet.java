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
@WebServlet("/WX_storeuserinfoServlet")
//�����洢������Ϣ
public class WX_storeuserinfoServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //�����������
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        /* ������Ӧͷ����ajax������� */
        response.setHeader("Access-Control-Allow-Origin", "*");
        /* �Ǻű�ʾ���е��������󶼿��Խ��ܣ� */
        response.setHeader("Access-Control-Allow-Method"
        		+ "s", "GET,POST");
        //��ȡ΢��С����get�Ĳ���ֵ����ӡ
        
        
        System.out.println("openid="+request.getParameter("openid"));
        System.out.println("email="+request.getParameter("email"));
        System.out.println("neckname="+request.getParameter("neckname"));
        System.out.println("label="+request.getParameter("label"));
        System.out.println("grade="+request.getParameter("grade"));
        System.out.println("url="+request.getParameter("url"));
        //ת��json����
        String openid, email, nickname, label, grade, photourl;
        openid = request.getParameter("openid");
        nickname = request.getParameter("nickname");
        email = request.getParameter("email");      
        label = request.getParameter("label");
        grade = request.getParameter("grade");
        photourl = request.getParameter("photourl");
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
            	System.out.println("������");
            	sql2 = "Insert Into user values('"+openid +"','"+nickname+"','"+email+"','"+grade+"','"+label+"','"+photourl+"',6000,4,4,4 )";
            	System.out.println(sql2);
    		    statement.execute(sql2);
    		    System.out.println("Succeed to insert");
            }
            else{
            	System.out.println("����,���û���ע�������Ӧ�ý����������");
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
        //JsonParser parse =new JsonParser();//����json������
		//JsonObject json=(JsonObject) parse.parse(new FileReader("C:\\Users\\Administrator\\Desktop\\iTrip\\data.json"));  //����jsonObject����
        
        //result.put("msg", "�û���Ϣ�洢�ɹ�");
        //ʹ��Gson����Ҫ����gson-2.8.0.jar
       
        //String json1 = new Gson().toJson(result);
        String json1 = "succeed insert!";
        Writer out = response.getWriter();
        
        out.write(json1);
        out.flush();
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

