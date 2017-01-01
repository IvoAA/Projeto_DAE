package dtos;

import entities.Caretaker;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Patient")
@XmlAccessorType(XmlAccessType.FIELD)

public class PatientDTO implements Serializable{
   
    private int id;
    private String name;
    private String caretaker;
    private String necessity;
    private String necessityType;


    public PatientDTO(){
    }

    public PatientDTO(int id, String name, String caretaker, String necessity, String necessityType) {
        this.id = id;
        this.name = name;
        this.caretaker = caretaker;
        this.necessity = necessity;
        this.necessityType = necessityType;
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
    
    public String getCaretaker() {
        return caretaker;
    }

    public void setCaretaker(String caretaker) {
        this.caretaker = caretaker;
    }
    
    public void reset() {
        this.id = -1;
        this.name = null;
        this.caretaker = null;
    }

    public String getNecessity() {
        return necessity;
    }

    public void setNecessity(String necessity) {
        this.necessity = necessity;
    }

    public String getNecessityType() {
        return necessityType;
    }

    public void setNecessityType(String necessityType) {
        this.necessityType = necessityType;
    }
    
    
    
    
}
