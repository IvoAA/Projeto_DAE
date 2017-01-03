/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.NecessityTypeDTO;
import entities.Caretaker;
import entities.NecessityType;
import entities.NecessityType;
import entities.TrainingMaterial;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.PatientAssociateException;
import exceptions.PatientNotAssociatedException;
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
@Stateless
@Path("/necessityTypes")
public class NecessityTypeBean {

    // addTrainingMaterial business logic below. (Right-click in editor and choose
    // "Insert Code > addTrainingMaterial Business Method")
    @PersistenceContext
    private EntityManager em;

    public void create(String name)
            throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            if (em.find(NecessityType.class, name) != null) {
                throw new EntityAlreadyExistsException("A NecessityTypeBean with that name already exists.");
            }
            NecessityType necessityType = new NecessityType(name);
            em.persist(necessityType);

        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }



    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<NecessityTypeDTO> getAll() {
        try {
            List<NecessityType> necessityTypes = (List<NecessityType>) em.createNamedQuery("getAllNecessityTypes").getResultList();
            return necessityTypesToDTOs(necessityTypes);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }    
    
    
    NecessityTypeDTO necessityTypeToDTO(NecessityType necessityType) {
        return new NecessityTypeDTO(
                necessityType.getName());
  
    }
    
    
    List<NecessityTypeDTO> necessityTypesToDTOs(List<NecessityType> necessityTypes) {
        List<NecessityTypeDTO> dtos = new ArrayList<>();
        for (NecessityType t : necessityTypes) {
            dtos.add(new NecessityTypeDTO(t.getName()));
        }
        return dtos;
    }
    
    public void associateMaterialToNecessityType(int id, String type)
            throws EntityDoesNotExistsException, PatientAssociateException {
        try {

            TrainingMaterial trainingMaterial = em.find(TrainingMaterial.class, id);
            if (trainingMaterial == null) {
                throw new EntityDoesNotExistsException("There is no trainingMaterial with that id.");
            }

            NecessityType necessityType = em.find(NecessityType.class, type);
            if (necessityType == null) {
                throw new EntityDoesNotExistsException("There is no NecessityType with that name.");
            }

            if (necessityType.getMaterials().contains(trainingMaterial)) {
                throw new PatientAssociateException("Material is already associated to that caretaker.");
            }

            necessityType.addTrainingMaterial(trainingMaterial);
            trainingMaterial.addNecessityType(necessityType);

        } catch (PatientAssociateException | EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

}
