package dtos;

import java.io.Serializable;

public class UserDTO implements Serializable{

    protected long id;
    protected String name;
    protected String password;

    public UserDTO() {
    }    
    
    public UserDTO(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
    
    public void reset() {
        setId(-1);
        setName(null);
        setPassword(null);
    }        

    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
