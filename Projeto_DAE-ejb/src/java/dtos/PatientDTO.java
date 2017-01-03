package dtos;



import java.io.Serializable;
import java.util.List;
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
    private List<String> necessities;



    public PatientDTO(){
        
    }

    public PatientDTO(int id, String name, String caretaker, String necessity, List<String> necessities ) {
        this.id = id;
        this.name = name;
        this.caretaker = caretaker;
        this.necessity = necessity;
        this.necessities = necessities;
        
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

    public List<String> getNecessities() {
        return necessities;
    }

    public void setNecessities(List<String> necessities) {
        this.necessities = necessities;
    }

    public String getNecessity() {
        return necessity;
    }

    public void setNecessity(String necessity) {
        this.necessity = necessity;
    }
    
    
    
    
    
    public void reset() {
        this.id = -1;
        this.name = null;
        this.caretaker = null;
    }


    
    
    
    
}
