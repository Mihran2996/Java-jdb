package jdb.model;


import jdb.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
@Data
@NoArgsConstructor
public class Todo {
    private int id;
    private String name;
    private Date createDate;
    private String  deadline;
    private Status status;
    private int  userId;

    public Todo(int id,String name, Date createDate, String deadline, Status status, int userId) {
        this.id=id;
        this.name = name;
        this.createDate = createDate;
        this.deadline = deadline;
        this.status = status;
        this.userId = userId;
    }
}
