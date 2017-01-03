/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "PROCEDURES",
uniqueConstraints =
@UniqueConstraint(columnNames = {"ID"}))
@NamedQueries({
    @NamedQuery(name= "getAllProcedures", 
    query = "SELECT p FROM Procedure p ORDER BY p.name")
})
public class Procedure implements Serializable {

    @Id
    private int id;
    @NotNull
    private String description;
    @NotNull
    private String necessity;
    @NotNull
    private String caretaker;
    @NotNull
    private int patient;
    @NotNull
    private int material;

    
    public Procedure(){

    }
    
    public Procedure(int id, String description, String necessity, String caretaker, int patient, int material) {
        this.id = id;
        this.description = description;
        this.necessity = necessity;
        this.caretaker = caretaker;
        this.patient = patient;
        this.material = material;
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

    public String getNecessity() {
        return necessity;
    }

    public void setNecessity(String necessity) {
        this.necessity = necessity;
    }

    public String getCaretaker() {
        return caretaker;
    }

    public void setCaretaker(String caretaker) {
        this.caretaker = caretaker;
    }

    public int getPatient() {
        return patient;
    }

    public void setPatient(int patient) {
        this.patient = patient;
    }

    public int getMaterial() {
        return material;
    }

    public void setMaterial(int material) {
        this.material = material;
    }
    
    
    
    
}
