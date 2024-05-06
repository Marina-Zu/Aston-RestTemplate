package org.example;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class PostServiceApp {
    private static final int PORT = 8081;
    public static void main(String[] args) throws LifecycleException {
//        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
//            System.out.println("Application context loaded successfully");
//        } catch (Exception e) {
//            System.err.println("Failed to load application context: " + e.getMessage());
//        }

        Tomcat tomcat = new Tomcat();

        tomcat.getConnector().setPort(PORT);

        Context context = tomcat.addContext("", null);

       // SessionFactory sf = new Configuration().configure().buildSessionFactory();

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.setServletContext(context.getServletContext());
        applicationContext.scan("org.example");
        applicationContext.refresh();

        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
        Wrapper wrapper = Tomcat.addServlet(context, "dispatcher", dispatcherServlet);
        wrapper.addMapping("/");
        wrapper.setLoadOnStartup(1);

        tomcat.start();
    }

}
