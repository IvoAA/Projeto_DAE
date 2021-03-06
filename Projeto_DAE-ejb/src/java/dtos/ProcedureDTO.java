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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Procedure")
@XmlAccessorType(XmlAccessType.FIELD)

public class ProcedureDTO implements Serializable{
    
    protected int id;
    protected String description;
    private String caretaker;
    private int patient;
    private int material;
    private String date;
    
    public ProcedureDTO(){
        
    }

    public ProcedureDTO(int id, String description, String caretaker, int patient, int material, String date) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    
    public void reset() {
        this.id = -1;
        this.description = null;
        this.caretaker = null;
        this.patient = -1;
        this.material = -1;
        this.date = null;
    }
    
}
