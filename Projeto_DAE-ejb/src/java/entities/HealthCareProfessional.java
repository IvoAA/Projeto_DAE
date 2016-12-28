/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Figueiredo
 */

@NamedQueries({
    @NamedQuery(name = "getAllHealthCareProfessionals",
    query = "SELECT hcp FROM HealthCareProfessional hcp ORDER BY hcp.name")})
@Entity
public class HealthCareProfessional extends User implements Serializable {

    public HealthCareProfessional(){
      
  }
   
   public HealthCareProfessional(String username, String name, String password){
       super(username, name, password);
      
  }
}
