/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Utente;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Ivo
 */
@Stateless
@LocalBean
public class UtenteBean {
    
    @PersistenceContext
    private EntityManager em;

    public void create(long id, String name, String password) throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            if(em.find(Utente.class, id) != null){
                throw new EntityAlreadyExistsException("A utente with that id already exists");
            }
            Utente utente = new Utente(id, name, password);
            em.persist(utente);
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage()); 
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
