/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ivo
 */
@Entity
@Table(name = "PATIENTS",
uniqueConstraints =
@UniqueConstraint(columnNames = {"NAME"}))
@NamedQueries({
    @NamedQuery(name = "getAllPatients",
    query = "SELECT p FROM Patient p ORDER BY p.name")})
public class Patient implements Serializable {

    @Id
    protected int id;
    @NotNull(message = "Name must not be empty")
    protected String name;
    
    @ManyToOne
    @JoinColumn(name = "CARETAKER_USERNAME")
    //@NotNull (message="A patient must have a caretaker")
    private Caretaker caretaker;
    
    public Patient(){ 
  
    }
    
    public Patient(int id, String name) {
        this.id = id;
        this.name = name;
        this.caretaker = null;
        
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Caretaker getCaretaker() {
        return caretaker;
    }

    public void setCaretaker(Caretaker caretaker) {
        this.caretaker = caretaker;
    }


    
    
    
}
    

