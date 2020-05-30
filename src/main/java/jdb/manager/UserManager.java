package jdb.manager;

import com.sun.org.apache.regexp.internal.RE;
import jdb.Status;
import jdb.db.DBCConnection;
import jdb.model.Todo;
import jdb.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.applet.AppletResourceLoader;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.sql.Date.valueOf;

public class UserManager {
    Connection connection;

    public UserManager() throws SQLException {
        connection = DBCConnection.getInstance().getConnection();
    }

    public void addUser(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user(name,surname,age,phone_number,email,password) VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setInt(3, user.getAge());
        preparedStatement.setString(4, user.getPhoneNumber());
        preparedStatement.setString(5, user.getEmail());
        preparedStatement.setString(6, user.getPassword());
        Statement statement = connection.createStatement();
        ResultSet resultSet1 = statement.executeQuery("SELECT email FROM user");
        while (!resultSet1.isClosed()) {
            if (resultSet1.next()) {
                if (user.getEmail().equals(resultSet1.getString("email"))) {
                    System.out.println("user already exist");
                    break;
                }
            } else {
                preparedStatement.executeUpdate();
                break;
            }
        }
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        while (resultSet.next()) {
            int anInt = resultSet.getInt(1);
            user.setId(anInt);
        }
    }

    public List<User> getUser() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
        List<User> users = new LinkedList<>();
        while (resultSet.next()) {
            int anInt = resultSet.getInt(1);
            User user = new User();
            user.setId(anInt);
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setPhoneNumber(resultSet.getString("phone_number"));
            user.setAge(resultSet.getInt("age"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            users.add(user);
            System.out.println("User was added");
        }
        return users;
    }

    public void deleteUserById(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE  FROM user WHERE id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }

    public User loginUsre(String phoneNumber, String password) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
        User user = new User();
        while (resultSet.next()) {
            if (phoneNumber.equals(resultSet.getString("phone_number"))
                    && password.equals(resultSet.getString("password"))) {
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setAge(resultSet.getInt("age"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
            }
        }
        return user;
    }

    public void addTodo(Todo todo) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO todo(name,deadline,status,user_id) VALUES(?,?,?,?)");
        preparedStatement.setString(1, todo.getName());
        preparedStatement.setString(2, todo.getDeadline());
        preparedStatement.setString(3, todo.getStatus().name());
        preparedStatement.setInt(4, todo.getUserId());
        preparedStatement.executeUpdate();
        System.out.println("Todo was added");
    }

    public void printAllMyTodo(int userId) throws SQLException {
        ResultSet resultSet;
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM todo WHERE user_id=?");
        preparedStatement.setInt(1, userId);
        resultSet = preparedStatement.executeQuery();
        List<Todo> todos = new LinkedList<>();
        while (resultSet.next()) {
            int user_id = resultSet.getInt("user_id");
            Todo todo = new Todo();
            todo.setId(resultSet.getInt("id"));
            todo.setName(resultSet.getString("name"));
            todo.setCreateDate(resultSet.getTimestamp("create_date"));
            todo.setDeadline(resultSet.getString("deadline"));
            todo.setStatus(Status.valueOf(resultSet.getString("status")));
            todo.setUserId(resultSet.getInt("user_id"));
            todos.add(todo);
        }
        printTodo(todos);
    }

    public void printInStatusTodo(Status status) throws SQLException {
        ResultSet resultSet;
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM todo WHERE status=?");
        preparedStatement.setString(1, String.valueOf(status));
        resultSet = preparedStatement.executeQuery();
        List<Todo> todos = new LinkedList<>();
        while (resultSet.next()) {
            int user_id = resultSet.getInt("user_id");
            Todo todo = new Todo();
            todo.setId(resultSet.getInt("id"));
            todo.setName(resultSet.getString("name"));
            todo.setCreateDate(resultSet.getTimestamp("create_date"));
            todo.setDeadline(resultSet.getString("deadline"));
            todo.setStatus(Status.valueOf(resultSet.getString("status")));
            todo.setUserId(resultSet.getInt("user_id"));
            todos.add(todo);
        }
        printTodo(todos);
    }

    public void printTodo(List<Todo> todo) {
        for (Todo todo1 : todo) {
            System.out.println(todo1);
        }
    }

    public void changeTodoById(int id, String status) throws SQLException {
        ResultSet resultSet;
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `todo` SET status=? WHERE id=?");
        preparedStatement.setString(1, String.valueOf(status));
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

    public void deleteTodoById(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE  FROM todo WHERE id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }
}
