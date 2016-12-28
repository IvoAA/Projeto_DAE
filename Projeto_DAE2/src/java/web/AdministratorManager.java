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
import ejbs.AdministratorBean;
import ejbs.CaretakerBean;
import ejbs.HealthCareProfessionalBean;
import ejbs.PatientBean;
import entities.HealthCareProfessional;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
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

    /*
    @EJB
    private CourseBean courseBean;
    @EJB
    private SubjectBean subjectBean;
    private StudentDTO newStudent;
    private StudentDTO currentStudent;
    private CourseDTO newCourse;
    private CourseDTO currentCourse;
    private SubjectDTO newSubject;
    private SubjectDTO currentSubject;
    */
    
    @EJB
    private PatientBean patientBean;
    @EJB
    private CaretakerBean caretakerBean;
    @EJB
    private AdministratorBean administratorBean;
    @EJB
    private HealthCareProfessionalBean healthCareProfessionalBean;
    
    private PatientDTO newPatient;
    private PatientDTO currentPatient;
    private CaretakerDTO newCaretaker;
    private CaretakerDTO currentCaretaker;
    private AdministratorDTO newAdministrator;
    private AdministratorDTO currentAdministrator;
    private HealthCareProfessionalDTO newHealthCareProfessional; 
    private HealthCareProfessionalDTO currentHealthCareProfessional; 
    private String userType;
    private String searchCaretakersText;
    private String searchAdminsText;
    private String searchHCProsText;
    
    private List<CaretakerDTO> allCaretakers;
    private List<CaretakerDTO> caretakers;
    private List<AdministratorDTO> allAdmins;
    private List<AdministratorDTO> admins;
    private List<HealthCareProfessionalDTO> allHCPros;
    private List<HealthCareProfessionalDTO> hCPros;

    private static final Logger logger = Logger.getLogger("web.AdministratorManager");
    private UIComponent component;
    private Client client;
    private final String baseUri
            = "http://localhost:8080/Projeto_DAE2/webapi";

    public AdministratorManager() {
        
        newPatient = new PatientDTO();
        newCaretaker = new CaretakerDTO();
        newAdministrator = new AdministratorDTO();
        newHealthCareProfessional = new HealthCareProfessionalDTO();
        client = ClientBuilder.newClient();
        
        allCaretakers = getAllCaretakersREST();
        caretakers = allCaretakers;
        allAdmins = getAllAdministratorsREST();
        admins = allAdmins;
        allHCPros = getAllHealthCareProfessionalsREST();
        hCPros = allHCPros;
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
                    newPatient.getCaretaker());
            
            newPatient.reset();
            return "admin_index?faces-redirect=true";
        } catch (EntityAlreadyExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }
    /*      
    public String updatePatient() {
        try {
            patientBean.update(
                    currentPatient.getId(),
                    currentPatient.getName(),
                    currentPatient.getCaretaker());
            return "admin_index?faces-redirect=true";

        } catch (EntityDoesNotExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return "admin_patient_update";
    }
    */
    
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
/*
    public List<StudentDTO> getAllStudents() {
        try {
            return studentBean.getAll();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }

    public List<SubjectDTO> getCurrentStudentSubjects() {
        try {
            return subjectBean.getStudentSubjects(currentStudent.getUsername());
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
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
            return "admin_index?faces-redirect=true";
           
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return "admin_administrator_update";
    }
/*
    public List<CaretakerDTO> getAllCaretakers() {
        try {
            return caretakerBean.getAll();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
*/
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
/*
    public List<CaretakerDTO> getAllCaretakers() {
        try {
            return caretakerBean.getAll();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
*/
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
/*
    public List<CaretakerDTO> getAllCaretakers() {
        try {
            return caretakerBean.getAll();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
*/
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
    
    
    
/*
    /////////////// SUBJECTS /////////////////
    public String createSubject() {
        try {
            subjectBean.create(
                    newSubject.getCode(),
                    newSubject.getName(),
                    newSubject.getCourseCode(),
                    newSubject.getCourseYear(),
                    newSubject.getScholarYear());
            newSubject.reset();
            return "index?faces-redirect=true";
        } catch (EntityAlreadyExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }

    public List<SubjectDTO> getAllSubjects() {
        try {
            return subjectBean.getAll();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }

    public void removeSubject(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("subjectCode");
            int code = Integer.parseInt(param.getValue().toString());
            subjectBean.remove(code);
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }

    public List<StudentDTO> getEnrolledStudents() {
        try {
            return studentBean.getEnrolledStudents(currentSubject.getCode());
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }

    public List<StudentDTO> getUnrolledStudents() {
        try {
            return studentBean.getUnrolledStudents(currentSubject.getCode());
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }

    public void enrollStudent(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("studentUsername");
            String username = param.getValue().toString();
            studentBean.enrollStudent(username, currentSubject.getCode());
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }

    public void unrollStudent(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("studentUsername");
            String username = param.getValue().toString();
            studentBean.unrollStudent(username, currentSubject.getCode());
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    */
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
    
    
    
    
    /*
    public CourseDTO getNewCourse() {
        return newCourse;
    }

    public void setNewCourse(CourseDTO newCourse) {
        this.newCourse = newCourse;
    }

    public CourseDTO getCurrentCourse() {
        return currentCourse;
    }

    public void setCurrentCourse(CourseDTO currentCourse) {
        this.currentCourse = currentCourse;
    }

    public SubjectDTO getNewSubject() {
        return newSubject;
    }

    public void setNewSubject(SubjectDTO newSubject) {
        this.newSubject = newSubject;
    }

    public SubjectDTO getCurrentSubject() {
        return currentSubject;
    }

    public void setCurrentSubject(SubjectDTO currentSubject) {
        this.currentSubject = currentSubject;
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
    /*
    public void validateUsername(FacesContext context, UIComponent toValidate, Object value) {
        try {
            //Your validation code goes here
            String username = (String) value;
            //If the validation fails
            if (username.startsWith("xpto")) {
                FacesMessage message = new FacesMessage("Error: invalid username.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                context.addMessage(toValidate.getClientId(context), message);
                ((UIInput) toValidate).setValid(false);
            }
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unkown error.", logger);
        }
    }
    */
    
    
    ///////////// SEARCH ////////////////////////
    
    
    public void search() {
        switch(userType) {
            case "caretaker":
                List<CaretakerDTO> resultC = new LinkedList();
                caretakers = allCaretakers;                    
                caretakers.stream().filter((c) -> (c.getUsername().contains(searchCaretakersText))).forEach((c) -> {
                    resultC.add(c);
                });

                caretakers = resultC;
                break;
            case "admin":
                List<AdministratorDTO> resultA = new LinkedList();
                admins = allAdmins;                    
                admins.stream().filter((a) -> (a.getUsername().contains(searchAdminsText))).forEach((a) -> {
                    resultA.add(a);
                });

                admins = resultA;
                break;
            case "hCPro":
                List<HealthCareProfessionalDTO> resultH = new LinkedList();
                hCPros = allHCPros;                    
                hCPros.stream().filter((h) -> (h.getUsername().contains(searchHCProsText))).forEach((h) -> {
                    resultH.add(h);
                });

                hCPros = resultH;
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
        }
    }
}
