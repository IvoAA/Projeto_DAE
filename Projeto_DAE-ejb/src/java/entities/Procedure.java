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
    @ManyToOne
    @JoinColumn(name = "CARETAKER_NAME")
    @NotNull (message="A Procedure must have a caretaker")
    private Caretaker caretaker;
    @ManyToOne
    @JoinColumn(name = "PATIENT_NAME")
    @NotNull (message="A Procedure must have a patient")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "TRAINING_MATERIAL_ID")
    @NotNull (message="A Procedure must have a material")
    private TrainingMaterial material;

    public Procedure(){

    }

    public Procedure(int id, String description, Caretaker caretaker, Patient patient, TrainingMaterial material) {
        this.id = id;
        this.description = description;
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
    
    public Caretaker getCaretaker() {
        return caretaker;
    }

    public void setCaretaker(Caretaker caretaker) {
        this.caretaker = caretaker;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public TrainingMaterial getMaterial() {
        return material;
    }

    public void setMaterial(TrainingMaterial material) {
        this.material = material;
    }
    
    
    
}
