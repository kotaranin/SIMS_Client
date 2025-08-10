/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coordinator;

import controllers.ClientFormController;
import controllers.FilePickerFormController;
import controllers.LogInPanelController;
import controllers.ReportsPanelController;
import communication.Communication;
import controllers.CountriesPanelController;
import controllers.ExamPeriodsPanelController;
import controllers.InsertCountryController;
import controllers.InsertExamPeriodController;
import domain.Country;
import domain.ExamPeriod;
import domain.Report;
import domain.StudentOfficer;
import enums.Mode;
import view.ClientForm;
import view.FilePickerForm;
import view.InsertCountryForm;
import view.InsertExamPeriodForm;

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

}
