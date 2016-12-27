package dtos;

import java.io.Serializable;

public class AdministratorDTO implements Serializable {
    
    private String username;
    private String name;
    private String password;

    public AdministratorDTO() {
    }    
    
    public AdministratorDTO(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password= password;
        
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    public void reset() {
        setName(null);
        setUsername(null);
        setPassword(null);  
    }    


}
