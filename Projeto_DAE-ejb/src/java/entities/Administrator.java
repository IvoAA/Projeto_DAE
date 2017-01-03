/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.UserGroup.GROUP;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


/**
 *
 * @author Figueiredo
 */

@NamedQueries({
    @NamedQuery(name = "getAllAdministrators",
    query = "SELECT a FROM Administrator a ORDER BY a.name")})
@Entity
public class Administrator extends User implements Serializable {

  public Administrator(){
      
  }
   
   public Administrator(String username, String name, String password){
       super(username, name, password, GROUP.Administrator);
      
  }
   
   
}
