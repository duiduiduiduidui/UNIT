package WX_Servlet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Calendar;
import JDBCConnector.DataBaseDemo;
public class sendMail {
	public static List getmailinfo() throws SQLException{
		List<Map<String, Object>> maillist = new ArrayList<Map<String, Object>>();
		List list = new ArrayList();
        
		Connection cc=DataBaseDemo.getConnection();
	    if(!cc.isClosed())
	    System.out.println("Succeeded connecting to the Database!");
	    Statement statement = cc.createStatement();
	    
    	String sql = "SELECT * FROM USER ";
    	System.out.println(sql);
        ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
		list.add(rs.getObject(1));
		list.add(rs.getObject(2));
		list.add(rs.getObject(3));
		}
		List listofmission = new ArrayList();
		String openid1,sql1,sql2,nickname,mail;
		int y,m,d,h;    
		Calendar cal=Calendar.getInstance();    
		y=cal.get(Calendar.YEAR);    
		m=cal.get(Calendar.MONTH);    
		d=cal.get(Calendar.DATE)+1;  
		System.out.println("明天是"+y+"年"+m+"月"+d+"日");  
		for(int i=0;i<list.size();i+=3) {
			openid1 = list.get(i).toString();
			nickname = list.get(i+1).toString();
			mail = list.get(i+2).toString();
			sql1 = "Select Count(*) from mission where member ='"+openid1+"' and state = '0' and year='"+y+"' and "
					+ "month = '"+m+"' and day = '"+d+"'";
			System.out.println(sql1);
			sql2 = "Select Count(*) from mission where member = '"+openid1+"' and state = '0' and is_overdue='1'";
			System.out.println(sql2);
			rs = statement.executeQuery(sql1);
            rs.next();
            int ct = rs.getInt("Count(*)");
            rs = statement.executeQuery(sql2);
            rs.next();
            int ct2 = rs.getInt("Count(*)");
            System.out.println("openid = "+openid1+" ,email = "+mail+" ddl = "+ct+" ,overdue = "+ct2);
            Map<String, Object> rowData = new HashMap<String, Object>();
            rowData.put("nickname", nickname);
            rowData.put("mail", mail);
            rowData.put("ddl", ct);
            rowData.put("overdue", ct2);
            maillist.add(rowData);
            
		}
		for (Map<String, Object> map : maillist) {
			System.out.print(map.get("nickname"));
			System.out.println(map.get("namil"));
	}
		for (int i = 0; i < maillist.size(); i++) {
            System.out.print(maillist.get(i)+" ");

       }    
            
		return maillist;
		
	}
	public static void send(String email,String nickname,int ddl,int overdue) throws AddressException, MessagingException {
		Properties props = new Properties();
        //获取163邮箱smtp服务器的地址，
        props.setProperty("mail.host", "smtp.163.com");
        //是否进行权限验证。
        props.setProperty("mail.smtp.auth", "true");
        
        
        //0.2确定权限（账号和密码）
        Authenticator authenticator = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                //填写自己的163邮箱的登录帐号和授权密码，授权密码的获取，在后面会进行讲解。
                return new PasswordAuthentication("you_jz@163.com","Ayou197404UNIT");
            }
        };

        //1 获得连接
        /**
         * props：包含配置信息的对象，Properties类型
         *         配置邮箱服务器地址、配置是否进行权限验证(帐号密码验证)等
         * 
         * authenticator：确定权限(帐号和密码)        
         * 
         * 所以就要在上面构建这两个对象。
         */
        
        Session session = Session.getDefaultInstance(props, authenticator);

        
        //2 创建消息
        Message message = new MimeMessage(session);
        // 2.1 发件人        xxx@163.com 我们自己的邮箱地址，就是名称
        message.setFrom(new InternetAddress("you_jz@163.com"));
        /**
         * 2.2 收件人 
         *         第一个参数：
         *             RecipientType.TO    代表收件人 
         *             RecipientType.CC    抄送
         *             RecipientType.BCC    暗送
         *         比如A要给B发邮件，但是A觉得有必要给要让C也看看其内容，就在给B发邮件时，
         *         将邮件内容抄送给C，那么C也能看到其内容了，但是B也能知道A给C抄送过该封邮件
         *         而如果是暗送(密送)给C的话，那么B就不知道A给C发送过该封邮件。
         *     第二个参数
         *         收件人的地址，或者是一个Address[]，用来装抄送或者暗送人的名单。或者用来群发。可以是相同邮箱服务器的，也可以是不同的
         *         这里我们发送给我们的qq邮箱
         */
        message.setRecipient(RecipientType.TO, new InternetAddress(email));
        // 2.3 主题（标题）
        message.setSubject("这是一个四点自动发送的邮件");
        // 2.4 正文
        String str ;
        if (ddl!=0 && overdue !=0) {
        	str =nickname +
                        "，您好，您明天有+"+ddl+"个DDL哦！<br/>" +
                        "请抓紧完成<br/>" +
                        "P.S.<br/>" +
                        "您有+"+overdue+"个任务已超时<br/>"+
                        "请登陆UNIT查看任务情况";
        }
        else if(ddl==0 && overdue!=0) {
        	str =nickname +
                    "，你好！<br/>"+
                    "你有+"+overdue+"个任务已超时<br/>"+
                    "请登陆UNIT查看任务情况";
        }
        else if(ddl!=0 && overdue ==0) {
        	str =nickname +
                    "，您好，您明天有+"+ddl+"个DDL哦！<br/>" +
                    "请抓紧完成<br/>" +
                    "" +
                    "（无超时任务）<br/>"+
                    "请登陆UNIT查看任务情况";
        }
        else {
        	str =nickname +
                    "，您好，您明天没有DDL哦！<br/>" +
                    "" +
                    "（无超时任务）<br/>"+
                    "请登陆UNIT查看任务情况";
        }
        
        //设置编码，防止发送的内容中文乱码。
        message.setContent(str, "text/html;charset=UTF-8");
        
        
        //3发送消息
        Transport.send(message);
        System.out.println("发送成功");
	}
    public static void main(String[] args) throws Exception{
        List<Map<String,Object>> list = getmailinfo();
        String mail,nickname,ddl,overdue;
        int ddl_int,overdue_int;
        for (Map<String, Object> map : list) {
        	mail = map.get("mail").toString();
        	nickname = map.get("nickname").toString();
        	ddl = map.get("ddl").toString();
        	overdue = map.get("overdue").toString();
        	ddl_int = Integer.parseInt(ddl);
        	overdue_int = Integer.parseInt(overdue);
        	send(mail,nickname,ddl_int,overdue_int);
       }
        
    }
}