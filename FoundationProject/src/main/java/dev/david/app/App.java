package dev.david.app;

import dev.david.daos.AppDao;
import dev.david.daos.UserDaoPostgres;
import dev.david.entities.Users;
import dev.david.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class App {
    public static void main(String[] args)  throws SQLException {
        /*Connection con = ConnectionUtil.getConnection();

        String sql = "select * from social_app.app_users where id = 1";
        Statement state = con.createStatement();
        ResultSet rs = state.executeQuery(sql);
        rs.next();

        System.out.println(rs.getString("id"));*/

        AppDao appDAO = new UserDaoPostgres();

        Comparator<Users> sortByAge = (u1,u2) ->{
            if(u1.getAge() < u2.getAge()) return -1;
            if(u1.getAge() > u2.getAge()) return 1;
            return 0;
        };

        List<Users> allUsers = new ArrayList<Users>();
        allUsers = appDAO.getAllUsers();

        Collections.sort(allUsers, sortByAge);
        System.out.println(allUsers);

    }
}
