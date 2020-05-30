package jdb;

public interface Comand {
    int EXIT = 0;
    int LOGIN = 1;
    int REGISTER = 2;

    int LOGOUT = 0;
    int MY_LIST = 1;
    int MY_IN_PROGRESS_LIST = 2;
    int MY_FINISHED_LIST = 3;
    int ADD_TODO = 4;
    int CHANGE_TODO_STATUS = 5;
    int DELETE_TODO_BY_ID = 6;

    static void printComand() {
        System.out.println( EXIT + " for EXIT");
        System.out.println(LOGIN + " For LOGIN");
        System.out.println(REGISTER + " For REGISTER");
    }

    static void printMainComand() {
        System.out.println("Please input " + LOGOUT + " for LOGOUT");
        System.out.println("Please input " + MY_LIST + " for print MY_LIST");
        System.out.println("Please input " + MY_IN_PROGRESS_LIST + " for print MY_IN_PROGRESS_LIST");
        System.out.println("Please input " + MY_FINISHED_LIST + " for print MY_FINISHED_LIST");
        System.out.println("Please input " + ADD_TODO + " for ADD_TODO ");
        System.out.println("Please input " + CHANGE_TODO_STATUS + " for CHANGE_TODO_STATUS");
        System.out.println("Please input " + DELETE_TODO_BY_ID + " for DELETE_TODO_BY_ID ");

    }

}
