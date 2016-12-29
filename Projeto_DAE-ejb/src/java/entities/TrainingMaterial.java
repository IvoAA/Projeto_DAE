/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import enumerations.MaterialType;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    public TrainingMaterial() {
    }

    public TrainingMaterial(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
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

    
    
    
}
