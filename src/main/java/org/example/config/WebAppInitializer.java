//package org.example.config;
//
//import jakarta.servlet.ServletContext;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRegistration;
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.servlet.DispatcherServlet;
//
//public class WebAppInitializer implements WebApplicationInitializer {
//
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//
//        // Регистрируем конфигурационный класс
//        context.register(AppConfig.class);
//        context.setServletContext(servletContext);
//        context.refresh(); // Важно: обязательно вызывать refresh!
//
//        // Создаем и регистрируем DispatcherServlet
//        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
//        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", dispatcherServlet);
//        registration.setLoadOnStartup(1);
//        registration.addMapping("/");
//    }
//}