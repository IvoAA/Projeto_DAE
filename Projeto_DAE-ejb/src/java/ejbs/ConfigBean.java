package ejbs;

import enumerations.MaterialSupport;
import enumerations.MaterialType;
import enumerations.NecessityTypes;
import entities.NecessityType;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class ConfigBean {

    @EJB
    private PatientBean patientBean;
    @EJB
    private CaretakerBean caretakerBean;
    @EJB
    private AdministratorBean administratorBean;
    @EJB
    private TrainingMaterialBean trainingMaterialBean;
    @EJB
    private NecessityTypeBean necessityTypeBean;
    @EJB
    private ProcedureBean procedureBean;

    @PostConstruct
    public void populateBD() {

        try {

            for (NecessityTypes t : NecessityTypes.values()) {
                necessityTypeBean.create(t.name());
            }
            
            administratorBean.create("admin1", "adminName1", "adminPass1");
            administratorBean.create("admin2", "adminName2", "adminPass2");

            caretakerBean.create("user1", "name1", "pass1");
            caretakerBean.create("user2", "name2", "pass2");

            List<String> necessities1 = new LinkedList<>();
            List<String> necessities2 = new LinkedList<>();
            List<String> necessities3 = new LinkedList<>();
            
            
            necessities1.add(NecessityTypes.MEMBROS_SUPERIORES.getNecessityType());
            necessities2.add(NecessityTypes.MEMBROS_SUPERIORES.getNecessityType());
            necessities2.add(NecessityTypes.PSICOLOGICA.getNecessityType());
            

            patientBean.create(1, "Ivo Amador", "Dor de estômago",necessities1);
            patientBean.create(2, "Rodrigo Faria", "Dor de cu",necessities2 );
            patientBean.create(3, "Pedro Figueiredo", "Dor de corno", necessities3);


            trainingMaterialBean.create(1, "Venancio de 4", MaterialType.questionários.name(), MaterialSupport.texto.name());
            trainingMaterialBean.create(2, "Figas de 2", MaterialType.tutoriais.name(), MaterialSupport.video.name());
            trainingMaterialBean.create(3, "Massagens Superiores", MaterialType.tutoriais.name(), MaterialSupport.video.name());
            
            patientBean.enrollPatient(1, "user1");
            patientBean.enrollPatient(2, "user1");
            
            trainingMaterialBean.enrollTrainingMaterial(1, "user1");

            necessityTypeBean.associateMaterialToNecessityType(1, NecessityTypes.MEBROS_INFERIORES.name());
            necessityTypeBean.associateMaterialToNecessityType(3, NecessityTypes.MEBROS_INFERIORES.name());
            necessityTypeBean.associateMaterialToNecessityType(2, NecessityTypes.MEMBROS_SUPERIORES.name());
            necessityTypeBean.associateMaterialToNecessityType(2, NecessityTypes.PSICOLOGICA.name());
            necessityTypeBean.associateMaterialToNecessityType(3, NecessityTypes.PSICOLOGICA.name());
            necessityTypeBean.associateMaterialToNecessityType(3, NecessityTypes.TRONCO.name());
            
            procedureBean.create(1, "Aparafusar Joelho", NecessityTypes.MEBROS_INFERIORES.name(), "user2", 2, 2);
            
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
