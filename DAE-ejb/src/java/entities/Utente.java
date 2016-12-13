/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.sun.istack.internal.NotNull;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Ivo
 */
@Entity
@Table(name="UTENTES")
public class Utente implements Serializable {

        
    @Id
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String name;
    
    public Utente(){ }
    
    public Utente(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }
        
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
}
    

