/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coordinator;

import controllers.form.*;
import controllers.panel.*;
import communication.Communication;
import domain.*;
import enums.Mode;
import view.forms.*;

/**
 *
 * @author kotar
 */
public class Coordinator {

    private static Coordinator instance;
    private final Communication communication;
    public static StudentOfficer studentOfficer;
    private ClientFormController clientFormController;
    private LogInPanelController logInPanelController;
    private InternshipPanelController internshipPanelController;
    private FilePickerFormController filePickerFormController;
    private CountryPanelController countryPanelController;
    private InsertCountryController insertCountryController;
    private ExamPeriodPanelController examPeriodController;
    private InsertExamPeriodController insertExamPeriodController;
    private TeacherPanelController teacherPanelController;
    private InsertTeacherController insertTeacherController;
    private StudyLevelPanelController studyLevelPanelController;
    private InsertStudyLevelController insertStudyLevelController;
    private HelpPanelController helpPanelController;
    private InternshipPanelController internshipPanelController1;
    private StudentOfficerPanelController studentOfficerPanelController;
    private StudentPanelController studentPanelController;
    private InsertCountryForm insertCountryForm;
    private InsertCityController insertCityController;
    private InsertStudyProgramController insertStudyProgramController;
    private InsertStudyLevelForm insertStudyLevelForm;
    private InsertStudyProgramForm insertStudyProgramForm;
    private InsertModuleController insertModuleController;
    // svi kontroleri i insert forme za slozene SK ovde

    private Coordinator() {
        this.communication = Communication.getInstance();
    }

    public static Coordinator getInstance() {
        if (instance == null) {
            instance = new Coordinator();
        }
        return instance;
    }

    public InsertStudyLevelController getInsertStudyLevelController() {
        return insertStudyLevelController;
    }

    public InsertStudyProgramController getInsertStudyProgramController() {
        return insertStudyProgramController;
    }

    public void openClientForm() {
        clientFormController = new ClientFormController(new ClientForm());
        clientFormController.openClientForm();
        logInPanelController = new LogInPanelController(clientFormController.getLogInPanel());
    }

    public void openInternshipPanel() {
        internshipPanelController = new InternshipPanelController(clientFormController.getInternshipPanel());
        clientFormController.openInternshipPanel();
        internshipPanelController.fillInternships(communication.getAllInternships());
    }

    public Report openFilePickerForm() throws Exception {
        filePickerFormController = new FilePickerFormController(new FilePickerForm());
        return filePickerFormController.getSelectedReport();
    }

    public void openCountryPanel() throws Exception {
        countryPanelController = new CountryPanelController(clientFormController.getCountryPanel());
        countryPanelController.fillCountries(communication.getAllCountries());
        countryPanelController.fillCities(null);
    }

    public void openInsertCountryForm(Country country, Mode mode) throws Exception {
        this.insertCountryForm = new InsertCountryForm(clientFormController.getClientForm(), true);
        insertCountryController = new InsertCountryController(insertCountryForm, country, mode);
        insertCountryController.openInsertCountryForm();
    }

    public void openExamPeriodPanel() throws Exception {
        examPeriodController = new ExamPeriodPanelController(clientFormController.getExamPeriodPanel());
        examPeriodController.fillExamPeriods(communication.getAllExamPeriods());
    }

    public void openInsertExamPeriodForm(ExamPeriod examPeriod, Mode mode) throws Exception {
        insertExamPeriodController = new InsertExamPeriodController(new InsertExamPeriodForm(clientFormController.getClientForm(), true), examPeriod, mode);
        insertExamPeriodController.openInsertExamPeriodForm();
    }

    public void openTeacherPanel() throws Exception {
        teacherPanelController = new TeacherPanelController(clientFormController.getTeacherPanel());
        teacherPanelController.fillTeachers(communication.getAllTeachers());
    }

    public void openInsertTeacherForm(Teacher teacher, Mode mode) {
        insertTeacherController = new InsertTeacherController(new InsertTeacherForm(clientFormController.getClientForm(), true), teacher, mode);
        insertTeacherController.openInsertTeacherForm();
    }

    public void openStudyLevelPanel() throws Exception {
        studyLevelPanelController = new StudyLevelPanelController(clientFormController.getStudyLevelPanel());
        studyLevelPanelController.setAllStudyLevels(communication.getAllStudyLevels());
        studyLevelPanelController.fillStudyLevels(studyLevelPanelController.getAllStudyLevels());
        studyLevelPanelController.fillStudyPrograms(null);
        studyLevelPanelController.fillModules(null);
    }

    public void openInsertStudyLevelForm(StudyLevel studyLevel, Mode mode) throws Exception {
        this.insertStudyLevelForm = new InsertStudyLevelForm(clientFormController.getClientForm(), true);
        insertStudyLevelController = new InsertStudyLevelController(insertStudyLevelForm, studyLevel, mode);
        insertStudyLevelController.openInsertStudyLevelForm();
    }

    public void openStudentOfficerPanel() throws Exception {
        studentOfficerPanelController = new StudentOfficerPanelController(clientFormController.getStudentOfficerPanel());
        studentOfficerPanelController.fillStudentOfficers(communication.getAllStudentOfficers());
    }

    public void openStudentPanel() throws Exception {
        studentPanelController = new StudentPanelController(clientFormController.getStudentPanel());
        studentPanelController.fillStudents(communication.getAllStudents());
    }

    public void openHelpPanel() {
        helpPanelController = new HelpPanelController(clientFormController.getHelpPanel());
    }

    public void openInsertCityForm(City city, Mode mode) {
        insertCityController = new InsertCityController(new InsertCityForm(insertCountryForm, true), city, mode);
        insertCityController.openInsertCityForm();
    }

    public void openInsertStudyProgramForm(StudyProgram studyProgram, Mode mode) throws Exception {
        this.insertStudyProgramForm = new InsertStudyProgramForm(insertStudyLevelForm, true);
        insertStudyProgramController = new InsertStudyProgramController(insertStudyProgramForm, studyProgram, mode);
        insertStudyProgramController.openInsertStudyProgramForm();
    }

    public void openInsertModuleForm(domain.Module module, Mode mode) {
        insertModuleController = new InsertModuleController(new InsertModuleForm(insertStudyProgramForm, true), module, mode);
        insertModuleController.openInsertModuleForm();
    }

}
