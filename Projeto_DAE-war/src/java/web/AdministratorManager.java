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
import dtos.UtenteDTO;
import ejbs.UtenteBean;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

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
    private UtenteBean utenteBean;
    private UtenteDTO newUtente;
    private UtenteDTO currentUtente;
    
    private static final Logger logger = Logger.getLogger("web.AdministratorManager");
    private UIComponent component;
    private Client client;
    private final String baseUri
            = "http://localhost:8080/AcademicManagement-war/webapi";

    public AdministratorManager() {
        
        /*
        newCourse = new CourseDTO();
        newSubject = new SubjectDTO();
        */
        newUtente = new UtenteDTO();
        client = ClientBuilder.newClient();
    }

    
    
    
    ///////////// STUDENTS /////////////////
    public String createUtente() {
        try {
            utenteBean.create(
                    newUtente.getId(),
                    newUtente.getName(),
                    newUtente.getPassword());
            newUtente.reset();
            return "index?faces-redirect=true";
        } catch (EntityAlreadyExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
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

    public List<StudentDTO> getAllStudentsREST() {
        List<StudentDTO> returnedStudents = null;
        returnedStudents = client.target(baseUri)
                .path("/students/all")
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<StudentDTO>>() {});
        return returnedStudents;
    }

    public String updateStudent() {
        try {
            studentBean.update(
                    currentStudent.getUsername(),
                    currentStudent.getPassword(),
                    currentStudent.getName(),
                    currentStudent.getEmail(),
                    currentStudent.getCourseCode());
            return "index?faces-redirect=true";

        } catch (EntityDoesNotExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return "admin_students_update";
    }

    public void removeStudent(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("studentUsername");
            String id = param.getValue().toString();
            studentBean.remove(id);
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }

    public List<SubjectDTO> getCurrentStudentSubjects() {
        try {
            return subjectBean.getStudentSubjects(currentStudent.getUsername());
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
    }

    /////////////// COURSES /////////////////
    public String createCourse() {
        try {
            courseBean.create(
                    newCourse.getCode(),
                    newCourse.getName());
            newCourse.reset();
            return "index?faces-redirect=true";
        } catch (EntityAlreadyExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }

    public List<CourseDTO> getAllCourses() {
        try {
            return courseBean.getAll();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }

    public void removeCourse(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("courseCode");
            int code = Integer.parseInt(param.getValue().toString());
            courseBean.remove(code);
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }

    public List<SubjectDTO> getCurrentCourseSubjects() {
        try {
            return subjectBean.getCourseSubjects(currentCourse.getCode());
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }

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

    /////////////// GETTERS & SETTERS /////////////////    
    public StudentDTO getNewStudent() {
        return newStudent;
    }

    public void setNewStudent(StudentDTO newStudent) {
        this.newStudent = newStudent;
    }

    public StudentDTO getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(StudentDTO currentStudent) {
        this.currentStudent = currentStudent;
    }

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

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

    ///////////// VALIDATORS ////////////////////////
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
}
