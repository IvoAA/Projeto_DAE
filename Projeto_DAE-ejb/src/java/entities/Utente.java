/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Ivo
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "getAllUtentes",
    query = "SELECT u FROM Utente u ORDER BY u.name")})
public class Utente extends User implements Serializable {
    
    public Utente(){ }
    
    public Utente(String username, String name, String password) {
        super(username, name, password);
    }
    
}
    

