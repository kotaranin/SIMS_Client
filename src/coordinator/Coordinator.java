/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coordinator;

import controllers.form.ClientFormController;
import controllers.form.FilePickerFormController;
import controllers.panel.LogInPanelController;
import controllers.panel.ReportsPanelController;
import communication.Communication;
import controllers.panel.CountriesPanelController;
import controllers.panel.ExamPeriodsPanelController;
import controllers.form.InsertCountryController;
import controllers.form.InsertExamPeriodController;
import controllers.form.InsertTeacherController;
import controllers.panel.TeacherPanelController;
import domain.Country;
import domain.ExamPeriod;
import domain.Report;
import domain.StudentOfficer;
import domain.Teacher;
import enums.Mode;
import view.forms.ClientForm;
import view.forms.FilePickerForm;
import view.forms.InsertCountryForm;
import view.forms.InsertExamPeriodForm;
import view.forms.InsertTeacherForm;

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
    private ReportsPanelController reportsPanelController;
    private FilePickerFormController filePickerFormController;
    private CountriesPanelController countriesPanelController;
    private InsertCountryController insertCountryController;
    private ExamPeriodsPanelController examPeriodsController;
    private InsertExamPeriodController insertExamPeriodController;
    private TeacherPanelController teacherPanelController;
    private InsertTeacherController insertTeacherController;
    // svi kontroleri ovde

    private Coordinator() {
        this.communication = Communication.getInstance();
    }

    public static Coordinator getInstance() {
        if (instance == null) {
            instance = new Coordinator();
        }
        return instance;
    }

    public void openClientForm() {
        clientFormController = new ClientFormController(new ClientForm());
        clientFormController.openClientForm();
        logInPanelController = new LogInPanelController(clientFormController.getLogInPanel());
    }

    public void openReportsPanel() throws Exception {
        reportsPanelController = new ReportsPanelController(clientFormController.getReportsPanel());
        clientFormController.openReportsPanel();
        reportsPanelController.fillReports(communication.getAllReports());
    }

    public Report openFilePickerForm() throws Exception {
        filePickerFormController = new FilePickerFormController(new FilePickerForm());
        return filePickerFormController.getSelectedReport();
    }

    public void openCountriesPanel() throws Exception {
        countriesPanelController = new CountriesPanelController(clientFormController.getCountriesPanel());
        countriesPanelController.fillCountries(communication.getAllCountries());
    }

    public void openInsertCountryForm(Country country, Mode mode) {
        insertCountryController = new InsertCountryController(new InsertCountryForm(clientFormController.getClientForm(), true), country, mode);
        insertCountryController.openInsertCountryForm();
    }

    public void openExamPeriodsPanel() throws Exception {
        examPeriodsController = new ExamPeriodsPanelController(clientFormController.getExamPeriodsPanel());
        examPeriodsController.fillExamPeriods(communication.getAllExamPeriods());
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

}
