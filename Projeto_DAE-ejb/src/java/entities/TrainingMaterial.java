/**
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ivo                  TODO!!!!
 *//*
@Entity
@Table(name = "TRAININGMATERIALS",
uniqueConstraints =
@UniqueConstraint(columnNames = {"NAME"}))
@NamedQueries({
    @NamedQuery(name = "getAllMaterials",
    query = "SELECT m FROM TrainingMaterial m ORDER BY m.name")})
*/public class TrainingMaterial implements Serializable {
/*
    @Id
    protected int id;
    @NotNull(message = "ID must not be empty")
    protected String name;
    @NotNull(message = "Related Necessity must not be empty")
    protected String relatedNecessity;    
    @ManyToMany
    @JoinTable(name = "TRAININGMATERIAL_CARETAKER",
            joinColumns
            = @JoinColumn(name = "TRAININGMATERIAL_ID", referencedColumnName = "ID"),
            inverseJoinColumns
            = @JoinColumn(name = "CARETAKER_USERNAME", referencedColumnName = "USERNAME"))
    private List<Caretaker> caretakers;
    
    public TrainingMaterial(){ }
    
    public TrainingMaterial(int id, String name, String relatedNecessity) {
        this.id = id;
        this.name = name;
        this.relatedNecessity = relatedNecessity;
        this.caretakers = new LinkedList();
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
    }*/
}
    

