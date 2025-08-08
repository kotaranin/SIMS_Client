/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package coordinator;

import client_controller.ClientFormController;
import client_controller.LogInPanelController;
import client_controller.ReportsPanelController;
import domain.StudentOfficer;
import view.ClientForm;

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

    public void openReportsPanel() {
        reportsPanelController = new ReportsPanelController(clientFormController.getReportsPanel());
        clientFormController.openReportsPanel();
        reportsPanelController.fillReportsPanel();
    }

}
