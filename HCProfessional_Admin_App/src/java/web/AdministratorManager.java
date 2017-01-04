package web;

/*
import dtos.CourseDTO;
import dtos.StudentDTO;
import dtos.SubjectDTO;
import ejbs.CourseBean;
import ejbs.StudentBean;
import ejbs.SubjectBean;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
*/

import dtos.AdministratorDTO;
import dtos.CaretakerDTO;
import dtos.HealthCareProfessionalDTO;
import dtos.PatientDTO;
import dtos.TrainingMaterialDTO;
import ejbs.AdministratorBean;
import ejbs.CaretakerBean;
import ejbs.HealthCareProfessionalBean;
import ejbs.PatientBean;
import ejbs.TrainingMaterialBean;
import entities.HealthCareProfessional;
import enumerations.MaterialSupport;
import enumerations.MaterialType;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.PatientAssociateException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

@ManagedBean
@SessionScoped
public class AdministratorManager {

    @EJB
    private PatientBean patientBean;
    @EJB
    private CaretakerBean caretakerBean;
    @EJB
    private AdministratorBean administratorBean;
    @EJB
    private HealthCareProfessionalBean healthCareProfessionalBean;
    @EJB
    private TrainingMaterialBean trainingMaterialBean;
    
    private PatientDTO newPatient;
    private PatientDTO currentPatient;
    private CaretakerDTO newCaretaker;
    private CaretakerDTO currentCaretaker;
    private AdministratorDTO newAdministrator;
    private AdministratorDTO currentAdministrator;
    private HealthCareProfessionalDTO newHealthCareProfessional; 
    private HealthCareProfessionalDTO currentHealthCareProfessional; 
    private TrainingMaterialDTO newTrainingMaterial;
    private TrainingMaterialDTO currentTrainingMaterial;
    
    
    private String userType;
    private String searchCaretakersText;
    private String searchAdminsText;
    private String searchHCProsText;
    private String searchTrainingMaterialsText;
    
    
    private List<CaretakerDTO> allCaretakers;
    private List<CaretakerDTO> caretakers;
    private List<AdministratorDTO> allAdmins;
    private List<AdministratorDTO> admins;
    private List<HealthCareProfessionalDTO> allHCPros;
    private List<HealthCareProfessionalDTO> hCPros;
    private List<TrainingMaterialDTO> allTrainingMaterials;
    private List<TrainingMaterialDTO> trainingMaterials;


    private static final Logger logger = Logger.getLogger("web.AdministratorManager");
    private UIComponent component;
    private Client client;
    private final String baseUri
            = "http://localhost:8080/HCProfessional_Admin_App/webapi";

