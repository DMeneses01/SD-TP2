package com.example.data;

import com.example.demo.UsersRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Entity
@XmlRootElement
public class Users {
    @Id
    private String email;
    private String name, telephone, password;
    private boolean admin;

    public Users() {}

    public Users(String name, String telephone, String email, String password, boolean admin) {
        this.name = name;
        this.telephone = telephone;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public String getTelephone() { return telephone; }

    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public boolean isAdmin() { return admin; }

    public void setAdmin(boolean admin) { this.admin = admin; }

    public String toString() {
        return this.name + " Telephone: " + this.telephone;
    }


}
