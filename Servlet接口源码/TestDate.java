package WX_Servlet;
import java.util.Date; 
import java.util.Calendar; 

import java.text.SimpleDateFormat; 

public class TestDate{ 
	public static String getdate(){ 
	Date now = new Date(); 
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
	
	
	String hehe = dateFormat.format( now ); 
	System.out.println(hehe); 
	
	return hehe;

	} 
}