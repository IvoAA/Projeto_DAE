/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.TrainingMaterialDTO;
import entities.Caretaker;
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
@Path("/trainingMaterials")
public class TrainingMaterialBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    private EntityManager em;

    public void create(int id, String name, String type, String support)
            throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            if (em.find(TrainingMaterial.class, id) != null) {
                throw new EntityAlreadyExistsException("A TrainingMaterial with that id already exists.");
            }
            TrainingMaterial trainingMaterial = new TrainingMaterial(id, name, type, support);
            em.persist(trainingMaterial);

        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @PUT
    @Path("updateREST")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateREST(TrainingMaterialDTO trainingMaterialDTO)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            TrainingMaterial trainingMaterial = em.find(TrainingMaterial.class, trainingMaterialDTO.getId());
            if (trainingMaterial == null) {
                throw new EntityDoesNotExistsException("There is no trainingMaterial with that username.");
            }

            trainingMaterial.setName(trainingMaterialDTO.getName());
            em.merge(trainingMaterial);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
     public void update(TrainingMaterialDTO trainingMaterialDTO)
            throws EntityDoesNotExistsException, MyConstraintViolationException {
        try {
            TrainingMaterial trainingMaterial = em.find(TrainingMaterial.class, trainingMaterialDTO.getId());
            if (trainingMaterial == null) {
                throw new EntityDoesNotExistsException("There is no trainingMaterial with that username.");
            }

            trainingMaterial.setName(trainingMaterialDTO.getName());
            em.merge(trainingMaterial);

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
            TrainingMaterial trainingMaterial = em.find(TrainingMaterial.class, id);
            if (trainingMaterial == null) {
                throw new EntityDoesNotExistsException("There is no trainingMaterial with that id");
            }

            em.remove(trainingMaterial);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    List<TrainingMaterialDTO> trainingMaterialsToDTOs(List<TrainingMaterial> trainingMaterials) {
        List<TrainingMaterialDTO> dtos = new ArrayList<>();
        for (TrainingMaterial t : trainingMaterials) {
            dtos.add(new TrainingMaterialDTO(t.getId(), t.getName(), t.getType(), t.getSupport()));
        }
        return dtos;
    }

    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<TrainingMaterialDTO> getAll() {
        try {
            List<TrainingMaterial> trainingMaterials = (List<TrainingMaterial>) em.createNamedQuery("getAllTrainingMaterials").getResultList();
            return trainingMaterialsToDTOs(trainingMaterials);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void enrollTrainingMaterial(int id, String username)
            throws EntityDoesNotExistsException, PatientAssociateException {
        try {

            TrainingMaterial trainingMaterial = em.find(TrainingMaterial.class, id);
            if (trainingMaterial == null) {
                throw new EntityDoesNotExistsException("There is no trainingMaterial with that id.");
            }

            Caretaker caretaker = em.find(Caretaker.class, username);
            if (caretaker == null) {
                throw new EntityDoesNotExistsException("There is no subject with that code.");
            }

            if (caretaker.getTrainingMaterials().contains(trainingMaterial)) {
                throw new PatientAssociateException("Material is already associated to that caretaker.");
            }

            caretaker.addTrainingMateial(trainingMaterial);
            trainingMaterial.addCaretaker(caretaker);

        } catch (PatientAssociateException | EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
     public void unrollTrainingMaterial(int id, String username)
            throws EntityDoesNotExistsException, PatientNotAssociatedException {
        try {
            Caretaker caretaker = em.find(Caretaker.class, username);
            if (caretaker == null) {
                throw new EntityDoesNotExistsException("There is no caretaker with that username.");
            }

            TrainingMaterial trainingMaterial = em.find(TrainingMaterial.class, id);
            if (trainingMaterial == null) {
                throw new EntityDoesNotExistsException("There is no trainingMaterial with that id.");
            }

            if (!caretaker.getTrainingMaterials().contains(trainingMaterial)) {
                throw new PatientNotAssociatedException();
            }

            caretaker.removeTrainingMateial(trainingMaterial);
            trainingMaterial.removeCaretaker(caretaker);

        } catch (EntityDoesNotExistsException | PatientNotAssociatedException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    
    
        TrainingMaterialDTO trainingMaterialDTO(TrainingMaterial trainingMaterial) {
        return new TrainingMaterialDTO(
                trainingMaterial.getId(),
                trainingMaterial.getName(),
                trainingMaterial.getType(),
                trainingMaterial.getSupport());
  
    }
    
    
        public List<TrainingMaterialDTO> trainingMaterialsToDTO(List<TrainingMaterial> trainingMaterials) {
        List<TrainingMaterialDTO> dtos = new ArrayList<>();
        for (TrainingMaterial s : trainingMaterials) {
            dtos.add(trainingMaterialDTO(s));
        }
        return dtos;
    }
        
             public List<TrainingMaterial> getCaretakerTrainingMaterials(String caretakerUsername) throws EntityDoesNotExistsException {
        try {
            Caretaker caretaker = em.find(Caretaker.class, caretakerUsername);
            if (caretaker == null) {
                throw new EntityDoesNotExistsException("Caretaker does not exists.");
            }

            return caretaker.getTrainingMaterials();

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

}
