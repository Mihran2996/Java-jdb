package jdb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{
    private int id;
    private String name;
    private String surname;
    private int age;
    private String phoneNumber;
    private String email;
    private String password;

    public User(String name, String surname,int age, String phoneNumber,String email, String password) {
        this.name = name;
        this.surname = surname;
        this.age=age;
        this.email = email;
        this.password = password;
        this.phoneNumber=phoneNumber;
    }
}
