package ejbs;

import dtos.CaretakerDTO;
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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/caretakers")
public class CaretakerBean {

    @PersistenceContext
    private EntityManager em;

    public void create(String username, String name, String password)
            throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            if (em.find(Caretaker.class, username) != null) {
                throw new EntityAlreadyExistsException("A caretaker with that code already exists.");
            }
            Caretaker caretaker = new Caretaker(username, name, password);
            em.persist(caretaker);

        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(String username) throws EntityDoesNotExistsException {

        try {
            Caretaker caretaker = em.find(Caretaker.class, username);
            if (caretaker == null) {
                throw new EntityDoesNotExistsException("There is no caretaker with that username");
            }

            em.remove(caretaker);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
            int a = 1;
    }

    List<CaretakerDTO> caretakersToDTOs(List<Caretaker> caretakers) {
        List<CaretakerDTO> dtos = new ArrayList<>();
        for (Caretaker c : caretakers) {
            dtos.add(new CaretakerDTO(c.getUsername(), c.getName(), c.getPassword()));            
        }
        return dtos;
    }
    
     @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<CaretakerDTO> getAll() {
        try {
            List<Caretaker> caretakers = (List<Caretaker>) em.createNamedQuery("getAllCaretakers").getResultList();
            return caretakersToDTOs(caretakers);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
