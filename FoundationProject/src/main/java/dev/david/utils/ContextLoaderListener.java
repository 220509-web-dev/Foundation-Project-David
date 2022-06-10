package dev.david.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.david.daos.UserDaoPostgres;
import dev.david.filters.CustomFilter;
import dev.david.services.UserService;
import dev.david.servlets.AuthServlet;
import dev.david.servlets.UserServlet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.time.LocalDateTime;
import java.util.EnumSet;

public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("[LOG] - Th servlet context was initialized at " + LocalDateTime.now());
        ObjectMapper mapper = new ObjectMapper();
        UserDaoPostgres userDAO = new UserDaoPostgres();
        UserService userService = new UserService(userDAO);

        UserServlet userServlet = new UserServlet(mapper, userService);

        AuthServlet authServlet = new AuthServlet(mapper, userDAO);

        ServletContext context = sce.getServletContext();

        CustomFilter customFilter = new CustomFilter();
        context.addFilter("CustomFilter", customFilter).addMappingForUrlPatterns(EnumSet.of(DispatcherType.INCLUDE), true, "/*");

        context.addServlet("UserServlet", userServlet).addMapping("/users/*");
        context.addServlet("AuthServlet", authServlet).addMapping("/auth");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[LOG] - Th servlet context was destroyed at " + LocalDateTime.now());
    }
}
