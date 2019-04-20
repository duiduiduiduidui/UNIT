package WX_Servlet;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.net.URLConnection;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject; 
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
/**
 * Servlet implementation class WX_GetOpenidServlet
 */
@WebServlet("/WX_GetOpenidServlet")
public class WX_GetOpenidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WX_GetOpenidServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    public String getopenid(String appid,String code,String secret) {  
        BufferedReader in = null;  
        //appid和secret是开发者分别是小程序ID和小程序密钥，开发者通过微信公众平台-》设置-》开发设置就可以直接获取，
        String url="https://api.weixin.qq.com/sns/jscode2session?appid="
        +appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
        try {  
			URL weChatUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection connection = weChatUrl.openConnection();  
            // 设置通用的请求属性  
            connection.setConnectTimeout(5000);  
            connection.setReadTimeout(5000);  
            // 建立实际的连接  
            connection.connect();  
            // 定义 BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));  
            StringBuffer sb = new StringBuffer();  
            String line;  
            while ((line = in.readLine()) != null) {  
                sb.append(line);  
            }  
            return sb.toString();  
        } catch (Exception e1) {  
        	throw new RuntimeException(e1);
        }  
        // 使用finally块来关闭输入流  
        finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (Exception e2) {  
                e2.printStackTrace();  
            }  
        }  
    } 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        /* 设置响应头允许ajax跨域访问 */
        response.setHeader("Access-Control-Allow-Origin", "*");
        /* 星号表示所有的异域请求都可以接受， */
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		
		/*
		 * StringBuilder sb = new StringBuilder(
		 * "https://api.weixin.qq.com/sns/jscode2session?"); Map<String, String> params
		 * = new HashMap<String, String>(); params.put("appid",
		 * request.getParameter("appid"));
		 * params.put("secret",request.getParameter("secret"));//固定值
		 * params.put("js_code", request.getParameter("js_code"));//固定值
		 * params.put("grand_type", "authorization_code"); String result1 =
		 * GetPostUrl(sb.toString(), params, "GET");
		 * System.out.println(result1+"aaaaa");
		 */
		
	    
	  
	  //调用访问微信服务器工具方法，传入三个参数获取带有openid、session_key的json字符串
	  String jsonId=getopenid(request.getParameter("appid"),request.getParameter("js_code"),request.getParameter("secret"));
	  System.out.println(jsonId);
	  Writer out = response.getWriter();
      out.write(jsonId);
      //out.write(result);
      out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	 public static String GetPostUrl(String sendUrl, Map<String, String> params, String sendType) {
	        URL url = null;
	        HttpURLConnection httpurlconnection = null;

	        try {
	            // 构建请求参数
	            StringBuffer paramSb = new StringBuffer();
	            if (params != null) {
	                for (java.util.Map.Entry<String, String> e : params.entrySet()) {
	                    paramSb.append(e.getKey());
	                    paramSb.append("=");
	                    // 将参数值urlEncode编码,防止传递中乱码
	                    paramSb.append(URLEncoder.encode(e.getValue(), "UTF-8"));
	                    paramSb.append("&");
	                }
	                paramSb.substring(0, paramSb.length() - 1);
	                System.out.println(paramSb.toString());
	            }
	            url = new URL(sendUrl + "?" + paramSb.toString());
	            httpurlconnection = (HttpURLConnection) url.openConnection();
	            httpurlconnection.setRequestMethod("GET");
	            httpurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	            httpurlconnection.setDoInput(true);
	            httpurlconnection.setDoOutput(true);

	            // 设置http请求超时时间30000毫秒（30秒）
	            httpurlconnection.setConnectTimeout(30000);
	            httpurlconnection.setReadTimeout(30000);
	            httpurlconnection.setUseCaches(true);
	            /*
	             * if (submitMethod.equalsIgnoreCase("POST")) {
	             * httpurlconnection.getOutputStream().write(postData.getBytes("GBK"
	             * )); httpurlconnection.getOutputStream().flush();
	             * httpurlconnection.getOutputStream().close(); }
	             */

	            int code = httpurlconnection.getResponseCode();
	            if (code == 200) {
	                DataInputStream in = new DataInputStream(httpurlconnection.getInputStream());
	                int len = in.available();
	                byte[] by = new byte[len];
	                in.readFully(by);
	                String rev = new String(by, "UTF-8");

	                in.close();

	                return rev;
	            } else {
	                // http 请求返回非 200状态时处理
	                return "<?xml version=\"1.0\" encoding=\"utf-8\" ?><error>发送第三方请求失败</error>";
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (httpurlconnection != null) {
	                httpurlconnection.disconnect();
	            }
	        }
	        return null;
	    }


	

}
