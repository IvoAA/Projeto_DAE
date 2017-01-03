/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.PatientDTO;
import dtos.ProcedureDTO;
import entities.Patient;
import entities.Procedure;
import exceptions.EntityAlreadyExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
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
         
            Procedure procedure = new Procedure(id, description, necessity, caretaker, patient, material);
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
                        p.getNecessity(),
                        p.getCaretaker(),
                        p.getPatient(),
                        p.getMaterial());
        
    }
    
    List<ProcedureDTO> proceduresToDTOs(List<Procedure> procedures){
        List<ProcedureDTO> dtos = new ArrayList<>();
        for(Procedure p : procedures){
            dtos.add(procedureToDTO(p));
        }
        return dtos;
    }
    
    
  
    
}
