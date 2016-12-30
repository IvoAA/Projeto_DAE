package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "CARETAKERS",
        uniqueConstraints
        = @UniqueConstraint(columnNames = {"NAME"}))
@NamedQueries({
    @NamedQuery(name = "getAllCaretakers",
            query = "SELECT c FROM Caretaker c ORDER BY c.name")
    ,
    @NamedQuery(name = "getAllCaretakersNames",
            query = "SELECT c.name FROM Caretaker c ORDER BY c.name")})
public class Caretaker extends User implements Serializable {

    @OneToMany(mappedBy = "caretaker", cascade = CascadeType.REMOVE)
    private List<Patient> patients;

    @ManyToMany(mappedBy = "caretakers")
    private List<TrainingMaterial> trainingMaterials;

    public Caretaker() {
        patients = new LinkedList<>();
        trainingMaterials = new LinkedList<>();
        
    }

    public Caretaker(String username, String name, String password) {
        super(username, name, password);
        patients = new LinkedList<>();
        trainingMaterials = new LinkedList<>();
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void removePatient(Patient patient) {
        patients.remove(patient);
    }

    public List<TrainingMaterial> getTrainingMaterials() {
        return trainingMaterials;
    }

    public void setTrainingMaterials(List<TrainingMaterial> trainingMaterials) {
        this.trainingMaterials = trainingMaterials;
    }
    
    
}