    public AdministratorManager() {
        
        newPatient = new PatientDTO();
        newCaretaker = new CaretakerDTO();
        newAdministrator = new AdministratorDTO();
        newHealthCareProfessional = new HealthCareProfessionalDTO();
        newTrainingMaterial = new TrainingMaterialDTO();
        client = ClientBuilder.newClient();
 
        
        allCaretakers = getAllCaretakersREST();
        caretakers = allCaretakers;
        allAdmins = getAllAdministratorsREST();
        admins = allAdmins;
        allHCPros = getAllHealthCareProfessionalsREST();
        hCPros = allHCPros;
        allTrainingMaterials = getAllTrainingMaterialsREST();
        trainingMaterials = allTrainingMaterials;
    }

    
    
    
    ///////////// Patient /////////////////  
    public List<PatientDTO> getAllPatientsREST() {
        List<PatientDTO> returnedPatients = null;
        returnedPatients = client.target(baseUri)
                .path("/patients/all")
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<PatientDTO>>() {});
        return returnedPatients;
    }
    
    public String createPatient() {
        try {
            patientBean.create(
                    newPatient.getId(),
                    newPatient.getName(),
                    newPatient.getNecessity(),
                    newPatient.getNecessities()
                    );
            
            newPatient.reset();
            return "admin_index?faces-redirect=true";
        } catch (EntityAlreadyExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again later!", component, logger);
        }
        return null;
    }
    
    public String updatePatientsREST(){   
        try {
           client.target(baseUri)
                    .path("/patients/updateREST")
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(currentPatient));
            return "admin_index?faces-redirect=true";
           
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return "admin_patient_update";
    }

    public void removePatient(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("patientId");
            int id = Integer.parseInt(param.getValue().toString());
            patientBean.remove(id);
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }

    /////////////// CARETAKERS /////////////////*/
    
    private List<CaretakerDTO> getAllCaretakersREST() {
        List<CaretakerDTO> returnedCaretakers = client.target(baseUri)
                .path("/caretakers/all")
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<CaretakerDTO>>() {});
        return returnedCaretakers;
    }
    
    public String createCaretaker() {
        try {
            caretakerBean.create(
                    newCaretaker.getUsername(),
                    newCaretaker.getName(),
                    newCaretaker.getPassword());
            newCaretaker.reset();
            userType = "caretaker";
            allCaretakers = getAllCaretakersREST();
            search();
            return "admin_index?faces-redirect=true";
        } catch (EntityAlreadyExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }
    
    public String updateCaretakersREST(){   
        try {
           client.target(baseUri)
                    .path("/caretakers/updateREST")
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(currentCaretaker));
            return UserManager.class.newInstance().isUserInRole("Administrator") ? "/faces/admin/index?faces-redirect=true" : "/faces/healthCareProfessional/index?faces-redirect=true" ;
           
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return "admin_administrator_update";
    }

    public void removeCaretaker(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("caretakerUsername");
            String username = param.getValue().toString();
            if(patientBean.getCaretakerPatients(username).isEmpty()){
                caretakerBean.remove(username);
            userType = "caretaker";
            allCaretakers = getAllCaretakersREST();
            search();
            }else{
                throw new EntityDoesNotExistsException("Can't remove a caretaker with patients!");
            }
            
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Can't remove a caretaker with patients!", logger);
        }
    }

    public List<PatientDTO> getCurrentCaretakerPatients() {
        try {
            return patientBean.patientsToDTOs( patientBean.getCaretakerPatients(currentCaretaker.getUsername()) );
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    public List<TrainingMaterialDTO> getCurrentCaretakerTrainingMaterials() {
        try {
            return trainingMaterialBean.trainingMaterialsToDTO( trainingMaterialBean.getCaretakerTrainingMaterials(currentCaretaker.getUsername()) );
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    public void enrollPatient(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("patientId");
            int id = Integer.parseInt(param.getValue().toString()) ;
            patientBean.enrollPatient(id, currentCaretaker.getUsername());
            
            
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    public void unrollPatients(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("patientId");
            int id = Integer.parseInt(param.getValue().toString());
            patientBean.unrollPatient(id, currentCaretaker.getUsername());
            
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
       
    
    ////////////////////HealthCareProfessional ///////////////////////
    
    public HealthCareProfessionalDTO getNewHealthCareProfessional() {
        return newHealthCareProfessional;
    }

    public void setNewHealthCareProfessional(HealthCareProfessionalDTO newHealthCareProfessional) {
        this.newHealthCareProfessional = newHealthCareProfessional;
    }

    public HealthCareProfessionalDTO getCurrentHealthCareProfessional() {
        return currentHealthCareProfessional;
    }

    public void setCurrentHealthCareProfessional(HealthCareProfessionalDTO currentHealthCareProfessional) {
        this.currentHealthCareProfessional = currentHealthCareProfessional;
    }
 
   private List<HealthCareProfessionalDTO> getAllHealthCareProfessionalsREST() {
        List<HealthCareProfessionalDTO> returnedHealthCareProfessionals = null;
        returnedHealthCareProfessionals = client.target(baseUri)
                .path("/healthCareProfessionals/all")
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<HealthCareProfessionalDTO>>() {});
        return returnedHealthCareProfessionals;
    }
    
    public String createHealthCareProfessional() {
        try {
            healthCareProfessionalBean.create(
                    newHealthCareProfessional.getUsername(),
                    newHealthCareProfessional.getName(),
                    newHealthCareProfessional.getPassword());
            newHealthCareProfessional.reset();
            userType = "hCPro";
            allHCPros = getAllHealthCareProfessionalsREST();
            search();
            return "admin_index?faces-redirect=true";
        } catch (EntityAlreadyExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }
    
    public String updateHealthCareProfessionalREST(){   
        try {
           client.target(baseUri)
                    .path("/healthCareProfessionals/updateREST")
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(currentHealthCareProfessional));
            return "admin_index?faces-redirect=true";
           
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return "admin_administrator_update";
    }
    
    public void removeHealthCareProfessional(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("healthCareProfessionalUsername");
            String username = param.getValue().toString();
            
            
            healthCareProfessionalBean.remove(username);
         
            userType = "hCPro";
            allHCPros = getAllHealthCareProfessionalsREST();
            search();
            
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    ////////////////////ADMINISTRATOR ///////////////////////
    
    
   private List<AdministratorDTO> getAllAdministratorsREST() {
        List<AdministratorDTO> returnedAdministrators = null;
        returnedAdministrators = client.target(baseUri)
                .path("/administrators/all")
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<AdministratorDTO>>() {});
        return returnedAdministrators;
    }
    
    public String createAdministrator() {
        try {
            administratorBean.create(
                    newAdministrator.getUsername(),
                    newAdministrator.getName(),
                    newAdministrator.getPassword());
            newAdministrator.reset();
            userType = "admin";
            allAdmins = getAllAdministratorsREST();
            search();
            return "admin_index?faces-redirect=true";
        } catch (EntityAlreadyExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }
    
    public String updateAdministratorsREST(){   
        try {
           client.target(baseUri)
                    .path("/administrators/updateREST")
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(currentAdministrator));
            return "admin_index?faces-redirect=true";
           
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return "admin_administrator_update";
    }
    
    
    public void removeAdministrator(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("administratorUsername");
            String username = param.getValue().toString();
            
            
            administratorBean.remove(username);
         
            userType = "admin";
            allAdmins = getAllAdministratorsREST();
            search();
            
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Can't remove a caretaker with patients!", logger);
        }
    }
    
        
    ////////////////////TrainingMaterial ///////////////////////
    
    
   private List<TrainingMaterialDTO> getAllTrainingMaterialsREST() {
        List<TrainingMaterialDTO> returnedTrainingMaterials = null;
        returnedTrainingMaterials = client.target(baseUri)
                .path("/trainingMaterials/all")
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<TrainingMaterialDTO>>() {});
        return returnedTrainingMaterials;
    }
    
    public String createTrainingMaterial() {
        try {
            trainingMaterialBean.create(
                    newTrainingMaterial.getId(),
                    newTrainingMaterial.getName(),
                    newTrainingMaterial.getType(),
                    newTrainingMaterial.getSupport());
            newTrainingMaterial.reset();
                        userType = "trainingM";
            allTrainingMaterials = getAllTrainingMaterialsREST();
            search();
            
            
            return "admin_index?faces-redirect=true";
        } catch (EntityAlreadyExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }

    public String updateTrainingMaterialsREST(){   
        try {
           client.target(baseUri)
                    .path("/trainingMaterials/updateREST")
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(currentTrainingMaterial));
            return "admin_index?faces-redirect=true";
           
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return "admin_trainingMaterial_update";
    }
        
    public void removeTrainingMaterial(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("trainingMaterialId");
            int id = Integer.parseInt(param.getValue().toString()) ;            
            
            trainingMaterialBean.remove(id);
            userType = "trainingM";
            allTrainingMaterials = getAllTrainingMaterialsREST();
            search();
                        
            
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Something is wrong!", logger);
        }
    }
      
    public List<String> getAllTrainingMaterialTypes() {
            List<String> trainingMaterialsTypes = new LinkedList(); // = (List<TrainingMaterial>) em.createNamedQuery("getAllTrainingMaterials").getResultList();
            //return trainingMaterialsToDTOs(trainingMaterials);
            for (MaterialType type : MaterialType.values()) {
                trainingMaterialsTypes.add(type.name());
            }
            
            return trainingMaterialsTypes;
    }
   
    public List<String> getAllTrainingMaterialSupports() {
            List<String> trainingMaterialSupports = new LinkedList(); // = (List<TrainingMaterial>) em.createNamedQuery("getAllTrainingMaterials").getResultList();
            //return trainingMaterialsToDTOs(trainingMaterials);
            for (MaterialSupport support : MaterialSupport.values()) {
                trainingMaterialSupports.add(support.name());
            }
            
            return trainingMaterialSupports;
    }
    
    public void enrollTrainingMaterial(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("trainingMaterialId");
            int id = Integer.parseInt(param.getValue().toString()) ;
            trainingMaterialBean.enrollTrainingMaterial(id, currentCaretaker.getUsername());
            
            
        } catch (PatientAssociateException | EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
           
    public void unrollTrainingMaterial(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("trainingMaterialId");
            int id = Integer.parseInt(param.getValue().toString());
            trainingMaterialBean.unrollTrainingMaterial(id, currentCaretaker.getUsername());
            
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    
    /////////////// GETTERS & SETTERS /////////////////    
    
    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }
    
    public PatientDTO getNewPatient() {
        return newPatient;
    }

    public void setNewPatient(PatientDTO newStudent) {
        this.newPatient = newStudent;
    }

    public PatientDTO getCurrentPatient() {
        return currentPatient;
    }

    public void setCurrentPatient(PatientDTO currentPatient) {
        this.currentPatient = currentPatient;
    }
    
    public List<CaretakerDTO> getCaretakers() {
        return caretakers;
    }

    public List<AdministratorDTO> getAdmins() {
        return admins;
    }

    public List<HealthCareProfessionalDTO> gethCPros() {
        return hCPros;
    }

    public List<TrainingMaterialDTO> getTrainingMaterials() {
        return trainingMaterials;
    }
    
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setSearchCaretakersText(String searchCaretakersText) {
        this.searchCaretakersText = searchCaretakersText;
    }

    public String getSearchCaretakersText() {
        return searchCaretakersText;
    }

    public String getSearchAdminsText() {
        return searchAdminsText;
    }

    public void setSearchAdminsText(String searchAdminsText) {
        this.searchAdminsText = searchAdminsText;
    }

    public String getSearchHCProsText() {
        return searchHCProsText;
    }

    public void setSearchHCProsText(String searchHCProsText) {
        this.searchHCProsText = searchHCProsText;
    }

    public String getSearchTrainingMaterialsText() {
        return searchTrainingMaterialsText;
    }

    public void setSearchTrainingMaterialsText(String searchTrainingMaterialsText) {
        this.searchTrainingMaterialsText = searchTrainingMaterialsText;
    }
        
    public CaretakerDTO getNewCaretaker() {
        return newCaretaker;
    }

    public void setNewCaretaker(CaretakerDTO newCaretaker) {
        this.newCaretaker = newCaretaker;
    }

    public CaretakerDTO getCurrentCaretaker() {
        return currentCaretaker;
    }

    public void setCurrentCaretaker(CaretakerDTO currentCaretaker) {
        this.currentCaretaker = currentCaretaker;
    }

    public AdministratorDTO getNewAdministrator() {
        return newAdministrator;
    }

    public void setNewAdministrator(AdministratorDTO newAdministrator) {
        this.newAdministrator = newAdministrator;
    }

    public AdministratorDTO getCurrentAdministrator() {
        return currentAdministrator;
    }

    public void setCurrentAdministrator(AdministratorDTO currentAdministrator) {
        this.currentAdministrator = currentAdministrator;
    }

    public TrainingMaterialDTO getNewTrainingMaterial() {
        return newTrainingMaterial;
    }

    public void setNewTrainingMaterial(TrainingMaterialDTO newTrainingMaterial) {
        this.newTrainingMaterial = newTrainingMaterial;
    }

    public TrainingMaterialDTO getCurrentTrainingMaterial() {
        return currentTrainingMaterial;
    }

    public void setCurrentTrainingMaterial(TrainingMaterialDTO currentTrainingMaterial) {
        this.currentTrainingMaterial = currentTrainingMaterial;
    }
    

    ///////////// VALIDATORS ////////////////////////*/
    
    public void validateId(FacesContext context, UIComponent toValidate, Object value) {
        try {
            //Your validation code goes here
            //If the validation fails

              if(Integer.parseInt(value.toString()) < 1 ){ 
                  throw new NumberFormatException();
              } 
            } catch(NumberFormatException nfe){                  
                FacesMessage message = new FacesMessage("Error: invalid id.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                context.addMessage(toValidate.getClientId(context), message);
                ((UIInput) toValidate).setValid(false);
            } catch (Exception e) {
                FacesExceptionHandler.handleException(e, "Unkown error.", logger);
        }
    }
    
    ///////////// SEARCH ////////////////////////
    
    public void search() {
        switch(userType) {
            case "caretaker":
                List<CaretakerDTO> resultC = new LinkedList();
                caretakers = allCaretakers;                    
                caretakers.stream().filter((c) -> (c.getName().contains(searchCaretakersText))).forEach((c) -> {
                    resultC.add(c);
                });

                caretakers = resultC;
                break;
            case "admin":
                List<AdministratorDTO> resultA = new LinkedList();
                admins = allAdmins;                    
                admins.stream().filter((a) -> (a.getName().contains(searchAdminsText))).forEach((a) -> {
                    resultA.add(a);
                });

                admins = resultA;
                break;
            case "hCPro":
                List<HealthCareProfessionalDTO> resultH = new LinkedList();
                hCPros = allHCPros;                    
                hCPros.stream().filter((h) -> (h.getName().contains(searchHCProsText))).forEach((h) -> {
                    resultH.add(h);
                });

                hCPros = resultH;
                break;
            case "trainingM":
                List<TrainingMaterialDTO> resultT = new LinkedList();
                trainingMaterials = allTrainingMaterials;                    
                trainingMaterials.stream().filter((t) -> (t.getName().contains(searchTrainingMaterialsText))).forEach((t) -> {
                    resultT.add(t);
                });

                trainingMaterials = resultT;
                break;
        }
    }
    
    public void clearSearch() {
        switch(userType) {
            case "caretaker":
                caretakers = allCaretakers;
                searchCaretakersText = null;
                break;
            case "admin":
                admins = allAdmins;
                searchAdminsText = null;
                break;
            case "hCPro":
                hCPros = allHCPros;
                searchHCProsText = null;
            case "trainingM":
                trainingMaterials = allTrainingMaterials;
                searchTrainingMaterialsText = null;
        }
    }
}
