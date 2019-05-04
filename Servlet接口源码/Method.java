import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
public class Method {
	public static String getJsonString(String path){
		
//      String path = String.format("C:\\Users\\Administrator\\Desktop\\iTrip\\user\\%s_data.json",username);
        File file = new File(path);
        try {
            FileReader fileReader = new FileReader(file);
            Reader reader = new InputStreamReader(new FileInputStream(file),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            String jsonString = sb.toString();
            return jsonString;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
	public static void execPython(String file, String text){
		if( text == ""){
			text = "";
		}
	    //定义个获取结果的变量
	    String result="";
	    try {
	        //调用python，其中字符串数组对应的是python，python文件路径，向python传递的参数
	        System.out.println(text);
	        String strs= "C:\\Users\\刘凡\\AppData\\Local\\Programs\\Python\\Python37\\python.exe " + file + " " + text;
	        strs =new String(strs.getBytes("GBK"),"GBK");
	        System.out.println(strs);
	        //Runtime类封装了运行时的环境。每个 Java 应用程序都有一个 Runtime 类实例，使应用程序能够与其运行的环境相连接。
	        //一般不能实例化一个Runtime对象，应用程序也不能创建自己的 Runtime 类实例，但可以通过 getRuntime 方法获取当前Runtime运行时对象的引用。
	        // exec(String[] cmdarray) 在单独的进程中执行指定命令和变量。 
	        Process pr = Runtime.getRuntime().exec(strs);
	        //使用缓冲流接受程序返回的结果
	        System.out.println("run over");
	        BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream(),"GBK"));//注意格式
	        //定义一个接受python程序处理的返回结果
	        String line=" ";
	        while((line=in.readLine())!=null) {
	            //循环打印出运行的结果
	            result+=line+"\n";
	        }
	        //关闭in资源
	        in.close();
	        pr.waitFor();
	    }catch (Exception e) {
	        e.printStackTrace();
	    }
	    System.out.println("python传来的结果：");
	    //打印返回结果
	        System.out.println(result);
	        System.out.println("print over");
	    }
	public static void docmd(String file,String text) {
		if( text == ""){
			text = "";
		}
	    //定义个获取结果的变量
	    String result="";
		String[] command = { "cmd", };
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(command);
			new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
			new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
			PrintWriter stdin = new PrintWriter(p.getOutputStream());/** 以下可以输入自己想输入的cmd命令 */
				stdin.println("C:\\Users\\刘凡\\AppData\\Local\\Programs\\Python\\Python37\\python.exe "+ file +" "+text);//此处自行填写，切记有空格，跟cmd的执行语句一致。
				stdin.close();
		} catch (Exception e) {
			throw new RuntimeException("编译出现错误：" + e.getMessage());
		}
}
}
class SyncPipe implements Runnable {

	private final OutputStream ostrm_;
	private final InputStream istrm_;
	public SyncPipe(InputStream istrm, OutputStream ostrm) {
		istrm_ = istrm;
		ostrm_ = ostrm;
	}

public void run() {
	try {
		final byte[] buffer = new byte[1024];
		for (int length = 0; (length = istrm_.read(buffer)) != -1;) {
			ostrm_.write(buffer, 0, length);
			}
		} catch (Exception e) {
			throw new RuntimeException("处理命令出现错误：" + e.getMessage());
		}
}
}

