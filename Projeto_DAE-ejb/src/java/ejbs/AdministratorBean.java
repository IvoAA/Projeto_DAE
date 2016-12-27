/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;


import dtos.AdministratorDTO;
import dtos.PatientDTO;
import entities.Administrator;
import entities.Caretaker;
import entities.Patient;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Figueiredo
 */
@Path("/administrators")
@Stateless
public class AdministratorBean {

    
    @PersistenceContext
    private EntityManager em;
    
    
      public void create(String username, String name, String password) throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            if(em.find(Administrator.class, username) != null){
                throw new EntityAlreadyExistsException("A administrator with that username already exists");
            }         
            Administrator administrator = new Administrator(username, name, password);
            em.persist(administrator);
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
            
            Administrator administrator = em.find(Administrator.class, username);
            if (administrator == null) {
                throw new EntityDoesNotExistsException("There is no administrator with that username.");
            }
            

            administrator.setName(name);
            administrator.setPassword(password);
            em.merge(administrator);
            
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @Path("upateREST")
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public void updateREST2(AdministratorDTO administratorDTO) 
        throws EntityDoesNotExistsException, MyConstraintViolationException{
        try {
            Administrator administrator = em.find(Administrator.class, administratorDTO.getUsername());
            if (administrator == null) {
                throw new EntityDoesNotExistsException("There is no patient with that username.");
            }
          

            administrator.setName(administratorDTO.getName());
            administrator.setPassword(administratorDTO.getPassword());
            em.merge(administrator);
            
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
            Administrator administrator = em.find(Administrator.class, username);
            if (administrator == null) {
                throw new EntityDoesNotExistsException("There is no administrator with that username.");
            }            
            em.remove(administrator);
        
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
