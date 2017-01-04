package web;




import dtos.AdministratorDTO;
import dtos.CaretakerDTO;
import dtos.HealthCareProfessionalDTO;
import dtos.PatientDTO;
import dtos.ProcedureDTO;
import dtos.TrainingMaterialDTO;
import ejbs.AdministratorBean;
import ejbs.CaretakerBean;
import ejbs.HealthCareProfessionalBean;
import ejbs.PatientBean;
import ejbs.ProcedureBean;
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
    @EJB
    private ProcedureBean procedureBean;
    
    private PatientDTO newPatient;
    private PatientDTO currentPatient;
    private CaretakerDTO newCaretaker;
    private CaretakerDTO currentCaretaker;
    private String cCaretaker;
    private AdministratorDTO newAdministrator;
    private AdministratorDTO currentAdministrator;
    private HealthCareProfessionalDTO newHealthCareProfessional; 
    private HealthCareProfessionalDTO currentHealthCareProfessional; 
    private TrainingMaterialDTO newTrainingMaterial;
    private TrainingMaterialDTO currentTrainingMaterial;
    private ProcedureDTO newProcedure;
    private ProcedureDTO currentProcedure;
        
    private String userType;
    private String searchCaretakersText;
    private String searchAdminsText;
    private String searchHCProsText;
    private String searchTrainingMaterialsText;
    private String searchProcedureText;
        
    private List<CaretakerDTO> allCaretakers;
    private List<CaretakerDTO> caretakers;
    private List<AdministratorDTO> allAdmins;
    private List<AdministratorDTO> admins;
    private List<HealthCareProfessionalDTO> allHCPros;
    private List<HealthCareProfessionalDTO> hCPros;
    private List<TrainingMaterialDTO> allTrainingMaterials;
    private List<TrainingMaterialDTO> trainingMaterials;
    private List<ProcedureDTO> allProcedures;
    private List<ProcedureDTO> procedures;


    private static final Logger logger = Logger.getLogger("web.AdministratorManager");
    private UIComponent component;
    private Client client;
    private final String baseUri
            = "http://localhost:8080/caretaker_app/webapi";

    public AdministratorManager() {
        
        newPatient = new PatientDTO();
        newCaretaker = new CaretakerDTO();
        newAdministrator = new AdministratorDTO();
        newHealthCareProfessional = new HealthCareProfessionalDTO();
        newTrainingMaterial = new TrainingMaterialDTO();
        newProcedure = new ProcedureDTO();
        client = ClientBuilder.newClient();
 
        
    }

////////////////////Procedures ///////////////////////
    private List<ProcedureDTO> getAllProceduresREST() {
        List<ProcedureDTO> returnedProcedures = null;
        returnedProcedures = client.target(baseUri)
                .path("/procedures/all")
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<ProcedureDTO>>() {
                });
        return returnedProcedures;
    }

    public String createProcedure() {
        try {
            procedureBean.create(
                    newProcedure.getId(),
                    newProcedure.getDescription(),
                    newProcedure.getCaretaker(),
                    newProcedure.getPatient(),
                    newProcedure.getMaterial());
            newProcedure.reset();
            userType = "procedure";
            allProcedures = getAllProceduresREST();
            search();

            return "index?faces-redirect=true";
        } catch (EntityAlreadyExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }
    
    public String updateProceduresREST() {
        try {
            client.target(baseUri)
                    .path("/procedures/updateREST")
                    .request(MediaType.APPLICATION_XML).put(Entity.xml(currentProcedure));
            return "index?faces-redirect=true";

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return "admin_procedure_update";
    }

    public void removeProcedure(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("procedureId");
            int id = Integer.parseInt(param.getValue().toString());

            procedureBean.remove(id);
            userType = "procedure";
            allProcedures = getAllProceduresREST();
            search();

        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Something is wrong!", logger);
        }
    }
    
   
    

    ////////////////////TrainingMaterial ///////////////////////
    public List<TrainingMaterialDTO> getCurrentCaretakerTrainingMaterials() {
        try {
            return trainingMaterialBean.trainingMaterialsToDTO( trainingMaterialBean.getCaretakerTrainingMaterials(cCaretaker) );
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
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
    

    
    
    /////////////// GETTERS & SETTERS ///////////////// 
    
     public ProcedureDTO getNewProcedure() {
        return newProcedure;
    }

    public void setNewProcedure(ProcedureDTO newProcedure) {
        this.newProcedure = newProcedure;
    }

    public ProcedureDTO getCurrentProcedure() {
        return currentProcedure;
    }

    public void setCurrentProcedure(ProcedureDTO currentProcedure) {
        this.currentProcedure = currentProcedure;
    }

    public String getSearchProcedureText() {
        return searchProcedureText;
    }

    public void setSearchProcedureText(String searchProcedureText) {
        this.searchProcedureText = searchProcedureText;
    }

    public List<ProcedureDTO> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<ProcedureDTO> procedures) {
        this.procedures = procedures;
    }
    
    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
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
    
    ///////////// LISTS ////////////////////////
    
    public void onPageLoad() throws InstantiationException, IllegalAccessException{
        //updateProcedure();
        //cCaretaker = UserManager.class.newInstance().getUsername();
        cCaretaker = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
    }
    private void updateProcedure() {
        allProcedures = getAllProceduresREST();
        procedures = allProcedures;
    }


    
    public void search() {
        switch(userType) {
            case "trainingM":
                List<TrainingMaterialDTO> resultT = new LinkedList();
                trainingMaterials = allTrainingMaterials;
                trainingMaterials.stream().filter((t) -> (t.getName().contains(searchTrainingMaterialsText))).forEach((t) -> {
                    resultT.add(t);
                });

                trainingMaterials = resultT;
                break;
                
            case "procedure":
                List<ProcedureDTO> resultP = new LinkedList();
                procedures = allProcedures;                    
                procedures.stream().filter((p) -> (p.getDescription().contains(searchProcedureText))).forEach((p) -> {
                    resultP.add(p);
                });

                procedures = resultP;
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
