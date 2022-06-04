package dev.david.daos;

import dev.david.entities.Users;
import dev.david.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoPostgres implements AppDao{
    @Override
    public Users createUser(Users user) {

        try(Connection con = ConnectionUtil.getConnection()){
            String sql = "insert into social_app.app_users values (default,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getEmail());
            ps.setInt(2, user.getAge());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int genId = rs.getInt("id");

            user.setUserId(genId);
            return user;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public Users getUserById(int id) {
        // Try with resources
        try(Connection con = ConnectionUtil.getConnection()){
            String sql = "select * from social_app.app_users where id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            rs.next();

            Users user = new Users();
            user.setUserId(id);
            user.setEmail(rs.getString("email"));
            user.setAge(rs.getInt("age"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;

        } catch (SQLException exception){
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Users> getAllUsers() {
        try(Connection con = ConnectionUtil.getConnection()){
            String sql = "select * from social_app.app_users";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Users> allUsers = new ArrayList<Users>();

            while(rs.next()){
                Users user = new Users();
                user.setUserId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setAge(rs.getInt("age"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                allUsers.add(user);
            }
            return allUsers;
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public Users updateUser(Users user) {
        try(Connection con = ConnectionUtil.getConnection()){
            String sql = "update social_app.app_users set email=?, age=?, username=?, password=? where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setInt(2, user.getAge());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getUserId());

            ps.execute();

            return user;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteUserById(int id) {
        try(Connection con = ConnectionUtil.getConnection()){
            String sql = "delete from social_app.app_users where id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
