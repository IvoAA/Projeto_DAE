package ejbs;

import enumerations.MaterialSupport;
import enumerations.MaterialType;
import enumerations.NecessityTypes;
import entities.NecessityType;
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

    @PostConstruct
    public void populateBD() {

        try {

            for (NecessityTypes t : NecessityTypes.values()) {
                necessityTypeBean.create(t.name());
            }
            /*
            necessityTypeBean.create(NecessityTypes.MEBROS_INFERIORES.name());
            necessityTypeBean.create(NecessityTypes.MEMBROS_SUPERIORES.name());
            necessityTypeBean.create(NecessityTypes.PSICOLOGICA.name());
            necessityTypeBean.create(NecessityTypes.TRONCO.name());*/
            
            administratorBean.create("admin1", "adminName1", "adminPass1");
            administratorBean.create("admin2", "adminName2", "adminPass2");

            caretakerBean.create("user1", "name1", "pass1");
            caretakerBean.create("user2", "name2", "pass2");

            patientBean.create(1, "Ivo Amador", "Dor de estômago", NecessityTypes.TRONCO.getNecessityType());
            patientBean.create(2, "Rodrigo Faria", "Dor de cu",NecessityTypes.TRONCO.getNecessityType() );
            patientBean.create(3, "Pedro Figueiredo", "Dor de corno",NecessityTypes.PSICOLOGICA.getNecessityType());


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
            /*

            teacherBean.addSubjectTeacher(1, "t1");
            teacherBean.addSubjectTeacher(2, "t2");
            teacherBean.addSubjectTeacher(1, "t3");
             */
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
