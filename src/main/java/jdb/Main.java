package jdb;

import jdb.manager.UserManager;
import jdb.model.Todo;
import jdb.model.User;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class Main implements Comand {
    public static Scanner scanner = new Scanner(System.in);
    public static UserManager userManager;
    public static User currentUser;

    static {
        try {
            userManager = new UserManager();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        boolean isRun = true;
        while (isRun) {
            Comand.printComand();
            int comand;
            try {
                comand = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                comand = -1;
                System.out.println("Please input number!");
            }
            switch (comand) {
                case EXIT:
                    isRun = false;
                    break;
                case LOGIN:
                    loginUsre();
                    break;
                case REGISTER:
                    regisdterUser();
                    break;
                default:
                    System.out.println("Wrong comand! please try again");
            }


        }
    }

    private static void regisdterUser() throws SQLException {
        System.out.println("Please input user`(name,surname,age,phoneNumber,email,password)");
        String userData = scanner.nextLine();
        String[] userArr = userData.split(",");
        User user = new User();
        user.setName(userArr[0]);
        user.setSurname(userArr[1]);
        user.setAge(Integer.parseInt(userArr[2]));
        user.setPhoneNumber(userArr[3]);
        user.setEmail(userArr[4]);
        user.setPassword(userArr[5]);
        userManager.addUser(user);
    }

    private static void loginUsre() throws SQLException {
        System.out.println("Please input user`(phoneNumber,password)");
        System.out.print("phoneNUmber: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("password: ");
        String password = scanner.nextLine();
        User user = userManager.loginUsre(phoneNumber, password);
        if (user != null && phoneNumber.equals(user.getPhoneNumber()) && password.equals(user.getPassword())) {
            currentUser = user;
            loginSuccess();
        }


    }

    private static void loginSuccess() throws SQLException {
        boolean isRun = true;
        while (isRun) {
            Comand.printMainComand();
            int comand;
            try {
                comand = Integer.parseInt(scanner.nextLine());
            } catch (NullPointerException e) {
                comand = -1;
                System.out.println("Please input number!");
            }
            switch (comand) {
                case LOGOUT:
                    isRun = false;
                    break;
                case MY_LIST:
                    userManager.printAllMyTodo(currentUser.getId());
                    break;
                case MY_IN_PROGRESS_LIST:
                    userManager.printInStatusTodo(Status.valueOf("IN_PROGRESS"));
                    break;
                case MY_FINISHED_LIST:
                    userManager.printInStatusTodo(Status.valueOf("FINISHED"));
                    break;
                case ADD_TODO:
                    addNewTodo();
                    break;
                case CHANGE_TODO_STATUS:
                    changeTodo();
                    break;
                case DELETE_TODO_BY_ID:
                    deleteById();
                    break;
                default:
                    System.out.println("Wrong comand");
            }
        }
    }

    private static void deleteById() throws SQLException {
        userManager.printAllMyTodo(currentUser.getId());
        System.out.println("Please input id");
        int id = Integer.parseInt(scanner.nextLine());
        userManager.deleteTodoById(id);

    }

    private static void changeTodo() throws SQLException {
        userManager.printAllMyTodo(currentUser.getId());
        System.out.println("Please input id,status");
        System.out.println(Arrays.toString(Status.values()));
        System.out.print("id: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println("Status");
        String status = scanner.nextLine();
        userManager.changeTodoById(id, status);

    }

    private static void addNewTodo() throws SQLException {
        System.out.println("Please input todo` (name,deadline,status)");
        System.out.println("Please select statous` " + Arrays.toString(Status.values()));
        String todoData = scanner.nextLine();
        String[] todoArr = todoData.split(",");
        Todo todo = new Todo();
        todo.setName(todoArr[0]);
        todo.setDeadline(todoArr[1]);
        todo.setStatus(Status.valueOf(todoArr[2].toUpperCase()));
        todo.setUserId(currentUser.getId());
        userManager.addTodo(todo);
    }


}
