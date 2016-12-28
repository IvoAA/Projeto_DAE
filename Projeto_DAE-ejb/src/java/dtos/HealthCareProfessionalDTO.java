package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "HealthCareProfessional")
@XmlAccessorType(XmlAccessType.FIELD)

public class HealthCareProfessionalDTO extends UserDTO implements Serializable {
    


    public HealthCareProfessionalDTO() {
    }    
    
    public HealthCareProfessionalDTO(String username, String name, String password) {
       super(username, name, password);
        
    }


}
