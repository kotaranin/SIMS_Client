/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coordinator;

import client_controller.ClientFormController;
import client_controller.FilePickerFormController;
import client_controller.LogInPanelController;
import client_controller.ReportsPanelController;
import communication.Communication;
import domain.Report;
import domain.StudentOfficer;
import view.ClientForm;
import view.FilePickerForm;

/**
 *
 * @author kotar
 */
public class Coordinator {

    private static Coordinator instance;
    public static StudentOfficer studentOfficer;
    private ClientFormController clientFormController;
    private LogInPanelController logInPanelController;
    private ReportsPanelController reportsPanelController;
    private FilePickerFormController filePickerFormController;
    // svi kontroleri ovde

    private Coordinator() {
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
        reportsPanelController.fillReportsPanel(Communication.getInstance().getAllReports());
    }

    public Report openFilePickerForm() throws Exception {
        filePickerFormController = new FilePickerFormController(new FilePickerForm());
        return filePickerFormController.getSelectedReport();
    }

}
