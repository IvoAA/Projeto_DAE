/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Caretaker;
import entities.NecessityType;
import entities.Patient;
import entities.TrainingMaterial;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Procedure")
@XmlAccessorType(XmlAccessType.FIELD)

public class ProcedureDTO implements Serializable{
    
    protected int id;
    protected String description;
    protected Caretaker caretaker;
    protected Patient patient;
    protected TrainingMaterial material;
    
    public ProcedureDTO(){
        
    }

    public ProcedureDTO(int id, String description, Caretaker caretaker, Patient patient, TrainingMaterial material) {
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


    
    public void reset() {
        this.id = -1;
        this.description = null;
        this.caretaker = null;
        this.patient = null;
        this.material = null;
    }
    
}
