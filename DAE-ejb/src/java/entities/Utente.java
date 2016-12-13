/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ivo
 */
@Entity
@Table(name="UTENTES")
public class Utente implements Serializable {

        
    @Id
    protected long id;
    @NotNull
    protected String name;
    @NotNull
    protected String password;
    
    public Utente(){ }
    
    public Utente(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
        
    public long getId() {
        return id;
    }

    public void setUsername(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
    

