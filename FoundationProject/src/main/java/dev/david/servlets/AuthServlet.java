package dev.david.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.david.daos.UserDaoPostgres;
import dev.david.entities.Users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class AuthServlet extends HttpServlet {

    private final ObjectMapper mapper;
    private final UserDaoPostgres userDaoPostgres;

    public AuthServlet(ObjectMapper mapper, UserDaoPostgres userDaoPostgres){
        this.mapper = mapper;
        this.userDaoPostgres = userDaoPostgres;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Users> allUsers = userDaoPostgres.getAllUsers();

        HashMap<String, Object> credentials = mapper.readValue(req.getInputStream(), HashMap.class);

        for(Users u : allUsers) {
            if(credentials.get("username").equals(u.getUsername()) && credentials.get("password").equals(u.getPassword())){
                System.out.println("[LOG] - Found User!");
                HttpSession session = req.getSession();
                session.setAttribute("auth-user", u);

                resp.setStatus(204);
                return;
            }
        }
        resp.setStatus(400);
        resp.setContentType("application/json");
        HashMap<String, Object> errorMsg = new HashMap<>();
        errorMsg.put("code", 400);
        errorMsg.put("message", "No users found with provided credentials!");
        errorMsg.put("timestamp", LocalDateTime.now().toString());

        resp.getWriter().write(mapper.writeValueAsString(errorMsg));

    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if(session != null){
            session.invalidate();
        }
    }
}
