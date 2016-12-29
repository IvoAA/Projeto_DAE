/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;


import entities.Caretaker;
import enumerations.MaterialType;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TrainingMaterial")
@XmlAccessorType(XmlAccessType.FIELD)

public class TrainingMaterialDTO {
    
    private int id;
    private String name;

    private String type;

    public TrainingMaterialDTO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TrainingMaterialDTO(int id, String name, String type ) {
        this.id = id;
        this.name = name;
        this.type = type;
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

    
    
    public void reset() {
        this.id = -1;
        this.name = null;
    }
    
}
