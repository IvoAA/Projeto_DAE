/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Figueiredo
 */
@Entity
@Table(name = "TrainingMaterials",
uniqueConstraints =
@UniqueConstraint(columnNames = {"NAME"}))
@NamedQueries({
    @NamedQuery(name = "getAllTrainingMaterials",
    query = "SELECT t FROM TrainingMaterial t ORDER BY t.name")})
public class TrainingMaterial implements Serializable {
    @Id
    private int id;
    @NotNull(message = "Name must not be empty")
    private String name;
    private String type;
    private String support;
   
    
    @ManyToMany
    @JoinTable(name = "TRAININGMATERIAL_CARETAKER",
            joinColumns
            = @JoinColumn(name = "TRAININGMATERIALS_ID", referencedColumnName = "ID"),
            inverseJoinColumns
            = @JoinColumn(name = "CARETAKER_USERNAME", referencedColumnName = "USERNAME"))
    private List<Caretaker> caretakers;
    
    
    
    public TrainingMaterial() {
        caretakers = new LinkedList<>();
    }

    public TrainingMaterial(int id, String name, String type, String support) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.support = support;
        caretakers = new LinkedList<>();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public List<Caretaker> getCaretakers() {
        return caretakers;
    }

    public void setCaretakers(List<Caretaker> caretakers) {
        this.caretakers = caretakers;
    }


    

    
    
    
    
    
}
