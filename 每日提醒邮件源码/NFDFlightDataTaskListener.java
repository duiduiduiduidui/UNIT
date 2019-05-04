package DailyEmail;

import javax.servlet.ServletContextEvent;

import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
@WebListener
public class NFDFlightDataTaskListener implements ServletContextListener {
  
 public void contextInitialized(ServletContextEvent sce) {
   new TimerManager();
   System.out.println("task over");
 }
  
 public void contextDestroyed(ServletContextEvent sce) {
  // TODO Auto-generated method stub
    
 } 
}