package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Caretaker")
@XmlAccessorType(XmlAccessType.FIELD)

public class CaretakerDTO extends UserDTO  implements Serializable{

    
    public CaretakerDTO(){
    }
    
    public CaretakerDTO(
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
