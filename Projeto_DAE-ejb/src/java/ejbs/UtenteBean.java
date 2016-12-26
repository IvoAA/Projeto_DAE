/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.UtenteDTO;
import entities.Utente;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
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

/**
 *
 * @author Ivo
 */
@Stateless
@Path("/utentes")
public class UtenteBean {
    
    @PersistenceContext
    private EntityManager em;

    public void create(String username, String name, String password) throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            if(em.find(Utente.class, username) != null){
                throw new EntityAlreadyExistsException("A utente with that id already exists");
            }
            Utente utente = new Utente(username, name, password);
            em.persist(utente);
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage()); 
        }
    }
    

    public void update(String username, String password, String name) 
        throws EntityDoesNotExistsException, MyConstraintViolationException{
        try {
            
            Utente utente = em.find(Utente.class, username);
            if (utente == null) {
                throw new EntityDoesNotExistsException("There is no utente with that username.");
            }

            utente.setPassword(password);
            utente.setName(name);
            em.merge(utente);
            
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
            Utente utente = em.find(Utente.class, username);
            if (utente == null) {
                throw new EntityDoesNotExistsException("There is no utente with that username.");
            }
            
            em.remove(utente);
        
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
    public List<UtenteDTO> getAll() {
        try {
            List<Utente> utentes = (List<Utente>) em.createNamedQuery("getAllUtentes").getResultList();
            return utentesToDTOs(utentes);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    
    UtenteDTO utenteToDTO(Utente utente) {
        return new UtenteDTO(
                utente.getUsername(),
                utente.getName(),
                null);
    }
    

    List<UtenteDTO> utentesToDTOs(List<Utente> utentes) {
        List<UtenteDTO> dtos = new ArrayList<>();
        for (Utente s : utentes) {
            dtos.add(utenteToDTO(s));
        }
        return dtos;
    }
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
