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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="NECESSITY_TYPES")
public class NecessityType implements Serializable {
    
@Id
private String name;

    public String getName() {
        return name;
    }
@ManyToMany
@JoinTable(name = "NECESSITY_TYPES_MATERIAL",
            joinColumns
            = @JoinColumn(name = "NECESSITY_TYPE", referencedColumnName = "NAME"),
            inverseJoinColumns
            = @JoinColumn(name = "MATERIAL_ID", referencedColumnName = "ID"))
private List<TrainingMaterial> materials;

    public NecessityType() {
    }

    public NecessityType(String name) {
        this.name = name;
        this.materials = new LinkedList<>();
    }
    
    public void Add(TrainingMaterial m){
        materials.add(m);
    }

    public List<TrainingMaterial> getMaterials() {
        return materials;
    }

    
}
