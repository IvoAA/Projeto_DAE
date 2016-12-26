/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.PatientDTO;
import entities.Caretaker;
import entities.Patient;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Ivo
 */
@Stateless
@Path("/patients")
public class PatientBean {
    
    @PersistenceContext
    private EntityManager em;

    public void create(int id, String name, String caretakerUser) throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            if(em.find(Patient.class, id) != null){
                throw new EntityAlreadyExistsException("A patient with that id already exists");
            }
            Caretaker caretaker = em.find(Caretaker.class, caretakerUser);
            if (caretaker == null) {
                throw new EntityDoesNotExistsException("There is no caretaker with that username.");
            }
            Patient patient = new Patient(id, name, caretaker);
            em.persist(patient);
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage()); 
        }
    }
    

    public void update(int id, String name, String caretakerUser) 
        throws EntityDoesNotExistsException, MyConstraintViolationException{
        try {
            
            Patient patient = em.find(Patient.class, id);
            if (patient == null) {
                throw new EntityDoesNotExistsException("There is no patient with that username.");
            }
            
            Caretaker caretaker = em.find(Caretaker.class, caretakerUser);
            if (caretaker == null) {
                throw new EntityDoesNotExistsException("There is no caretaker with that username.");
            }

            patient.setId(id);
            patient.setName(name);
            patient.setCaretaker(caretaker);
            em.merge(patient);
            
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @Path("updateREST")
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public void updateREST2(PatientDTO patientDTO) 
        throws EntityDoesNotExistsException, MyConstraintViolationException{
        try {
            Patient patient = em.find(Patient.class, patientDTO.getId());
            if (patient == null) {
                throw new EntityDoesNotExistsException("There is no patient with that username.");
            }
           
            Caretaker caretaker = em.find(Caretaker.class, patientDTO.getCaretaker());
            if (caretaker == null) {
                throw new EntityDoesNotExistsException("There is no caretaker with that username.");
            }

            patient.setId(patientDTO.getId());
            patient.setName(patientDTO.getName());
            patient.setCaretaker(caretaker);
            em.merge(patient);
            
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(int id) throws EntityDoesNotExistsException {
        try {
            Patient patient = em.find(Patient.class, id);
            if (patient == null) {
                throw new EntityDoesNotExistsException("There is no patient with that username.");
            }            
            em.remove(patient);
        
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<PatientDTO> getAll() {
        try {
            List<Patient> patients = (List<Patient>) em.createNamedQuery("getAllPatients").getResultList();
            return patientsToDTOs(patients);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    
    PatientDTO patientToDTO(Patient patient) {
        return new PatientDTO(
                patient.getId(),
                patient.getName(),
                patient.getCaretaker().getUsername());
    }
    

    public List<PatientDTO> patientsToDTOs(List<Patient> patients) {
        List<PatientDTO> dtos = new ArrayList<>();
        for (Patient s : patients) {
            dtos.add(patientToDTO(s));
        }
        return dtos;
    }
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}