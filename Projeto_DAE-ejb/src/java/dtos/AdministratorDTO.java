package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Administrator")
@XmlAccessorType(XmlAccessType.FIELD)

public class AdministratorDTO extends UserDTO implements Serializable {
    


    public AdministratorDTO() {
    }    
    
    public AdministratorDTO(String username, String name, String password) {
       super(username, name, password);
        
    }




}
