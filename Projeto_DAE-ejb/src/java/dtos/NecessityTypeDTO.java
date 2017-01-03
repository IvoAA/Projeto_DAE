/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;


import entities.Caretaker;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "NecessityType")
@XmlAccessorType(XmlAccessType.FIELD)

public class NecessityTypeDTO {
    
    private String name;

    public NecessityTypeDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NecessityTypeDTO(String name) {
        this.name = name;
    }
    
    public void reset() {
        this.name = null;
    }
    
    
    
}
