/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;


import dtos.HealthCareProfessionalDTO;
import entities.HealthCareProfessional;
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
 * @author Figueiredo
 */
@Path("/healthCareProfessionals")
@Stateless
public class HealthCareProfessionalBean {

    
    @PersistenceContext
    private EntityManager em;
    
    
      public void create(String username, String name, String password) throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            if(em.find(HealthCareProfessional.class, username) != null){
                throw new EntityAlreadyExistsException("A healthCareProfessional with that username already exists");
            }         
            HealthCareProfessional healthCareProfessional = new HealthCareProfessional(username, name, password);
            em.persist(healthCareProfessional);
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage()); 
        }
    }
    

    public void update(String username, String name, String password) 
        throws EntityDoesNotExistsException, MyConstraintViolationException{
        try {
            
            HealthCareProfessional healthCareProfessional = em.find(HealthCareProfessional.class, username);
            if (healthCareProfessional == null) {
                throw new EntityDoesNotExistsException("There is no healthCareProfessional with that username.");
            }
            

            healthCareProfessional.setName(name);
            healthCareProfessional.setPassword(password);
            em.merge(healthCareProfessional);
            
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
    public void updateREST(HealthCareProfessionalDTO healthCareProfessionalDTO) 
        throws EntityDoesNotExistsException, MyConstraintViolationException{
        try {
            HealthCareProfessional healthCareProfessional = em.find(HealthCareProfessional.class, healthCareProfessionalDTO.getUsername());
            if (healthCareProfessional == null) {
                throw new EntityDoesNotExistsException("There is no patient with that username.");
            }
          
            healthCareProfessional.setName(healthCareProfessionalDTO.getName());
            healthCareProfessional.setPassword(healthCareProfessionalDTO.getPassword());
            em.merge(healthCareProfessional);
            
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
       public void remove(String username) throws EntityDoesNotExistsException {
        try {
            HealthCareProfessional healthCareProfessional = em.find(HealthCareProfessional.class, username);
            if (healthCareProfessional == null) {
                throw new EntityDoesNotExistsException("There is no healthCareProfessional with that username.");
            }            
            em.remove(healthCareProfessional);
        
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
       
       
        List<HealthCareProfessionalDTO> healthCareProfessionalToDTO(List<HealthCareProfessional> healthCareProfessionals) {
        List<HealthCareProfessionalDTO> dtos = new ArrayList<>();
        for (HealthCareProfessional a : healthCareProfessionals) {
            dtos.add(new HealthCareProfessionalDTO(a.getUsername(), a.getName(), a.getPassword()));            
        }
        return dtos;
    } 
       
     @GET
    //@RolesAllowed({"HealthCareProfessional"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<HealthCareProfessionalDTO> getAll() {
        try {
            List<HealthCareProfessional> healthCareProfessionals = (List<HealthCareProfessional>) em.createNamedQuery("getAllHealthCareProfessionals").getResultList();
            return healthCareProfessionalToDTO(healthCareProfessionals);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
       
}
