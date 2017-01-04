/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.PatientDTO;
import dtos.ProcedureDTO;
import entities.Caretaker;
import entities.NecessityType;
import entities.Patient;
import entities.Procedure;
import entities.TrainingMaterial;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Stateless
@Path("/procedures")
public class ProcedureBean {

    @PersistenceContext
    private EntityManager em;
    
    
    public void create(int id, String description, String necessity, String caretaker, int patient, int material) throws EntityAlreadyExistsException, MyConstraintViolationException  {
        
        try{  
            if(em.find(Procedure.class,id)!= null){
               throw new EntityAlreadyExistsException("A procedure whith that id already exists");
            }
            
            Caretaker c = em.find(Caretaker.class, caretaker);
            if (c == null) {
                throw new EntityDoesNotExistsException("There is no caretaker with that username.");
            }
            
            Patient p = em.find(Patient.class, patient);
            if (p == null) {
                throw new EntityDoesNotExistsException("There is no patient with that id.");
            }
         
            TrainingMaterial tM = em.find(TrainingMaterial.class, material);
            if (tM == null) {
                throw new EntityDoesNotExistsException("There is no trainingMaterial with that id.");
            }
            
            String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(Calendar.getInstance().getTime());
            
            Procedure procedure = new Procedure(id, description, c.getName(), p.getName(), tM.getName(), timeStamp);
            em.persist(procedure);
       // EntityDoesNotExistException missing
        }catch (EntityAlreadyExistsException e){
            throw e;
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        }catch (Exception e){
           
           throw new EJBException (e.getMessage());
        }
    }
    
    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<ProcedureDTO> getAll() {
        try {
            List<Procedure> procedures = (List<Procedure>) em.createNamedQuery("getAllProcedures").getResultList();
            return proceduresToDTOs(procedures);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    
    ProcedureDTO procedureToDTO(Procedure p){
        return new ProcedureDTO(
                        p.getId(),
                        p.getDescription(),
                        p.getCaretaker(),
                        p.getPatient(),
                        p.getMaterial(),
                        p.getDate());
        
    }
    
    List<ProcedureDTO> proceduresToDTOs(List<Procedure> procedures){
        List<ProcedureDTO> dtos = new ArrayList<>();
        for(Procedure p : procedures){
            dtos.add(procedureToDTO(p));
        }
        return dtos;
    }
    
    
  
    
}
