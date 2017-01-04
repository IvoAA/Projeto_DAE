/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ivo
 */
@Entity
@Table(name = "PROCEDURES",
uniqueConstraints =
@UniqueConstraint(columnNames = {"ID"}))
@NamedQueries({
    @NamedQuery(name= "getAllProcedures", 
    query = "SELECT p FROM Procedure p ORDER BY p.id")
})
public class Procedure implements Serializable {

    @Id
    private int id;
    @NotNull
    private String description;
    @NotNull
    private String caretaker;
    @NotNull
    private String patient;
    @NotNull
    private String material;
    @NotNull
    private String date;

    public Procedure(){
    }

    public Procedure(int id, String description, String caretaker, String patient, String material, String date) {
        this.id = id;
        this.description = description;
        this.caretaker = caretaker;
        this.patient = patient;
        this.material = material;
        this.date = date;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCaretaker() {
        return caretaker;
    }

    public void setCaretaker(String caretaker) {
        this.caretaker = caretaker;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
    
}
