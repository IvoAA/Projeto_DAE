package dtos;

import java.io.Serializable;

public class AdministratorDTO extends UserDTO implements Serializable {
    


    public AdministratorDTO() {
    }    
    
    public AdministratorDTO(String username, String name, String password) {
       super(username, name, password);
        
    }




}
