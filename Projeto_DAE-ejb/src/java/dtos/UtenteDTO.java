package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Student")
@XmlAccessorType(XmlAccessType.FIELD)

public class UtenteDTO extends UserDTO implements Serializable{
   
    public UtenteDTO(){
    }

    public UtenteDTO(
            String username,
            String name,
            String password) {
        super(username, name, password);
    }
    
    @Override
    public void reset() {
        super.reset();
    }
}