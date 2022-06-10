package dev.david.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.david.daos.UserDaoPostgres;
import dev.david.entities.Users;
import dev.david.exceptions.EmailNotAvailableException;
import dev.david.exceptions.InvalidRequestException;
import dev.david.exceptions.UserNotFoundException;
import dev.david.exceptions.UsernameNotAvailableException;
import dev.david.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class UserServlet extends HttpServlet {

    private final ObjectMapper mapper;
    private final UserService userService;

    public UserServlet(ObjectMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("[LOG] - User Servlet received a request at " + LocalDateTime.now());

        resp.setContentType("application/json");
        String userId = req.getParameter("id");
        String username = req.getParameter("username");
        if(userId != null){
            try{
                resp.setStatus(201);
                resp.getWriter().write(mapper.writeValueAsString(userService.getUserById(Integer.parseInt(userId))));
            } catch (UserNotFoundException e){
                resp.setStatus(400);

                HashMap<String, Object> errorMsg = new HashMap<>();
                errorMsg.put("code", 400);
                errorMsg.put("message", e.getMessage());
                errorMsg.put("timestamp", LocalDateTime.now().toString());

                resp.getWriter().write(mapper.writeValueAsString(errorMsg));
            } catch(Exception e){
                resp.setStatus(500);

                HashMap<String, Object> errorMsg = new HashMap<>();
                errorMsg.put("code", 500);
                errorMsg.put("message", "Internal error");
                errorMsg.put("timestamp", LocalDateTime.now().toString());

                resp.getWriter().write(mapper.writeValueAsString(errorMsg));
            }
        }
        if(username != null){
            try{
                resp.setStatus(201);
                resp.getWriter().write(mapper.writeValueAsString(userService.getUserByUsername(username)));
            } catch (UserNotFoundException e){
                resp.setStatus(400);

                HashMap<String, Object> errorMsg = new HashMap<>();
                errorMsg.put("code", 400);
                errorMsg.put("message", e.getMessage());
                errorMsg.put("timestamp", LocalDateTime.now().toString());

                resp.getWriter().write(mapper.writeValueAsString(errorMsg));
            } catch(Exception e){
                resp.setStatus(500);

                HashMap<String, Object> errorMsg = new HashMap<>();
                errorMsg.put("code", 500);
                errorMsg.put("message", "Internal error");
                errorMsg.put("timestamp", LocalDateTime.now().toString());

                resp.getWriter().write(mapper.writeValueAsString(errorMsg));
            }
        }
        if(username == null && userId == null){
            try{
                resp.setStatus(201);
                resp.getWriter().write(mapper.writeValueAsString(userService.getAllUsers()));
            } catch(Exception e){
                resp.setStatus(500);

                HashMap<String, Object> errorMsg = new HashMap<>();
                errorMsg.put("code", 500);
                errorMsg.put("message", "Internal error");
                errorMsg.put("timestamp", LocalDateTime.now().toString());

                resp.getWriter().write(mapper.writeValueAsString(errorMsg));
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("[LOG] - User Servlet received a request at " + LocalDateTime.now());

        resp.setContentType("application/json");

        try {
            Users newUser = mapper.readValue(req.getInputStream(), Users.class);
            userService.createNewUser(newUser);
            resp.setStatus(201);
            resp.getWriter().write(mapper.writeValueAsString(newUser));
        } catch(InvalidRequestException e){
            resp.setStatus(400);

            HashMap<String, Object> errorMsg = new HashMap<>();
            errorMsg.put("code", 400);
            errorMsg.put("message", e.getMessage());
            errorMsg.put("timestamp", LocalDateTime.now().toString());

            resp.getWriter().write(mapper.writeValueAsString(errorMsg));
        }catch(UsernameNotAvailableException e){
            resp.setStatus(500);

            HashMap<String, Object> errorMsg = new HashMap<>();
            errorMsg.put("code", 400);
            errorMsg.put("message", e.getMessage());
            errorMsg.put("timestamp", LocalDateTime.now().toString());

            resp.getWriter().write(mapper.writeValueAsString(errorMsg));
        } catch(EmailNotAvailableException e){
            resp.setStatus(500);

            HashMap<String, Object> errorMsg = new HashMap<>();
            errorMsg.put("code", 400);
            errorMsg.put("message", e.getMessage());
            errorMsg.put("timestamp", LocalDateTime.now().toString());

            resp.getWriter().write(mapper.writeValueAsString(errorMsg));
        } catch(Exception e){
            resp.setStatus(500);

            HashMap<String, Object> errorMsg = new HashMap<>();
            errorMsg.put("code", 500);
            errorMsg.put("message", "Internal error");
            errorMsg.put("timestamp", LocalDateTime.now().toString());

            resp.getWriter().write(mapper.writeValueAsString(errorMsg));
        }


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
